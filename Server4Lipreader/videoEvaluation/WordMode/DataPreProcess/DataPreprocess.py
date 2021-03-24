# 分割出嘴唇区域，中心对齐的方法
'''
分离出嘴部的区域，使用alignment信息
'''
import numpy as np
import face_alignment
import cv2
import os
from skimage import io, transform
from matplotlib import pyplot as plt
import time
import ssl
ssl._create_default_https_context = ssl._create_unverified_context
os.environ['CUDA_VISIBLE_DEVICES'] = '0'
# fa = face_alignment.FaceAlignment(face_alignment.LandmarksType._2D, flip_input=False, device='cpu')
fa = face_alignment.FaceAlignment(face_alignment.LandmarksType._2D, flip_input=False, device='cuda')
mouth_bias_x = 20
mouth_bias_y = 20
norm_width = 184
norm_height = 184
span_width = int(norm_width/2)
span_height = int(norm_height/2)

def findLip(piturePath = ''):
    d = piturePath.split('/')[-1]
    os.mkdir("store/PictureProcessed/"+d)
    savePath = os.path.join("store/PictureProcessed/", d)
    print(savePath)
    for _, _, f in os.walk(piturePath):
        for pics in f:
            imgSavePath = os.path.join(savePath,pics)
            print("-----------------"+pics)
            img = cv2.imread(os.path.join(piturePath,pics))
            landMark = fa.get_landmarks(img)
            if landMark == None:
                print("Didn't detect face!!!!!!!!!!!")
                continue
            mouthLandmark = landMark[0][48:]
            max_x, max_y, min_x, min_y = np.max(mouthLandmark[:, 0]), np.max(mouthLandmark[:, 1]), np.min(mouthLandmark[:, 0]), np.min(mouthLandmark[:, 1])
            center_width, center_height = int((max_x + min_x) / 2), int((max_y + min_y) / 2)
            width_cut, height_cut = max_x - min_x, max_y - min_y

            if width_cut > norm_width or height_cut > norm_height:
                print('bigger than normal')
                # 标准化区域小于有效区域，以有效区域切割，并且进行尺度放缩归一化
                cuty = int(min_y) - mouth_bias_y if int(min_y) - mouth_bias_y > 0 else 0
                cutx = int(min_x) - mouth_bias_x if int(min_x) - mouth_bias_x > 0 else 0
                mouthimg = img[cuty:int(max_y) + mouth_bias_y, cutx:int(max_x) + mouth_bias_x, :]
                mouthimg = transform.resize(mouthimg, (norm_height, norm_width))
            else:
                cutheight = center_height - span_height if center_height - span_height > 0 else 0
                cutwidth = center_width - span_width if center_width - span_width > 0 else 0
                mouthimg = img[cutheight:center_height + span_height, cutwidth:center_width + span_width, :]
            cv2.imwrite(imgSavePath,mouthimg)
