package com.chunxinweiyu.newlipreader.FunctionPart.BaseClass;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadTools {
    private OkHttpClient okHttpClient;
    private UploadTools(){
        okHttpClient = new OkHttpClient();
    }
    /**
     * 使用静态内部类的方式实现单例模式
     */
    private static class UploadUtilInstance{
        private static final UploadTools INSTANCE = new UploadTools();
    }
    public static UploadTools getInstance(){
        return UploadUtilInstance.INSTANCE;
    }

    /**
     * @param url   服务器地址
     * @param file  所要上传的文件
     * @return      响应结果
     * @throws IOException
     */
    public boolean upload(String url, File file, String videoID) throws IOException {
        OkHttpClient client = new OkHttpClient();
        boolean flag = false;
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", videoID+".mp4",// file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "ClientID" + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        try {
            if (!response.isSuccessful()){
                flag = false;
                throw new IOException("Unexpected code " + response);
            }
            else{
                flag = true;
            }

            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //        if (!response.isSuccessful())
        //            throw new IOException("Unexpected code " + response);
        return flag;
    }
    public boolean upload_pic(String url, File file, String picID) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        //        OkHttpClient client = new OkHttpClient();
        final boolean[] flag = {false};
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", picID+".png",// file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "ClientID" + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        //        Call call = client.newCall(request);
        //        call.enqueue(new Callback() {
        //            @Override
        //            public void onFailure(Call call, IOException e) {
        //                flag[0] = false;
        //                Log.d("post", "----------fail----------");
        //            }
        //
        //            @Override
        //            public void onResponse(Call call, Response response) throws IOException {
        //                flag[0] = true;
        //            }
        //        });
        try {
            if (!response.isSuccessful()){
                flag[0] = false;
                throw new IOException("Unexpected code " + response);
            }
            else{
                flag[0] = true;
            }

            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //        if (!response.isSuccessful())
        //            throw new IOException("Unexpected code " + response);
        return flag[0];
    }
}
