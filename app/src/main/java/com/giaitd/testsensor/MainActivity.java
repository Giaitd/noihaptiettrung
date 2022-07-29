package com.giaitd.testsensor;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.giaitd.testsensor.DIDOModule.DIDOModule;
import com.giaitd.testsensor.DIDOModule.DIData;
import com.giaitd.testsensor.DIDOModule.ReadDI;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import asim.sdk.locker.DeviceInfo;
import asim.sdk.locker.SDKLocker;


public class MainActivity extends AppCompatActivity {
    
    
    TextView textView3, textView2,textView1 ;
    Button btnButton,btnOff;
    //Handler mTimerHandler = new Handler();
    public static boolean checkConnect;



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    };



// read sensor
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


//read DI
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        textView3 = findViewById(R.id.textView3);
//        textView2 = findViewById(R.id.textView2);
//        textView1 = findViewById(R.id.textView1);
//        btnButton = findViewById(R.id.btnButton);
//
//        Timer timerDI = new Timer();
//        TimerTask timerTaskSensor = new TimerTask() {
//            @Override
//            public void run() {
//                mTimerHandler.post(new Runnable() {
//                    @SuppressLint("SetTextI18n")
//                    public void run() {
//
//                        Log.i("Supervisor", "======Collect data from Sensor=====");
//                        DIDOModule DISdk = new DIDOModule();
//                        DIData diData = null;
//
//                        List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(getApplicationContext());
//                        for (DeviceInfo each : devices) {
//                            boolean connect = DISdk.connect(getApplicationContext(), each, 9600);
//                            if (connect) {
//                                diData = DISdk.getDIData();
//
//                                int num3 = diData.valueRead3;
//                                int num2 = diData.valueRead2;
//                                int num1 = diData.valueRead1;
//                                textView3.setText(num3 + "");
//                                textView2.setText(num2 + "");
//                                textView1.setText(num1 + "");
//
//                            }
//                        }
//                    }
//                });
//
//            }
//        };

//        timerDI.schedule(timerTaskSensor,0l,1000l);
//    }

//Write DO
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        tempView = findViewById(R.id.tempView);
//        btnButton = findViewById(R.id.btnButton);
//        btnOff = findViewById(R.id.btnOff);
//
//        DIDOModule DOSdk = new DIDOModule();
//
//
//        List<DeviceInfo> device = SDKLocker.getAllUsbDevicesHasDriver(getApplicationContext());
//        for (DeviceInfo each : device) {
//            checkConnect = DOSdk.connect(getApplicationContext(), each, 9600);
//        }
//            btnButton.setOnClickListener(v -> {
//                if (checkConnect) {
//                    DIDOModule.bufferAll = new byte[]{1, 5, 0, 1, -1, 0, -35, -6};
//                    DOSdk.setDOData();
//                    //DI_DO_module.bufferAll = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
//                }
//            });
//
//            btnOff.setOnClickListener(v -> {
//                if (checkConnect) {
//                    DIDOModule.bufferAll = new byte[]{1, 5, 0, 1, 0, 0, -100, 10};
//                    DOSdk.setDOData();
//                    //DI_DO_module.bufferAll = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
//                }
//            });
//    };


}












