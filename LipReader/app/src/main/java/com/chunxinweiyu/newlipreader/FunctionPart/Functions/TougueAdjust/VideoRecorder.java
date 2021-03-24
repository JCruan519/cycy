package com.chunxinweiyu.newlipreader.FunctionPart.Functions.TougueAdjust;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.PlaybackParams;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.chunxinweiyu.newlipreader.FunctionPart.BaseClass.CameraPreview;
import com.chunxinweiyu.newlipreader.FunctionPart.BaseClass.CameraTools;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class VideoRecorder {
    //这个类用来录制所有TougueAdjust功能下的视频 主要通过MediaRecorder来实现
    private boolean       isRecording                = false;
    private CameraPreview mCameraPreview;
    private FrameLayout   preview;
    private Camera        camera;
    private MediaRecorder recorder                   = null;
    //用于Recorder和Player之间的交互
    public  boolean       isPreviewing               = false;
    public  boolean       recordDone                 = false;
    //用于和Network交互
    public String        videoId                    = null;
    //适当改造成要发送的名字
    public String label = "";

    //录像倒计时
    private        CountDownTimer countDownTimer;
    private        ProgressBar    progressBar;
    private int limitTime = 2000;   // 单位ms 这是录像时长

    //通用网络交互(为了调用回传的分数智能先写成public)
    public NetworkFunctions mNetworkFunctions = new NetworkFunctions();

    //视频录制与暂停的方法
    public void startMediaCorder(final View view){
        ImageButton button= (ImageButton) view;
        if (!isRecording) {
            if (recorder==null){
                //实例化媒体录制器
                recorder = new MediaRecorder();

            }

            if (prepareVideoRecorder()){
                videoId = UUID.randomUUID().toString()+"_"+label;

                recorder.start();
                recordDone = false;
                //好吧 这里我放弃解耦倒计时和录像了 感觉可以但是没必要
                progressBar.setProgress(0);
                countDownTimer.start();
            }
        }
        else{
            stopRecord();
//            networkFunc(view);
        }




        isRecording=!isRecording;
    }

    private boolean prepareVideoRecorder() {

        if(camera == null){
            camera = CameraTools.getCameraInstance();
        }
        recorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        camera.unlock();
        recorder.setCamera(camera);
        recorder.setOrientationHint(270);   //调整录像时候的角度！！！！！ 否则按照默认的相机安装角度录像

        // Step 2: Set sources
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        File dataDir = new File(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir");
        if(!dataDir.exists()){
            // TODO: 2020/6/24 目前演示视频文件都还是手动复制的 后面需要增加联网下载视频的功能 
            dataDir.mkdirs();
        }
        recorder.setOutputFile(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/record.mp4");

        // Step 5: Set the preview output
        recorder.setPreviewDisplay(mCameraPreview.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            Log.d("", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            recorder.release();
            recorder = null;
            return false;
        } catch (IOException e) {
            Log.d("", "IOException preparing MediaRecorder: " + e.getMessage());
            recorder.release();
            recorder = null;
            return false;
        }
        return true;
    }

    public void stopRecord(){
        //必须先置空 否则在快速的视频录制时会报错
        recorder.setOnErrorListener(null);
        recorder.setOnInfoListener(null);
        recorder.setPreviewDisplay(null);
        try {
            recorder.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recorder.release();
        recorder = null;
        recordDone = true;
    }

    public void prepareCameraAndStartPreview(Context context, FrameLayout camPreview){
        preview = camPreview;
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            camera= CameraTools.getCameraInstance();
            camera.setDisplayOrientation(90);
            mCameraPreview = new CameraPreview(context, camera);
            preview.addView(mCameraPreview);
        }
        else{
            Log.d("CAMERA", "check camera fail");
        }
    }

    public void releaseCameraAndStopPreview(){
        preview.removeAllViews();
        if (recorder != null)  stopRecord();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


    public void setCountDown(final ProgressBar mProgressBar){
        progressBar = mProgressBar;
        final int progress = progressBar.getProgress();
        final int maxProgress = progressBar.getMax();

        countDownTimer = new CountDownTimer(limitTime, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished > limitTime*0.75){
                    progressBar.setProgress(0);
                }
                else{
                    progressBar.incrementProgressBy(1);
                }

            }

            @Override
            public void onFinish() {
                progressBar.setProgress(maxProgress);
                if (isRecording){
                    stopRecord();
//                    networkFunc(getView());
                    mNetworkFunctions.networkFunc(recordDone,videoId);
                    isRecording =! isRecording;

                }

            }
        };
    }
}
