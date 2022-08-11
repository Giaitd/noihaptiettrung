package com.giaitd.testsensor.Program;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.TextView;

import com.giaitd.testsensor.DIDOModule.DIData;
import com.giaitd.testsensor.DIDOModule.DOData;

import java.util.Timer;
import java.util.TimerTask;

import asim.sdk.tempandhum.TempHumiData;

public class Globals {


    public static DIData dIData = null;
    public static DOData dOData = null;
    public static byte[] bufferAll = null;



    public static TempHumiData tempData = null;

    public static String m_androidId;
    public static Handler mTimerHandler = new Handler();

    public static Timer timerReadRTDData;
    public static TimerTask timerReadRTDTask;

    public static Timer timerReadDIDOData;
    public static TimerTask timerReadDIDODataTask;

    //button manual
    public static boolean manBoiler;    //q00
    public static boolean manVacuum;    //q01
    public static boolean manWaterIn;   //q02
    public static boolean manExhaust;   //q03
    public static boolean manBalance;   //q04

    //Button start/stop
    public static boolean btnStartYes;
    public static boolean btnStopYes;
    public static byte runStop = 0;

    //status autoclave
    public static String statusDetail;
    public static String status;

    //program value set
    public static Double tempSet =121.0;
    public static Double sterTimeSet = 5.0;
    public static Integer dryTimeSet = 2;


    //printer
    public static boolean enablePrinter;
    public static int cyclePrinter;

    //T&P
    public static Double temperature = 0.0;
    public static Double tempOffSet = 2.0;


    //setup vacuum
    public static Integer numberHCKSet = 2;
    public static Double pressHCKSet;


    //countdown time
    public static int minCount;
    public static int secCount;

    public static int countTimeVacuumAirRemove;
    public static int countTimeExhaustAirRemove;

    public static int countTimeSterilization;
    public static int countTimeDry;

    public static int countTimerBalance;




    //state of program
    public static byte progress;
    public static int numberVacuumCount = 0;
    public static boolean errorStatus;
    public static boolean reachTAndP;

    public static boolean fullWater;
    public static boolean exhaustVacuum;



}
