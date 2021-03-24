import os
import torch
from torch import nn
from torch.autograd import Variable
import torchvision.datasets as dsets
import torch.utils.data as Data
import matplotlib.pyplot as plt
import torchvision
from torch.utils.data import Dataset, DataLoader
from torchvision import transforms, utils
import numpy as np


file_test = []
number_test = []
EPOCH = 1
# rnn1 = RNN().cuda()
# rnn2 = RNN().cuda()
# rnn3 = RNN().cuda()
# rnn1.load_state_dict(torch.load('LSTMVersion_testspace/CheckPoint/shengmu/epoch17_0.82.pth'))
# rnn2.load_state_dict(torch.load('LSTMVersion_testspace/CheckPoint/yunmu/epoch11_0.8714285714285714.pth'))
# rnn3.load_state_dict(torch.load('LSTMVersion_testspace/CheckPoint/danyinzi/epoch35_0.84.pth'))


def dataPreLoad(path):
    file_test.append(path)
    label = path.split('.')[0].split('_')[-1]
    number_test.append(int(label))

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
    return torch.from_numpy(tempArray)
class testset(Dataset):
    def __init__(self, loader=default_loader):
        #定义好 image 的路径
        self.images = file_test
        self.target = number_test
        self.loader = loader

    def __getitem__(self, index):
        fn = self.images[index]
        img = self.loader(fn)
        target = self.target[index]
        target = torch.from_numpy(np.array([target]))
        return img,target

    def __len__(self):
        return len(self.images)

class RNN(nn.Module):
    def __init__(self):
        super(RNN,self).__init__()
 
        self.rnn = nn.LSTM(
            input_size=26,
            hidden_size=64,
            num_layers=3,   # 单子但因
            # num_layers=2,   # 韵母
            # num_layers=1,   # 声母
            batch_first=True,
            bidirectional=True,
        )
 
        # self.out = nn.Linear(64*2,7)    # yunmu
        self.out = nn.Linear(64*2,5)  # shengmu
        # self.out = nn.Linear(64*2,5)  # danzidanyin
 
    def forward(self,x):
        r_out, (h_n, h_c) = self.rnn(x, None)
 
        out = self.out(r_out[:,-1,:])
        return out
class YUNMURNN(nn.Module):
    def __init__(self):
        super(YUNMURNN,self).__init__()
 
        self.rnn = nn.LSTM(
            input_size=26,
            hidden_size=64,
            num_layers=3,   # 单子但因
            # num_layers=2,   # 韵母
            # num_layers=1,   # 声母
            batch_first=True,
            bidirectional=True,
        )
 
        self.out = nn.Linear(64*2,7)    # yunmu
        # self.out = nn.Linear(64*2,5)  # shengmu
        # self.out = nn.Linear(64*2,5)  # danzidanyin
 
    def forward(self,x):
        r_out, (h_n, h_c) = self.rnn(x, None)
 
        out = self.out(r_out[:,-1,:])
        return out

def infer(path):
    file_test.clear()
    number_test.clear()
    dataPreLoad(path)
    testset_ = testset()
    rnn = RNN().cpu()
    yunmuRnn = YUNMURNN().cpu()

    videoID = path.split('/')[-1].split('_')[1]
    if videoID == 'shengmu':
        print('shengmu mode')
        rnn.load_state_dict(torch.load('videoEvaluation/PinyinMode/ModelInfer/CheckPoint/shengmu/epoch17_0.82.pth',map_location='cpu'))
        # rnn = torch.load('LSTMVersion_testspace/CheckPoint/shengmu/epoch44_0.86.pth').cuda()
    elif videoID == 'yunmu':
        print('yunmu mode')
        yunmuRnn.load_state_dict(torch.load('videoEvaluation/PinyinMode/ModelInfer/CheckPoint/yunmu/epoch11_0.8714285714285714.pth',map_location='cpu'))
        # rnn = torch.load('LSTMVersion_testspace/CheckPoint/yunmu/epoch23_0.8714285714285714.pth').cuda()
    elif videoID == 'danyinzi':
        print('danyinzi mode')
        rnn.load_state_dict(torch.load('videoEvaluation/PinyinMode/ModelInfer/CheckPoint/danyinzi/epoch35_0.84.pth',map_location='cpu'))
        # rnn = torch.load('LSTMVersion_testspace/CheckPoint/danyinzi/epoch41_0.9.pth').cuda()
    else:
        print('out of ctl')
    
    for _, (testX,textY) in enumerate(testset_):
        testX = Variable(testX).float().cpu()
        testY = Variable(textY)
        test_output = None
        if videoID == 'shengmu' or videoID == 'danyizi':
            test_output = rnn(testX)
        elif videoID == 'yunmu':
            test_output = yunmuRnn(testX)
        else:
            print('out of range')
        
        test_output = torch.nn.functional.softmax(test_output,dim=1)

        pred_y = test_output.cpu().data.numpy()
        pred_y = pred_y[0].tolist()
        return pred_y[testY.item()]
        # print('predY: '+str(pred_y)+'-------testY: '+str(testY))
        
    


if __name__ == "__main__":
    # dataPreLoad("pinyinPreprocessOutput/1a455398-242f-4b3b-8202-f49e7e4297c7_shengmu_0.txt")
    score = infer('pinyinPreprocessOutput/b9b8c04f-83a8-438f-b271-a000847d60f2_yunmu_5.txt')
    print(score)