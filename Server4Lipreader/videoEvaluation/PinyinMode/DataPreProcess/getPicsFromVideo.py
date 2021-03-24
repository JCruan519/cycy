import cv2 as cv
import os

imgs= []

def loadVideo(videoPath = "store/get/record.mp4"):
    videoID = videoPath.split('/')[-1]
    videoID = videoID.split('.')[0]
    os.mkdir("store/pinyinmode/PictureGet/"+videoID)
    print(videoID)
    videoReader = cv.VideoCapture(videoPath)
    imgs_ = []
    while videoReader.isOpened():
        ret, frame = videoReader.read()
        if ret:
            trans_img = cv.transpose( frame )
            new_img = cv.flip( trans_img, 0 )
            frame = cv.resize(new_img,(480,640),interpolation=cv.INTER_NEAREST)
            # cv.imshow("", frame)
            # cv.waitKey(0)
            # size = frame.shape
            # print(size)
            imgs_.append(frame)
        else:
            if len(imgs_) > 20:
                imgs_ = imgs_[0:20]
            print("end")
            return imgs_

def videoDecode(videoPath , pics):
    videoID = videoPath.split('/')[-1]
    videoID = videoID.split('.')[0]
    cnt = 0
    for img in pics:
        if cnt % 4 == 0:
            cv.imwrite("store/pinyinmode/PictureGet/"+ videoID + '/' +str((cnt//4) + 1)+".png", img)
        cnt += 1
    pics.clear()
    return "store/pinyinmode/PictureGet/"+ videoID, cnt//4


if __name__ == "__main__":
    loadVideo("/My_codes/tradition/video/1/2020-02-20-12-38-39_1_.avi")
    videoDecode("/My_codes/tradition/video/1/2020-02-20-12-38-39_1_.avi")
    print("test")
    
    