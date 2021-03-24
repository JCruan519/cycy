# coding=utf-8
import pyAudioAnalysis
import pyAudioAnalysis.audioBasicIO as audioBasicIO
import pyAudioAnalysis.ShortTermFeatures as sF
import os
import glob
from pydub import AudioSegment
import re

def loadAudio(path):
    Fs, x = audioBasicIO.read_audio_file(path)
    x = audioBasicIO.stereo_to_mono(x)
    return Fs, x

def getTXT(file):
    pattern = re.compile(r'([^<>/\\\|:""\*\?]+)\.\w+$')
    fileName = pattern.findall(file)[0]

    # mp4 to wav
    wav_filename = fileName + '.wav'
    AudioSegment.from_file(file).export('store/AVF-LearnStore/AudioClips/' + wav_filename, format='wav')
    wavPath = 'store/AVF-LearnStore/AudioClips/' + wav_filename
    # wav to txt(getFeature)
    Fs, x = loadAudio('store/AVF-LearnStore/AudioClips/' + wav_filename)
    print(Fs, x)
    st_features, st_features_name = sF.feature_extraction(x, Fs, 0.050 * Fs, 0.025 * Fs, deltas=False)
    outputFile = open('store/AVF-LearnStore/AudioClipsTxt/' + fileName + '.txt', 'w')
    for col in range(st_features.shape[1]):
        sampleFeature = []
        for row in range(st_features.shape[0]):
            feature = st_features[row][col]
            sampleFeature.append(feature)
        sampleString = str(sampleFeature).replace('[', '').replace(']', '')
        outputFile.write(sampleString + '\n')
    outputFile.close()
    txtPath = 'store/AVF-LearnStore/AudioClipsTxt/' + fileName + '.txt'
    return txtPath,wavPath
