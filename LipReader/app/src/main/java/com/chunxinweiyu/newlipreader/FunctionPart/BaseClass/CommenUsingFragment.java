package com.chunxinweiyu.newlipreader.FunctionPart.BaseClass;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.PlaybackParams;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommenUsingFragment extends BaseFragment {
    private        SurfaceView    sv1;
    private        SurfaceView   cameraPreview;
    private        MediaPlayer   player                     = null;
    private        MediaRecorder recorder                   = null;
    private static int           currentPosition;
    private        SurfaceHolder surfaceHolder              = null;
    private        SurfaceHolder cameraPreviewSurfaceHolder = null;
    private        boolean       isRecording                = false;
    private        CameraPreview mCameraPreview;
    private        FrameLayout   preview;
    private        Camera        camera;
    private        boolean       isPreviewing               = false;
    private        boolean       recordDone                 = false;
    private        File          file                       = null;
    //    服务器地址
    private        String        path                       = "http://192.168.31.68:5000/upload";
    private        String        videoId                    = null;
    private        boolean       uploadFlag                 = false;
    private        double        score                      = 0;
    private        TextView       scoreString;
    private        boolean        getFlag                    = false;
    private        boolean        videoRecordPrepareFlag     = false;
    protected      boolean        firstFlag                  = true;
    //录像倒计时
    //    private TextView countDownStr;
    private        CountDownTimer countDownTimer;
    private        ProgressBar    progressBar;
    private int limitTime = 1500;   // 单位ms
    //演示视频倍速播放设置
    private        boolean        videoSpeedMode = false;
    //崩溃补丁
    private        boolean        quitFlag=false;

    //适当改造成要发送的名字
    public String label = "";

    final String targetScoreString = "";


    @Override
    public void onDestroy() {
        super.onDestroy();
        getFlag = true; //取消该页面的get请求
        quitFlag = true;
    }
    @Override
    protected void onFragmentFirstVisible(View view) {
        super.onFragmentFirstVisible(view);
//        preview = view.findViewById(R.id.camera_preview);   //应该在集继承的对象里绑定！

    }
    @Override
    protected void onFragmentVisibleChange(boolean isVisible, View view) {
        super.onFragmentVisibleChange(isVisible, view);
        if (isVisible){
            prepareCameraAndStartPreview();
            if (player != null){
                restartVideo();
            }
            getFlag = false;

        }else {
            releaseCameraAndStopPreview();
            getFlag = true; //取消该页面的get请求
        }
    }
    public CommenUsingFragment() {
        // Required empty public constructor
    }
    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder){
        surfaceHolder = mSurfaceHolder;
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                prepaewAndStartVideo();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //todo: 明早处理一下切换页面的暂停问题吧

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                stopVideo();

            }
        });
    }
    public void slowModePlayer(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoSpeedMode = !videoSpeedMode;
                String tempText;
                if (videoSpeedMode){
                    tempText = "慢速播放模式开";
                }else{
                    tempText = "慢速播放模式关";
                }
                Toast toast=Toast.makeText(getActivity(),tempText,Toast.LENGTH_LONG    );
                //                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
    public void prepaewAndStartVideo(){
        if (player == null){
            player = new MediaPlayer();
            player.reset();
            //                    Log.d("fragment_1_1", "-----------"+Environment.getExternalStorageDirectory()+"-----------");
            try {
                player.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/exp1.mp4");

                player.setDisplay(surfaceHolder);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //                        player.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void prepareCameraAndStartPreview(){
        //         TODO: 2020/2/3 暂时采用mediarecoorder+surfacepreview的方式 这样看起来会简单一点

        if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            camera= CameraTools.getCameraInstance();
            camera.setDisplayOrientation(90);
            mCameraPreview = new CameraPreview(getContext(), camera);
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
    public void restartVideo() {

        player.reset();
        try {
            player.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/exp1.mp4");
            if (videoSpeedMode){
                player.setPlaybackParams(new PlaybackParams().setSpeed(0.5f));
            }

            player.setDisplay(surfaceHolder);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        isPreviewing = false;


    }
    public void pauseVideo(){
        if (player != null){
            player.pause();
        }
    }
    public void stopVideo(){
        if (player != null){
            currentPosition = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }
    }
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
                progressBar.setProgress(0);
                countDownTimer.start();
            }
        }
        else{
            stopRecord();
            networkFunc(view);
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
    public void  stratRecordedPreview(){
        if (player == null){
            player = new MediaPlayer();
        }
        if (recordDone){
            player.reset();
            try {
                player.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/record.mp4");
                if (videoSpeedMode){
                    player.setPlaybackParams(new PlaybackParams().setSpeed(0.5f));
                }

                player.setDisplay(surfaceHolder);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        player.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            isPreviewing = true;


        }
    }
    /**
     * 创建线程实现文件的上传
     * @param view
     */
    public void upload(View view, final String videoID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String pathname = Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/record.mp4";
                    file = new File(pathname);
                    if (file.exists()){
                        uploadFlag = UploadTools.getInstance().upload(path,file,videoID);


                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
    //get方法
    public boolean doGet(String url, String videoID) {

        OkHttpClient okHttpClient = new OkHttpClient();
        final boolean[] flag = {false};
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d("get", "----------fail----------");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseData = response.body().string();

                try{
                    if(responseData != null && responseData.startsWith("\ufeff")){

                        String json = responseData.substring(responseData.indexOf("{"), responseData.lastIndexOf("}") + 1);
                        responseData = json;
                    }

                    System.out.println(responseData);
                    JSONObject jsonObject=new JSONObject(responseData);
                    System.out.println(videoId);
                    if (jsonObject.has(videoId)){
                        getFlag = true;
                        score=jsonObject.getDouble(videoId);
                        System.out.println(score);
                        flag[0] = true;
//                        if (!quitFlag){
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    scoreString.setText(String.format(targetScoreString,score));
//                                }
//                            });
//                        }

                    }
                    else {
                        getFlag = false;
//                        if (!quitFlag){
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    scoreString.setText("处理中，请不要离开本页面");
//                                }
//                            });
//                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return flag[0];
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
    public void networkFunc(View view){
        // TODO: 2020/2/5 使用OKhttp框架将视频发送给电脑（flask）！
        if (recordDone) {
            upload(view, videoId);
            new Thread(new Runnable() {
                @SuppressLint("StringFormatMatches")
                @Override
                public void run() {
                    while (uploadFlag == false) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (uploadFlag != false) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "上传成功", Toast.LENGTH_LONG).show();
                            }
                        });

                        int cnt = 0;
                        while (!getFlag) {
                            doGet(path,videoId);
                            if (cnt>50) break;
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            cnt++;
                        }
                        getFlag = false;
                        videoId = null;
                        // TODO: 2020/2/11 在这里get了数据之后要找对应videoId 如果没有的话就一直做get？（中间肯定还是要休眠的）这个还没完成 把云端的完成之后再回来改
                        // TODO: 2020/2/12 最后的分数现在是已经保存在score变量里面了


                    }
                }
            }).start();



            //使用完之后 清空

            uploadFlag = false;
        }

    }
    public void setCountDown(final ProgressBar mProgressBar){
        progressBar = mProgressBar;
        final int progress = progressBar.getProgress();
        final int maxProgress = progressBar.getMax();

        countDownTimer = new CountDownTimer(limitTime, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                //                countDownStr.setText("剩余时间(" + millisUntilFinished/10 + ")");
                progressBar.incrementProgressBy(1);
            }

            @Override
            public void onFinish() {
                //                countDownStr.setText("时间到，点击重新开始录制");
                progressBar.setProgress(maxProgress);
                if (isRecording){
                    stopRecord();
                    networkFunc(getView());
                    isRecording =! isRecording;

                }

            }
        };
    }
}
