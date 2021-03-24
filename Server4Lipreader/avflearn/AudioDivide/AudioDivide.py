import matlab.engine
eng = matlab.engine.start_matlab()
def getQuantile(path):
    
    eng.cd('/home/hukcc/HYY-WORKSPACE/Server4Lipreader/avflearn/AudioDivide',nargout=0)
    eng.addpath('/home/hukcc/HYY-WORKSPACE/Server4Lipreader/avflearn/AudioDivide',nargout=0)
    eng.addpath('/home/hukcc/HYY-WORKSPACE/Server4Lipreader/store/AVF-LearnStore/AudioClips',nargout=0)
    # y_raw,Fs = eng.audioread("/home/hukcc/HYY-WORKSPACE/funcs/tongguo.wav", nargout=2)
    size_scaler_raw =100000
    Q1,Q2,Q3, size_scaler_raw = eng.test(path,nargout=4)
    if type(Q1) != float or type(Q2) != float or type(Q3) != float:
        return 0.3,0.5,0.7
    totalLenth = float(size_scaler_raw.size[0])
    return float(str(Q1))/totalLenth , float(str(Q2))/totalLenth , float(str(Q3))/totalLenth

if __name__ == "__main__":
    q1,q2,q3 = getQuantile("/home/hukcc/HYY-WORKSPACE/funcs/tongguo110.wav")
    print([q1,q2,q3])