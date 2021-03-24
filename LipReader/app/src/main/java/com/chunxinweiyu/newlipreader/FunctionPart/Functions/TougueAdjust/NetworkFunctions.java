package com.chunxinweiyu.newlipreader.FunctionPart.Functions.TougueAdjust;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chunxinweiyu.newlipreader.FunctionPart.BaseClass.UploadTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkFunctions {
    // TODO: 2020/5/2 解耦基本完成 实际通信功能仍需调试 介于我采用的是异步的方式所以比较难调 往后放一放
    //TougueAdjust模块下的所有涉及通信的功能全部都放在这一块 主要通过OKHttp实现

    //服务器ip以及端口 视情况更改(改成你实际使用的服务端地址！)
    private String url = "http://192.168.31.206:5000/upload";
    //用来与UI交互
    public double score = 0;
    //控制是否停止GET请求
    private boolean getFlag = false;
    //标志上传是否成功(同样是为了与目标界面的分数刷新进行交互才写成public)
    public boolean uploadFlag = false;


    //POST方法上传视频
    public boolean upload(String videoID) {
        //实例化上传文件位置
        File uploadFile = new File(Environment.getExternalStorageDirectory().getPath()+"/lipreader_data_dir/record.mp4");

        OkHttpClient client = new OkHttpClient();
        final boolean[] flag = {false};
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", videoID+".mp4",// file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "ClientID" + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                flag[0] = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                flag[0] = true;
                uploadFlag = true;
            }
        });
        return flag[0];
    }

    //get方法向服务器获取视频处理结果
    public boolean doGet(final String videoId) {

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
                        score = jsonObject.getDouble(videoId);
                        System.out.println(score);
                        flag[0] = true;

//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                scoreString.setText(String.format(targetScoreString,score));
//                            }
//                        });


                    }
                    else {
                        getFlag = false;
                        score = 0;
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

    //练习时POST&GET统一调用
    public void networkFunc(boolean recordDone, final String videoId) {
        // TODO: 2020/2/5 使用OKhttp框架将视频发送给电脑（flask）！
        if (recordDone) {
            upload(videoId);
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
                        int cnt = 0;
                        while (!getFlag) {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            doGet(videoId);
                            if (cnt>15) break;
                            cnt++;
                        }
                        getFlag = false;

                    }
                }
            }).start();

        }

    }
}
