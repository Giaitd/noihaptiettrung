package com.giaitd.testsensor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import asim.sdk.locker.DeviceInfo;
import asim.sdk.locker.SDKLocker;
import asim.sdk.tempandhum.TempHumiData;



public class MainActivity extends AppCompatActivity {
    TextView tempView;
    Handler mTimerHandler = new Handler();

//    public Timer timerSensor;
//    public TimerTask timerTaskSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempView = findViewById(R.id.tempView);

        Timer timerSensor = new Timer();
        TimerTask timerTaskSensor = new TimerTask() {
            @Override
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        Log.i("Supervisor", "======Collect data from Sensor=====");
                        SDKExtend tempHuSDK = new SDKExtend();

                        TempHumiData tempHuData = null;

                        List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(getApplicationContext());
                        for (DeviceInfo each : devices) {
                            boolean connect = tempHuSDK.connect(getApplicationContext(), each, 9600);
                            if (connect) {
                                tempHuData = tempHuSDK.getTempHumiData();
                                //tempHuSDK.disconnect();
                                tempView.setText((int) tempHuData.temperature + "");

                            }
                        }
                    }
                });

            }
        };
        timerSensor.schedule(timerTaskSensor,0l,1000l);

    }







}












