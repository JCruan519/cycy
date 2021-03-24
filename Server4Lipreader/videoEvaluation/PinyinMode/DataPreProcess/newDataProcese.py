from .getPicsFromVideo import loadVideo, videoDecode
from .dataExcute import getTraditionalFeatures
import re, os


def pinyinDataProcese(videoPath):
    path = videoPath
    f = videoPath.split('/')[-1]
    tempID = f.split('.')[0]
    # tempID = tempID.split('_')[0]+'_'+tempID.split('_')[1]+'_'
    txtName = tempID + ".txt"
    print(path)
    imgs = loadVideo(path)
    picPath, picnum = videoDecode(videoPath=path, pics = imgs)
    outputFile = open('store/pinyinPreprocessOutput/'+txtName,'w',encoding='utf-8')
    for a,b,c in os.walk(picPath):
        c.sort(key = lambda i:int(re.match(r'(\d+)',i).group()))
        for cf in c:
            features = getTraditionalFeatures(os.path.join(a,cf))
            outputFile.write(str(features).replace('[','').replace(']','').replace("'",'') +'\n')
    outputFile.close()
    return 'store/pinyinPreprocessOutput/'+txtName

if __name__ == "__main__":
    pinyinDataProcese('/My_codes/ServerMain/store/VideoGet/1beea060-3523-4b92-b2ef-9f0797949405_shengmu_0.mp4')
