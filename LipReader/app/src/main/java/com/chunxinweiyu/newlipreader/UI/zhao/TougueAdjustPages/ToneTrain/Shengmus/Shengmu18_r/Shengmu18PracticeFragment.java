package com.chunxinweiyu.newlipreader.UI.zhao.TougueAdjustPages.ToneTrain.Shengmus.Shengmu18_r;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chunxinweiyu.newlipreader.FunctionPart.BaseClass.CongratulationDialog;
import com.chunxinweiyu.newlipreader.FunctionPart.Functions.TougueAdjust.VideoPlayer;
import com.chunxinweiyu.newlipreader.FunctionPart.Functions.TougueAdjust.VideoRecorder;
import com.chunxinweiyu.newlipreader.R;

public class Shengmu18PracticeFragment extends Fragment {
    //示范视频标签
    private String               demoVideoName         = "r";
    //上传视频标签
    private String               uploadVideoName       = "shengmu_18";
    //弹窗对象
    private CongratulationDialog mCongratulationDialog;
    //当前模块对应的视频播放控制器
    private VideoPlayer          shengmu18VideoPlayer   = new VideoPlayer();
    //当前模块对应的视频录制控制器
    private VideoRecorder        shengmu18VideoRecorder = new VideoRecorder();
    //最后显示在页面上的分数
    private double               finalScore;
    //分数字符串
    private TextView             scoreString;

    public Shengmu18PracticeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shengmu18_practice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 返回按钮监听
        View goBackBtn = view.findViewById(R.id.go_back_btn);
        setGoBackBtn(goBackBtn);
        //录像按钮监听
        View startPracticeBtn = view.findViewById(R.id.recordandstop);
        setStartPracticeBtn(startPracticeBtn);
        //录像按钮监听
        View replayVideoBtn = view.findViewById(R.id.replay_video_btn);
        setReplayVideoBtn(replayVideoBtn);
        //录像按钮监听
        View replayRecordBtn = view.findViewById(R.id.replay_record_btn);
        setReplayRecordBtn(replayRecordBtn);
        //实例化自定义的dialog
        mCongratulationDialog = new CongratulationDialog(getActivity(),R.layout.congratulation_dialog_layout,new int[]{R.id.quitDialogBtn});
        //演示视频播放器设置
        SurfaceView videoPlaysv = view.findViewById(R.id.video_play_sv);
        setShengmu1VideoPlayer(videoPlaysv);
        //设置这个模块返回的视频尾部标签
        shengmu18VideoRecorder.label = uploadVideoName;
        //相机预览界面设置
        FrameLayout camPreview = view.findViewById(R.id.camera_preview);
        shengmu18VideoRecorder.prepareCameraAndStartPreview(getContext(), camPreview);
        //进度条读条&录像倒计时
        ProgressBar progressBar;
        progressBar = view.findViewById(R.id.progress_bar);
        shengmu18VideoRecorder.setCountDown(progressBar);
        //分数显示绑定
        scoreString = view.findViewById(R.id.scoreString);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shengmu18VideoRecorder.releaseCameraAndStopPreview();
    }

    protected void setGoBackBtn(View goBackBtn){
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed(); //销毁自己返回压栈的上一个fragment
            }
        });
    }

    private void setReplayVideoBtn(final View replayVideoBtn){
        replayVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shengmu18VideoPlayer.restartVideo(demoVideoName);
            }
        });

    }

    private void setReplayRecordBtn(final View replayRecordBtn){
        replayRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shengmu18VideoPlayer.stratRecordedPreview();
            }
        });

    }

    private void setStartPracticeBtn(final View startPracticeBtn){
        startPracticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shengmu18VideoRecorder.startMediaCorder(startPracticeBtn);
                shengmu18VideoPlayer.recordDone = true;
                waitStatusUpdate();
                flashScore(); //这个位置还没进行过测试
                //显示
                // TODO: 2020/5/2 这里弹窗的逻辑要改 应该是收到服务器的回传数据之后再弹 先注释掉
                //                mCongratulationDialog.show();
            }
        });

    }

    private void setShengmu1VideoPlayer(SurfaceView videoPlaysv){
        shengmu18VideoPlayer.slowModePlayer(videoPlaysv, getActivity());
        shengmu18VideoPlayer.setSurfaceHolder(videoPlaysv.getHolder(), demoVideoName);
    }

    // TODO: 2020/5/5  在哪调用会比较合适呢？
    private void flashScore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    double score = shengmu18VideoRecorder.mNetworkFunctions.score;
                    if (score != 0 && shengmu18VideoRecorder.mNetworkFunctions.uploadFlag){
                        finalScore = score;
                        shengmu18VideoRecorder.mNetworkFunctions.score = 0;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                scoreString.setText("您的练习得分为：" + String.format("%.2f", finalScore));
                            }
                        });
                        break;
                    }
                }
            }
        }).start();
    }

    private void waitStatusUpdate(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreString.setText("评估中，请稍候");
            }
        });
    }

}

