import cv2 as cv
import os

imgs= []

def loadVideo(videoPath = "store/get/record.mp4"):
    videoID = videoPath.split('/')[-1]
    videoID = videoID.split('.')[0]
    os.mkdir("store/PictureGet/"+videoID)
    print(videoID)
    videoReader = cv.VideoCapture(videoPath)
    while videoReader.isOpened():
        ret, frame = videoReader.read()
        if ret:
            trans_img = cv.transpose( frame )
            new_img = cv.flip( trans_img, 0 )
            frame = cv.resize(new_img,(720,1280),interpolation=cv.INTER_NEAREST)
            # cv.imshow("", frame)
            # cv.waitKey(0)
            # size = frame.shape
            # print(size)
            imgs.append(frame)
        else:
            print("end")
            break

def videoDecode(videoPath = "store/get/record.mp4"):
    videoID = videoPath.split('/')[-1]
    videoID = videoID.split('.')[0]
    cnt = 0
    for img in imgs:
        if cnt % 5 == 0:
            cv.imwrite("store/PictureGet/"+ videoID + '/' +str((cnt//5) + 1)+".png", img)
        cnt += 1
    imgs.clear()
    return "store/PictureGet/"+ videoID, cnt//5


if __name__ == "__main__":
    loadVideo()
    videoDecode()
    print("test")
    
    