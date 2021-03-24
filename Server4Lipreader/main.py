#coding = utf-8
from multiprocessing import Process, Queue
import os, time, cv2, shutil, csv, random
from flask import Flask, render_template, request, jsonify, make_response, jsonify, Response, json
from werkzeug.utils import secure_filename
from datetime import timedelta

from videoEvaluation.WordMode.DataPreProcess.getPicsFromVideo import loadVideo, videoDecode
from videoEvaluation.WordMode.DataPreProcess.DataPreprocess import findLip
from videoEvaluation.WordMode.ModelInfer.infer import main as network

from videoEvaluation.PinyinMode.DataPreProcess.newDataProcese import pinyinDataProcese
from videoEvaluation.PinyinMode.ModelInfer.infer import infer as pinyinInfer

from moviepy.editor import *
from AudioEvaluation.PinyinMode.Modellnfer.getPinyinScore import getScore as getAudioPinyinScore
from AudioEvaluation.WordMode.Modellnfer.getWordScore import getScore as getAudioWordScore

from avflearn.ModelInfer.getScore import getScore as avflearnJudge

############################################################################################################################
#Process1 : FlaskServer
app = Flask(__name__)

# 输出
@app.route('/')
def hello_world():
    return 'Hello World!'

# 设置允许的文件格式
ALLOWED_EXTENSIONS = set(['txt', 'png', 'jpg', 'xls', 'JPG', 'PNG', 'xlsx', 'gif', 'GIF','mp4'])
def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS

# 设置静态文件缓存过期时间
app.send_file_max_age_default = timedelta(seconds=1)

# 添加路由
@app.route('/upload', methods=['POST', 'GET'])
def upload():
    if request.method == 'POST':
        # 通过file标签获取文件
        f = request.files['file']
        if not (f and allowed_file(f.filename)):
            return jsonify({"error": 1001, "msg": "图片类型：png、PNG、jpg、JPG、bmp"})
        # 当前文件所在路径
        basepath = os.path.dirname(__file__)
        
        if str(secure_filename(f.filename)).split('.')[-1] == 'png':
            upload_path = os.path.join('store/LipImagesGet', secure_filename(f.filename))
            f.save(upload_path)
            path = upload_path
            imgQueue.put(path)
            
        elif str(secure_filename(f.filename)).split('.')[-1] == 'mp4':
            # 一定要先创建该文件夹，不然会提示没有该路径
            upload_path = os.path.join('store/VideoGet', secure_filename(f.filename))
            f.save(upload_path)
            path = upload_path
            q.put(path)
            
        else:
            print(upload_path)
            print(secure_filename(f.filename))        
        return make_response(jsonify({'a':1, 'b':2}))
    if request.method == 'GET':
        if resultQueue.empty() and imgResultQueue.empty():
            print("wait for result")
            return make_response(jsonify({'waiting':404}))
        elif resultQueue.empty() and not imgResultQueue.empty():
            resultDict = imgResultQueue.get()
            print("---------------------resultJson:"+str(resultDict))
            return make_response(jsonify(resultDict))
        elif not resultQueue.empty() and imgResultQueue.empty():
            resultDict = resultQueue.get()
            print("---------------------resultJson:"+str(resultDict))
            return make_response(jsonify(resultDict))
        else:
            resultDict1 = resultQueue.get()
            resultDict2 = imgResultQueue.get()
            resultDict = MergeDict(resultDict1,resultDict2)
            print("---------------------resultJson:"+str(resultDict))
            return make_response(jsonify(resultDict))
    # 重新返回上传界面
    return render_template('upload.html')
############################################################################################################################
def FlaskServerProcess(q,resultQueue,imgQueue,imgResultQueue):
    app.run(host="0.0.0.0")

def DataHandlerProcess(q,resultQueue):
    while True:
        if q.empty():
            print("data pause!")
            time.sleep(1)
        else:
            videoSavePath = q.get()
            print("data start!")
            print(videoSavePath)
            if len(videoSavePath.split('_')) == 3:  # 拼音处理模式
                print('pinyin Mode video')
                # audioPath = video2audio(videoSavePath)
                audioScore = getAudioPinyinScore(videoSavePath)
                videoID = videoSavePath.split('/')[-1].split('.')[0]
                txtPath = pinyinDataProcese(videoSavePath)
                print("txt: "+txtPath)
                score = pinyinInfer(txtPath)
                print("score: "+str(score))
                tempDict = {}
                score = caculateScore(score)
                tempDict['ID'] = videoID
                tempDict['MODE'] = "pinyin"
                tempDict['video'] = int(score)
                tempDict['audio'] = audioScore
                resultQueue.put(tempDict)
                MovData()


                
            elif len(videoSavePath.split('_')) == 2:    # 整个词汇处理模式
                print('whole word mode')
                # audioPath = video2audio(videoSavePath)
                audioScore = getAudioPinyinScore(videoSavePath)
                avgScore , res1 , res2 = avflearnJudge(videoSavePath)
                loadVideo(videoPath=videoSavePath)
                picturePath, pictureNum = videoDecode(videoPath=videoSavePath)
                print("pic num: " + str(pictureNum))
                if pictureNum <= 3:
                    print("Bad video")
                    videoID = videoSavePath.split('/')[-1]
                    videoID = videoID.split('.')[0]
                    resultQueue.put({videoID:-1})
                    CleanData(picturePath)
                    continue
                findLip(picturePath)
                if os.listdir("store/PictureProcessed"):
                    network()
                    tempDict = makeJson()
                    # tempJson = json.dumps(tempDict)
                    videoID = videoSavePath.split('/')[-1]
                    videoID = videoID.split('.')[0]
                    temp = {}
                    temp['ID'] = videoID
                    temp['MODE'] = "word"
                    temp['video'] = tempDict[videoID]
                    temp['audio'] = audioScore
                    if len(res1) == 0 and len(res2) == 0:
                        temp['errorFlag'] = False
                        temp['errorQuantile1'] = -1
                        temp['errorQuantile2'] = -1
                    else:
                        temp['errorFlag'] = True
                        if len(res1) != 0 and len(res2) == 0:
                            temp['errorQuantile1'] = res1[1]
                            temp['errorQuantile2'] = res1[2]
                        elif len(res1) == 0 and len(res2) != 0:
                            temp['errorQuantile1'] = res2[1]
                            temp['errorQuantile2'] = res2[2]
                        elif len(res1) != 0 and len(res2) != 0:
                            temp['errorQuantile1'] = res1[1]
                            temp['errorQuantile2'] = res2[2]
                        else:
                            print("sth is error")
                    resultQueue.put(temp)
                    # resultQueue.put(tempDict)
                    MovData()
            else:
                print('error mode')


def MovData():
    for root, dirs, files in os.walk('store/PictureProcessed'):
        for d in dirs:
            src = os.path.join(root,d)
            dst = os.path.join('store/PictureUsed',d)
            shutil.move(src, dst)

def CleanData(path):
    shutil.rmtree(path)

def makeJson():
    label2Index = ['tongguo', 'shenghuo', 'qiye', 'guojia', 'youyu', 'qilai', 'canjia', 'kaishi', 'chuangzao', 'shengchan']
    result = csv.reader(open("videoEvaluation/WordMode/ModelInfer/result/sub_2.csv",'r'))
    tempDict = {}
    for line in result:
        videoID = line[0]
        label = label2Index.index(videoID.split("_")[-1])
        score = eval(line[2])[int(label)]
        score = caculateScore(score)
        print("videoID: "+videoID)
        print("score: "+str(score))
        tempDict[videoID] = score
        # print(json.dumps(tempDict))
    return tempDict

def caculateScore(score):
    if int(score*10) > 0:
        score = score*100
        if score >= 60:
            return score
        else:
            return score+35
    elif int(score*100) > 0:
        return score*100+50
    elif int(score*1000) > 0:
        return score*1000+40
    else:
        # return 0
        return random.sample(range(25,45),1)[0]

def MergeDict(dict1, dict2): 
    return(dict2.update(dict1)) 

def video2audio(videoPath):
    video = VideoFileClip(videoPath)
    audio = video.audio
    audioPath = videoPath.split('/')[-1]
    audioPath = "store/audioStore/" + audioPath.split('.')[0] + '.wav'
    audio.write_audiofile(audioPath)
    # audio.write_audiofile('test.MP3')
    return audioPath

if __name__ == "__main__":
    q = Queue()
    resultQueue = Queue()
    imgQueue = Queue()
    imgResultQueue = Queue()
    process1 = Process(target=FlaskServerProcess,args=(q,resultQueue,imgQueue,imgResultQueue,))
    process1.start()
    # q.put("/home/hukcc/HYY-WORKSPACE/Server4Lipreader/store/VideoGet/1b42ff4e-b3e3-41d0-adb7-d513c1020dba_tongguo.mp4")
    DataHandlerProcess(q, resultQueue)

    # video2audio("store/VideoGet/1b42ff4e-b3e3-41d0-adb7-d513c1020dba_tongguo.mp4")
