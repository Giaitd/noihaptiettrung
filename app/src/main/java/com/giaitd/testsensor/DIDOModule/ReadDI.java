package com.giaitd.testsensor.DIDOModule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import asim.sdk.locker.DeviceInfo;
import asim.sdk.locker.SDKLocker;

public class ReadDI {
    private static Handler mTimerHandler;

    public static TimerTask getDITask(String m_androidId, Context context){
        return new TimerTask() {
            public void run() {

                mTimerHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {

                        Log.i("Supervisor", "======Collect data from Digital Input=====");
                        DIDOModule DISdk = new DIDOModule();
                        DIData diData = null;

                        List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(context);
                        for (DeviceInfo each : devices) {
                            boolean connect = DISdk.connect(context, each, 9600);
                            if (connect) {
                                diData = DISdk.getDIData();
                            }
                        }
                    }
                });
            }
        };
    }

}


