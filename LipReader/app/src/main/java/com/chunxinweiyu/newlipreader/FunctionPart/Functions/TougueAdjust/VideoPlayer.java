package com.chunxinweiyu.newlipreader.FunctionPart.Functions.TougueAdjust;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class VideoPlayer {
    //这个类用于播放所有TougueAdjust模块下的视频 主要利用MediaPlayer来实现
    //播放界面&播放器
    private        SurfaceHolder surfaceHolder  = null;
    private        MediaPlayer   player         = null;
    //暂停前视频位置记录
    private static int           currentPosition;
    //演示视频倍速播放设置
    private        boolean       videoSpeedMode = false;
    //用于Recorder和Player之间的交互
    public         boolean       isPreviewing   = false;
    public         boolean       recordDone     = false;

    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder, final String videoName){
        surfaceHolder = mSurfaceHolder;
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                prepaewAndStartVideo(videoName);

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

    public void slowModePlayer(View view, final Context context){
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
                Toast toast=Toast.makeText(context,tempText,Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void prepaewAndStartVideo(String videoName){
        if (player == null){
            player = new MediaPlayer();
            player.reset();
            try {
                player.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/"+videoName+".mp4");
                player.setDisplay(surfaceHolder);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //自动播放
                        player.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void restartVideo(String videoName) {

        player.reset();
        try {
            player.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/"+videoName+".mp4");
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

}
