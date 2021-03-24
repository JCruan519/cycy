package com.chunxinweiyu.newlipreader.FunctionPart.BaseClass;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

public class CameraTools {


    public boolean checkCameraHardware(Context context) {
        // 支持所有版本
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            // 在多个摄像头时，默认打开后置摄像头
            //            c = Camera.open();
            // Android 2.3（API 9之后可指定cameraId摄像头id，可选值为后置（CAMERA_FACING_BACK）/前置（ CAMERA_FACING_FRONT）
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);

            //            int cametacount=Camera.getNumberOfCameras();
            //            c=Camera.open(cametacount-1);
        } catch (Exception e){
            // Camera被占用或者设备上没有相机时会崩溃。
            Log.d("CAMERA", "can not get camera");
        }
        return c;  // returns null if camera is unavailable
    }
}

