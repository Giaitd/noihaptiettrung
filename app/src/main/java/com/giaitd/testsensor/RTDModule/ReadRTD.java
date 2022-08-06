package com.giaitd.testsensor.RTDModule;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.giaitd.testsensor.Program.Globals;

import java.util.List;
import java.util.TimerTask;

import asim.sdk.locker.DeviceInfo;
import asim.sdk.locker.SDKLocker;

public class ReadRTD {


//    public static TimerTask getRTDTask(String m_androidId, Context context){
//        return new TimerTask() {
//            public void run() {
//                Globals.mTimerHandler.post(() -> {
//                    SdkRTD tempSDK = new SdkRTD();
//                    Log.d("===testing====", "getRTDTask");
//                    List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(context);
//                    for (DeviceInfo each : devices) {
//                        boolean connect = tempSDK.connect(context, each, 9600);
//                        if (connect) {
//                            Globals.tempData = tempSDK.getTempData();
//                            Globals.temperature = Globals.tempData.temperature + Globals.tempOffSet;
//                        }
//
//                    }
//                });
//            }
//        };
//    }

    public static TimerTask getRTDTask(Context context){
        return new TimerTask() {
            public void run() {
                Globals.mTimerHandler.post(() -> {
                    SdkRTD tempSDK = new SdkRTD();
                    Log.d("===testing====", "getRTDTask");
                    List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(context);
                    for (DeviceInfo each : devices) {
                        boolean connect = tempSDK.connect(context, each, 9600);
                        if (connect) {
                            Globals.tempData = tempSDK.getTempData();
                            Globals.temperature = Globals.tempData.temperature + Globals.tempOffSet;
                        }

                    }
                });
            }
        };
    }
}
