package com.giaitd.testsensor.Program;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.giaitd.testsensor.DIDOModule.ReadDIDO;
import com.giaitd.testsensor.RTDModule.ReadRTD;

import java.util.Timer;

public class DataRealtime {
//    private static DataRealtime mCurrentService;


    @SuppressLint("HardwareIds")

    public DataRealtime() {
        super();
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        Globals.m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        mCurrentService = this;
//    }

//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }


    public static void initializedTimerTask(Context context) {

        Globals.timerReadDIDODataTask = ReadDIDO.getDIDOTask(context);
        Globals.timerReadRTDTask = ReadRTD.getRTDTask(context);

        try{
            Globals.timerReadDIDOData = new Timer();
            Globals.timerReadDIDOData.schedule(Globals.timerReadDIDODataTask,0,500);
        }catch (Exception e){
            e.printStackTrace();
        }


        try{
            Globals.timerReadRTDData = new Timer();
            Globals.timerReadRTDData.schedule(Globals.timerReadRTDTask,100,500);
            Log.d("abc", "initializedTimerTask: ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void runStop() {
        if (Globals.btnStartYes){
            Globals.runStop = 1;
            Globals.btnStartYes = false;
        }
        if (Globals.btnStopYes){
            Globals.runStop = 0;
            Globals.btnStopYes = false;
        }
    }


}
