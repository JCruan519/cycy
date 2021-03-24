package com.chunxinweiyu.newlipreader.FunctionPart.BaseClass;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import static android.support.constraint.Constraints.TAG;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    public  SurfaceHolder mHolder;
    private Camera        mCamera;
    public  Context       context;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.context = context;
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // 通知摄像头可以在这里绘制预览了
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // 什么都不做，但是在Activity中Camera要正确地释放预览视图
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // 如果预览视图可变或者旋转，要在这里处理好这些事件
        // 在重置大小或格式化时，确保停止预览

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // 变更之前要停止预览
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // 在这里重置预览视图的大小、旋转、格式化

        // 使用新设置启动预览视图
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
