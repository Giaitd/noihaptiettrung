package com.giaitd.testsensor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.giaitd.testsensor.DIDOModule.SetDO;
import com.giaitd.testsensor.Program.ControlOutput;
import com.giaitd.testsensor.Program.Globals;
import com.giaitd.testsensor.Program.DataRealtime;


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

        Timer timer0 = new Timer();
        Timer timer1= new Timer();

        TimerTask testTask = new TimerTask() {
            @Override
            public void run() {
               mTimerHandler.post(() -> {
                    DataRealtime.initializedTimerTask(getApplicationContext());
                    Log.d("aaa","running");

                });
            }
        };
        timer0.schedule(testTask,100,1000);

//        TimerTask timerTaskabc = new TimerTask() {
//            @Override
//            public void run() {
//                mTimerHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView2.setText(Globals.temperature+"");
//                        Log.d("run", "is running");
//                    }
//                });
//            }
//        };
//        timer1.schedule(timerTaskabc,2000,2000);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stt = !stt;
            }
        });

        TimerTask timerTaskA = new TimerTask() {
            @Override
            public void run() {
                mTimerHandler.post(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        };
        timer1.schedule(timerTaskA,200,1000);

    }



}













