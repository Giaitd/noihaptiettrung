package com.giaitd.testsensor.DIDOModule;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.giaitd.testsensor.Program.Globals;

import java.util.List;
import java.util.TimerTask;

import asim.sdk.locker.DeviceInfo;
import asim.sdk.locker.SDKLocker;

public class ReadDIDO {

    public static TimerTask getDIDOTask(Context context){
        return new TimerTask() {
            public void run() {
                Globals.mTimerHandler.post(() -> {
                    SdkDIDOModule DOSdk = new SdkDIDOModule();
                    Log.d("testing", "getDOTask");

                    List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(context);
                    for (DeviceInfo each : devices) {
                        boolean connect = DOSdk.connect(context, each, 9600);
                        if (connect) {
                            Globals.dOData = DOSdk.getDOData();
                            Log.d("testing", "getDOTask");
                        }

                        connect = DOSdk.connect(context, each, 9600);
                        if (connect) {
                            Globals.dIData = DOSdk.getDIData();
                            Log.d("testing", "getDITask");
                        }
                    }
                });
            }
        };
    }

}


