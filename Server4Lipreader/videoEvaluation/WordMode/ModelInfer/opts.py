import os
import random

RAW_PATH = "store" #数据路径，要保证下一级文件夹是1.训练集和2.测试集

# NUM_LABEL = "./label_map/num_label.txt"
# NUM_LABEL_R = "./label_map/num_label_r.txt"

TRAIN_PATH = os.path.join(RAW_PATH, "")
TRAIN_DATA = os.path.join(TRAIN_PATH, "newdata_train")
TRAIN_LABEL = os.path.join(TRAIN_PATH, "choose-50-kind-train-number.txt")

VAL_PATH = os.path.join(RAW_PATH, "")
VAL_DATA = os.path.join(VAL_PATH, "newdata_val")
VAL_LABEL = os.path.join(VAL_PATH, "choose-50-kind-valid-number.txt")

TEST_PATH = os.path.join(RAW_PATH, "")
TEST_DATA = os.path.join(TEST_PATH, "PictureProcessed")
TEST_SUB = "videoEvaluation/WordMode/ModelInfer/result/sub_2.csv" #提交的文件
num_class = 50  #类的数量，根据需要改

def file_deal(root_path, list_file = None):
    if list_file != None:
        with open(list_file, 'r', encoding='utf-8') as f:
            lip_list = list(line.replace(',', '\t').split() for line in f.readlines())
        lip_dict = dict(
            zip(list(x[0] for x in lip_list), list(x[1] for x in lip_list)))  # 构造文件名和标签之间的字典
    else:
        lip_dict = None

    sig_names = os.listdir(root_path)
    temp = []
    for sig_name in sig_names:
        if '.' not in sig_name:
            temp.append(sig_name)
    sig_names = temp
    video_list = list(filter(lambda x: len(os.listdir(os.path.join(root_path, x))) >= 3 and len(os.listdir(os.path.join(root_path, x))) <= 24,
                                  sig_names))#有效的文件名列表
    random.shuffle(video_list)
    return lip_dict, video_list

import argparse
parser = argparse.ArgumentParser(description="PyTorch implementation of Lip classification")
parser.add_argument('--dataset', default='lip', type=str)
parser.add_argument('--modality', default='RGB', type=str, choices=['RGB', 'Flow'])
parser.add_argument('--mode', default='test', type=str, choices=['train',  'test'], help='train表示训练模型，test表示预测测试集并生成答案')
parser.add_argument('--sub', default='prob', type=str, choices=['sub', 'prob'], help='sub代表生成答案可提交，prob代表生成的答案有概率、用于模型投票融合')
parser.add_argument('--root_path', type=str, default="")
parser.add_argument('--store_name', type=str, default="TSM")
# ========================= Model Configs ==========================
parser.add_argument('--arch', type=str, default="resnet50", help='backbone的选择')
parser.add_argument('--num_segments', type=int, default=24, help='模型输入帧数的设置')
parser.add_argument('--img_size', type=int, default=184)
parser.add_argument('--consensus_type', type=str, default='avg')

parser.add_argument('--dropout', '--do', default=0.5, type=float,
                    metavar='DO', help='dropout ratio (default: 0.5)')
parser.add_argument('--loss_type', type=str, default="nll",
                    choices=['nll'])
parser.add_argument('--pretrain', type=str, default=None)
# parser.add_argument('--pretrain', type=str, default='imagenet')
parser.add_argument('--tune_from', type=str, default=None, help='fine-tune from checkpoint')
# parser.add_argument('--tune_from', type=str, default="using-model/_model_180_120.pth.tar", help='fine-tune from checkpoint')
# parser.add_argument('--tune_from', type=str, default="pretrain_model/TSM_kinetics_RGB_resnet50_shift8_blockres_avg_segment8_e50.pth", help='fine-tune from checkpoint')
# parser.add_argument('--tune_from', type=str, default="pretrain_model/TSM_kinetics_RGB_resnet50_shift8_blockres_avg_segment8_e100_dense.pth", help='fine-tune from checkpoint')
# parser.add_argument('--tune_from', type=str, default="pretrain_model/TSM_kinetics_RGB_resnet50_shift8_blockres_avg_segment16_e50.pth", help='fine-tune from checkpoint')
# parser.add_argument('--tune_from', type=str, default="pretrain_model/TSM_something_RGB_resnet101_shift8_blockres_avg_segment8_e45.pth", help='fine-tune from checkpoint')
# parser.add_argument('--tune_from', type=str, default="pretrain_model/TSM_somethingv2_RGB_resnet101_shift8_blockres_avg_segment8_e45.pth", help='fine-tune from checkpoint')


# ========================= Learning Configs ==========================
parser.add_argument('--epochs', default=660, type=int, metavar='N',
                    help='number of total epochs to run')
parser.add_argument('-b', '--batch-size', default=5, type=int,
                    metavar='N', help='mini-batch size (default: 256)')
parser.add_argument('--lr', '--learning-rate', default=0.01, type=float,
                    metavar='LR', help='initial learning rate')
parser.add_argument('--lr_type', default='cos', type=str,
                    metavar='LRtype', help='learning rate type')
parser.add_argument('--lr_steps', default=[50, 100], type=float, nargs="+",
                    metavar='LRSteps', help='epochs to decay learning rate by 10')
parser.add_argument('--momentum', default=0.9, type=float, metavar='M',
                    help='momentum')
parser.add_argument('--weight-decay', '--wd', default=1e-4, type=float,
                    metavar='W', help='weight decay (default: 5e-4)')
parser.add_argument('--clip-gradient', '--gd', default=20, type=float,
                    metavar='W', help='gradient norm clipping (default: disabled)')
parser.add_argument('--no_partialbn', '--npb', default=True, action="store_true")


# ========================= Monitor Configs ==========================
parser.add_argument('--print-freq', '-p', default=20, type=int,
                    metavar='N', help='print frequency (default: 10)')
parser.add_argument('--eval-freq', '-ef', default=5, type=int,
                    metavar='N', help='evaluation frequency (default: 5)')


# ========================= Runtime Configs ==========================
parser.add_argument('-j', '--workers', default=0, type=int, metavar='N',
                    help='number of data loading workers (default: 8)')
parser.add_argument('--resume', default='/home/hukcc/HYY-WORKSPACE/Server4Lipreader/videoEvaluation/WordMode/ModelInfer/using-model/2020.2.14model/_model_best.pth.tar', type=str, metavar='PATH',
                    help='path to latest checkpoint (default: none)')
parser.add_argument('--snapshot_pref', type=str, default="./checkpoints/")
parser.add_argument('--start-epoch', default=0, type=int, metavar='N',
                    help='manual epoch number (useful on restarts)')
parser.add_argument('--gpus', nargs='+', type=int, default=None)
parser.add_argument('--flow_prefix', default="", type=str)
parser.add_argument('--root_log',type=str, default='log')
parser.add_argument('--root_model', type=str, default='save_mode')

parser.add_argument('--shift', default=True, action="store_true", help='use shift for models')
parser.add_argument('--shift_div', default=8, type=int, help='number of div for shift (default: 8)')
parser.add_argument('--shift_place', default='blockres', type=str, help='place for shift (default: stageres)')

parser.add_argument('--temporal_pool', default=False, action="store_true", help='add temporal pooling')
parser.add_argument('--non_local', default=False, action="store_true", help='add non local block')

