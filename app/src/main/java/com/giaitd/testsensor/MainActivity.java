package com.giaitd.testsensor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.giaitd.testsensor.DIDOModule.ReadDIDO;
import com.giaitd.testsensor.DIDOModule.SetDO;
import com.giaitd.testsensor.Program.ControlOutput;
import com.giaitd.testsensor.Program.Globals;
import com.giaitd.testsensor.Program.DataRealtime;
import com.giaitd.testsensor.Program.ProgressAndStatus;
import com.giaitd.testsensor.RTDModule.ReadRTD;


import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    TextView textView3, textView2,textView1 ;
    Button button;
    Handler mTimerHandler = new Handler();
    public int number = 0;
    boolean stt = false;

    /**
    * Read sensor
    */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stt = !stt;
                if (stt){
                    Globals.runStop = 1;
                    textView3.setText("running");
                } else {
                    Globals.runStop = 0;
                    textView3.setText("off");
                }

            }
        });

        Timer timer0 = new Timer();
        Timer timer1= new Timer();
        Timer timer2= new Timer();

        TimerTask timerControlTask;
        TimerTask timerProgressTask;

        TimerTask testTask = new TimerTask() {
            @Override
            public void run() {
               mTimerHandler.post(() -> {
//                    DataRealtime.initializedTimerTask(getApplicationContext());
//                    Log.d("aaa","running");
                   Globals.timerReadDIDODataTask = ReadDIDO.getDIDOTask(getApplicationContext());
                   Globals.timerReadRTDTask = ReadRTD.getRTDTask(getApplicationContext());

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
                });
            }
        };
        timer0.schedule(testTask,50,1000);

        timerProgressTask = ProgressAndStatus.timerProgressStatus(getApplicationContext());
        timerControlTask = ControlOutput.timerControlOutput(getApplicationContext());

        timer1.schedule(timerProgressTask,100,1000);
        timer2.schedule(timerControlTask,150,1000);





    }



}













