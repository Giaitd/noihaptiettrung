package com.giaitd.testsensor;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.giaitd.testsensor.DIDOModule.DIDOModule;
import com.giaitd.testsensor.DIDOModule.DIDOData;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import asim.sdk.locker.DeviceInfo;
import asim.sdk.locker.SDKLocker;


public class MainActivity extends AppCompatActivity {
    
    
    TextView textView3, textView2,textView1 ;
    Button btnButton,btnOff, button;
    Handler mTimerHandler = new Handler();
    public static boolean checkConnect;
    int num = 0;


//        @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//    };


    /**
    * Read sensor
    */

//    public Timer timerSensor;
//    public TimerTask timerTaskSensor;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        tempView = findViewById(R.id.tempView);
//
//        Timer timerSensor = new Timer();
//        TimerTask timerTaskSensor = new TimerTask() {
//            @Override
//            public void run() {
//                mTimerHandler.post(new Runnable() {
//                    public void run() {
//                        Log.i("Supervisor", "======Collect data from Sensor=====");
//                        SDKExtend tempHuSDK = new SDKExtend();
//
//                        TempHumiData tempHuData = null;
//
//                        List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(getApplicationContext());
//                        for (DeviceInfo each : devices) {
//                            boolean connect = tempHuSDK.connect(getApplicationContext(), each, 9600);
//                            if (connect) {
//                                tempHuData = tempHuSDK.getTempHumiData();
//                                //tempHuSDK.disconnect();
//                                tempView.setText((int) tempHuData.temperature + "");
//
//                            }
//                        }
//                    }
//                });
//
//            }
//        };
//        timerSensor.schedule(timerTaskSensor,0l,1000l);
//
//    }

    /**
    Read DI,DO Coil
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView3 = findViewById(R.id.textView3);
        textView2 = findViewById(R.id.textView2);
        textView1 = findViewById(R.id.textView1);
        btnButton = findViewById(R.id.btnButton);

        Timer timerDI = new Timer();
        TimerTask timerTaskDI = new TimerTask() {
            @Override
            public void run() {
                mTimerHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {

                        Log.i("Supervisor", "======Collect data from Sensor=====");
                        DIDOModule DISdk = new DIDOModule();
                        DIDOData DIDOData = null;


                        List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(getApplicationContext());
                        for (DeviceInfo each : devices) {
                            boolean connect = DISdk.connect(getApplicationContext(), each, 9600);
                            if (connect) {
                                DIDOData = DISdk.getDIData();

                                textView3.setText(DIDOData.valueRead3 + "");
//                                textView2.setText(diData.valueRead2 + "");
//                                textView1.setText(diData.valueRead1 + "");



                            }
                        }
                    }
                });

            }
        };

        timerDI.schedule(timerTaskDI,0l,1000l);

        Timer timerDO = new Timer();
        TimerTask timerTaskDO = new TimerTask() {
            @Override
            public void run() {
                mTimerHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {

                        Log.i("Supervisor", "======Collect data from Sensor=====");
                        DIDOModule DISdk = new DIDOModule();
//                        DIData diData = null;
                        DIDOData doData = null;

                        List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(getApplicationContext());
                        for (DeviceInfo each : devices) {
                            boolean connect = DISdk.connect(getApplicationContext(), each, 9600);
                            if (connect) {
//                                diData = DISdk.getDIData();
//
//                                textView3.setText(diData.valueRead3 + "");
//                                textView2.setText(diData.valueRead2 + "");
//                                textView1.setText(diData.valueRead1 + "");

                                doData = DISdk.getDOData();

//                                textView3.setText(doData.valueDO3 + "");
//                                textView2.setText(doData.valueDO2 + "");
                                textView1.setText(doData.valueRead1 + "");

                            }
                        }
                    }
                });

            }
        };

        timerDO.schedule(timerTaskDO,0l,1000l);
    }


    /**
    Write DO
    */

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        textView3 = findViewById(R.id.textView3);
//        textView2 = findViewById(R.id.textView2);
//        button = findViewById(R.id.button);
//
//    }


    public void countUp(View view) {
        DIDOModule.bufferAll = new byte[]{1, 5, 0, 1, -1, 0, -35, -6};
        writeDO(view);
    }


    public void countDown(View view){
        DIDOModule.bufferAll = new byte[]{1, 5, 0, 1, 0, 0, -100, 10};
        writeDO(view);
    }


    public void writeDO(View view) {

        DIDOModule DOSdk = new DIDOModule();

        List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(getApplicationContext());
        for (DeviceInfo each : devices) {
            boolean connect = DOSdk.connect(getApplicationContext(), each, 9600);
            if (connect) {
                DOSdk.setDOData();
            }
        }
    }


}












