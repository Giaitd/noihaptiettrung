package com.giaitd.testsensor;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;
import java.util.TimerTask;

import asim.sdk.locker.DeviceInfo;
import asim.sdk.locker.SDKLocker;
import asim.sdk.tempandhum.SDKTemperatureAndHumidity;
import asim.sdk.tempandhum.TempHumiData;

public class SensorTask {

        public static TimerTask getTimerTask(Context context) {
                return new TimerTask() {

                        public void run() {

                                Log.i("Supervisor", "======Collect data from Sensor=====");
                                SDKExtend tempHuSDK = new SDKExtend();

                                TempHumiData tempHuData = null;

                                List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(context);
                                //List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(getApplicationContext());
                                for (DeviceInfo each : devices) {
                                        boolean connect = tempHuSDK.connect(context, each, 9600);
                                        if (connect) {
                                                tempHuData = tempHuSDK.getTempHumiData();
                                                //tempHuSDK.disconnect();

                                        }
                                }
                        }
                };
        }
}
