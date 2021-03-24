import os
import sys
sys.path.append('..')

# from DataPreProcess import Video2Txt
from AudioEvaluation.PinyinMode.DataPreProcess import Video2Txt

import torch
from torch.autograd import Variable
from torch import nn
import numpy as np

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
        self.out = nn.Linear(64*2,10)################输出类别！！！！！！！！！！！！！

    def forward(self,x):
        r_out, (h_n, h_c) = self.rnn(x, None)

        out = self.out(r_out[:,-1,:])
        return out

def softmax(input):
    return torch.exp(input) / torch.sum(torch.exp(input))


def getScore(path):
    # 获取txt
    txtpath = Video2Txt.getTXT(path)
    print(txtpath)
    txtfile = default_loader(txtpath)

    # 加载模型并判断
    x = Variable(txtfile).float().cuda()
    state_dict_load = torch.load('WordMode.pth')
    model = RNN().cuda()
    model.load_state_dict(state_dict_load)
    y = model(x)

    # softmax后给出分数向量
    all_score = softmax(y)

    # 给出最终分数
    score = int(torch.max(all_score).item() * 100)
    print(all_score, score)

    return score

# import time
# t1 = time.time()
# path = 'E:\BaiduNetdiskDownload\Lip_recognition\SERVER4LIPREADER\Store\d9f6c3e8-180a-45b1-b9e5-f0817934baad_qiye.mp4'
# s = getScore(path)
# print(str(s)+"分")
# t2 = time.time()
# print(t2-t1)

##目前还需要大量的数据集，来训练模型