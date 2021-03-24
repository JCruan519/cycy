import numpy as np
import face_alignment
import cv2
import os
from skimage import io, transform
from matplotlib import pyplot as plt
import time
import math
import re
import ssl
ssl._create_default_https_context = ssl._create_unverified_context
# os.environ['CUDA_VISIBLE_DEVICES'] = '0'
fa = face_alignment.FaceAlignment(face_alignment.LandmarksType._2D, flip_input=False,device='cpu')

def getTraditionalFeatures(imageSavePath):
    img = cv2.imread(imageSavePath)
    # img = cv2.resize(img,(720,1280),interpolation=cv2.INTER_NEAREST)
    landMark = fa.get_landmarks(img)
    if landMark == None:
        print("Didn't detect face!!!!!!!!!!!")
        return None
    mouthLandmark = landMark[0][48:]
    features = []
    keyPointID = [50,51,52,56,57,58,60,61,62,63,64,65,66,67]
    CoordinateAngle = -getAngle(landMark[0][60],landMark[0][64])

    features.append(getAngle(landMark[0][50],landMark[0][51],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][50],landMark[0][60],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][50],landMark[0][61],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][50],landMark[0][62],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][51],landMark[0][56],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][51],landMark[0][62],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][56],landMark[0][62],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][56],landMark[0][63],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][56],landMark[0][64],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][61],landMark[0][60],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][61],landMark[0][62],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][63],landMark[0][62],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][63],landMark[0][64],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][67],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][67],landMark[0][58],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][67],landMark[0][60],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][67],landMark[0][66],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][66],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][66],landMark[0][65],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][65],landMark[0][56],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][65],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][65],landMark[0][64],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][56],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][56],landMark[0][64],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][58],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][58],landMark[0][60],coordinateAngle=CoordinateAngle))
            
    return features

def getAngle(point1, point2, coordinateAngle=None):
    if coordinateAngle == None:
        x1 = point1[0]
        y1 = point1[1]
        x2 = point2[0]
        y2 = point2[1]
        angle = math.atan2((y2-y1),(x2-x1))
        # angle = math.degrees(angle)
        return angle
    else:# sin cos 输入都是弧度而不是角度
        
        x1 = point1[0]*math.cos(coordinateAngle)-point1[1]*math.sin(coordinateAngle)
        y1 = point1[1]*math.cos(coordinateAngle)+point1[0]*math.sin(coordinateAngle)
        x2 = point2[0]*math.cos(coordinateAngle)-point2[1]*math.sin(coordinateAngle)
        y2 = point2[1]*math.cos(coordinateAngle)+point2[0]*math.sin(coordinateAngle)
        angle = math.atan2((y2-y1),(x2-x1))
        angle = math.degrees(angle)
        return angle

def loadTargetFeatures(imageSavePath):
    imageID = imageSavePath.split('/')[-1]
    imageID = imageID.split('.')[0]
    wordLabel = imageID.split('_')[1]
    imageNumber = imageID.split('_')[-1]
    targetPath = "/My_codes/ServerMain/tradition/targets/"+ wordLabel +"/" + imageNumber + ".png"

    img = cv2.imread(targetPath)
    landMark = fa.get_landmarks(img)
    if landMark == None:
        print("Didn't detect face!!!!!!!!!!!")
        return None
    mouthLandmark = landMark[0][48:]
    features = []
    CoordinateAngle = -getAngle(landMark[0][60],landMark[0][64])

    features.append(getAngle(landMark[0][50],landMark[0][51],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][50],landMark[0][60],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][50],landMark[0][61],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][50],landMark[0][62],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][51],landMark[0][56],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][51],landMark[0][62],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][56],landMark[0][62],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][56],landMark[0][63],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][56],landMark[0][64],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][61],landMark[0][60],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][61],landMark[0][62],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][63],landMark[0][62],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][63],landMark[0][64],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][67],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][67],landMark[0][58],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][67],landMark[0][60],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][67],landMark[0][66],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][66],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][66],landMark[0][65],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][65],landMark[0][56],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][65],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][65],landMark[0][64],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][56],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][56],landMark[0][64],coordinateAngle=CoordinateAngle))

    features.append(getAngle(landMark[0][58],landMark[0][57],coordinateAngle=CoordinateAngle))
    features.append(getAngle(landMark[0][58],landMark[0][60],coordinateAngle=CoordinateAngle))

    return features


def caculateSimilarity(inputData, targetData):
    inputData = np.array(inputData)
    targetData = np.array(targetData)
    loss = np.linalg.norm(inputData-targetData)
    return loss

if __name__ == "__main__":
    # loss = caculateSimilarity(np.array([1,1,1]),np.array([2,3,9.2836419]))
    # print (getAngle([1,0],[0,1],coordinateAngle=-math.pi/4))
    # print (getAngle([1,0],[0,1]))

    inputdata = getTraditionalFeatures("/My_codes/ServerMain/tradition/targets/0/4.png")
    targetdata = loadTargetFeatures("/My_codes/ServerMain/tradition/test/fd7d3bba-ab64-45de-a447-60c8b1c2d971_0/klhsdlkbave_0_2.png")
    print(caculateSimilarity(inputdata,targetdata)) 
                    
