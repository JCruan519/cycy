import os
import sys
sys.path.append('/home/hukcc/HYY-WORKSPACE/Server4Lipreader')


# from AudioClipsPreProcess import Video2Txt
from avflearn.AudioClipsPreProcess import Video2Txt

from avflearn.AudioDivide.AudioDivide import getQuantile

import torch
from torch.autograd import Variable
from torch import nn
import numpy as np
import re


#获取文件的标签
def getFileIndex(file):
    pattern = re.compile(r'([^<>/\\\|:""\*\?]+)\.\w+$')
    fileName = pattern.findall(file)[0]
    if re.findall(r'tongguo',fileName):
        return 0 , 1
    elif re.findall(r'shenghuo',fileName):
        return 2 , 3
    elif re.findall(r'qiye',fileName):
        return 4 , 5
    elif re.findall(r'guojia',fileName):
        return 6 , 7
    elif re.findall(r'youyu',fileName):
        return 8 , 9
    elif re.findall(r'qilai',fileName):
        return 10 , 11
    elif re.findall(r'canjia',fileName):
        return 12 , 13
    elif re.findall(r'kaishi',fileName):
        return 14 , 15
    elif re.findall(r'chuangzao',fileName):
        return 16 , 17
    elif re.findall(r'shengchan',fileName):
        return 18 , 19


def default_loader(path):
    arrayList = [[]]
    f_ = open(path, 'r', encoding='utf-8')
    lines = f_.readlines()
    for l in lines:
        l = l.replace('\n','')
        features = l.split(',')
        featureCnt = 0
        features_ = []
        for feature in features:
            feature = float(feature)
            features_.append(feature)
            featureCnt += 1
        arrayList[0].append(features_)
    tempArray = np.array(arrayList)
    test = torch.from_numpy(tempArray)
    return torch.from_numpy(tempArray)


class RNN(nn.Module):
    def __init__(self):
        super(RNN,self).__init__()

        self.rnn = nn.LSTM(
            input_size=34,
            hidden_size=64,
            num_layers=3,
            batch_first=True,
            bidirectional=True,
        )
        self.out = nn.Linear(64*2,20)################输出类别！！！！！！！！！！！！！

    def forward(self,x):
        r_out, (h_n, h_c) = self.rnn(x, None)

        out = self.out(r_out[:,-1,:])
        return out


def softmax(input):
    return torch.exp(input) / torch.sum(torch.exp(input))


def getScore(path):
    #mp4文件的预处理并获取处理后txt，wav文件的路径
    txtpath,wavpath = Video2Txt.getTXT(path)
    # print(txtpath)
    txtfile = default_loader(txtpath)
    len_txtfile = len(txtfile[0])
    print(len_txtfile)

    #获取分位点
    quantile1 , quantile2 , quantile3 = getQuantile(wavpath)
    # quantile1, quantile2, quantile3 = .3,.5,.7#测试使用

    #通过分位点从特征txt中截取相应的片段
    txtfile1 = txtfile[:,int(len_txtfile * quantile1):int(len_txtfile * quantile2),:]
    txtfile2 = txtfile[:,int(len_txtfile * quantile2):int(len_txtfile * quantile3),:]
    print(txtfile1.shape,txtfile2.shape)

    #载入模型并进行评分
    x1 = Variable(txtfile1).float().cuda()
    x2 = Variable(txtfile2).float().cuda()
    state_dict_load = torch.load('avflearn/ModelInfer/model.pth')
    model = RNN().cuda()
    model.load_state_dict(state_dict_load)
    y1 = model(x1)
    y2 = model(x2)

    # softmax后给出分数向量
    all_score1 = softmax(y1)
    all_score2 = softmax(y2)

    # 获取文件属于的类别
    fileIndex1 , fileIndex2 = getFileIndex(path)


    # 给出最终分数
    score1 = int(all_score1[0][fileIndex1].item() * 100)
    score2 = int(all_score2[0][fileIndex2].item() * 100)
    score = (score1 + score2) / 2
    # print(quantile1,quantile2,quantile3)
    print(fileIndex1,fileIndex2)
    # print(all_score1 , all_score2)
    print(score1 , score2)

    # 判断分数,返回出错地点(考虑到录音时长为1s，故直接返回分位点)
    res1 = []
    res2 = []
    if score1 < 70:
        res1.append(score1)
        res1.append(quantile1)
        res1.append(quantile2)
    if score2 < 70:
        res2.append(score2)
        res2.append(quantile2)
        res2.append(quantile3)

    return score , res1 , res2


#path = '../../Store/tongguo.mp4'#分位点：.4.6.8——79，89分
#path = '../../Store/AVF-LearnStore\OriginAudio/36da36ca-a7a6-4f86-a1a5-f6d7005ea316_shenghuo.mp4'#.3.6.8---99,91分
#path = '../../Store/AVF-LearnStore\OriginAudio/51e7b0fa-0da3-4cfe-82db-b11388634a38_chuangzao.mp4'#.2.5.8---97,86分
# path = '../../Store/AVF-LearnStore\OriginAudio/54d6e0c1-ba42-4bd1-ad7c-7c723b7ab4e5_kaishi.mp4'#。2.5.8---99，84


if __name__ == "__main__":
    print(getScore('/home/hukcc/HYY-WORKSPACE/funcs/tongguo110.wav'))

