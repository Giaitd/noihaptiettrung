package com.giaitd.testsensor.Program;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.giaitd.testsensor.DIDOModule.SetDO;

import java.util.Timer;
import java.util.TimerTask;

public class ControlOutput extends android.app.Service{

    public ControlOutput() { super(); }

    public static boolean fullWater;

    TimerTask timerControlOutput = new TimerTask() {
        @Override
        public void run() {
            switch (Globals.runStop){

                //chua an start
                case 0:
                    fullWater = false;
                    if (Globals.temperature >107.0){
                        SetDO.exhaustOn(getApplicationContext());
                    }else if (Globals.temperature <105.0){
                        SetDO.exhaustOff(getApplicationContext());
                    }
                    SetDO.waterInOff(getApplicationContext());
                    SetDO.balanceOff(getApplicationContext());
                    SetDO.boilerOff(getApplicationContext());
                    SetDO.vacuumOff(getApplicationContext());
                    break;

                //da an start
                case 1:
                    //check water level
                    if (Globals.dIData.i2[7])   {fullWater = true;}
                    if (!Globals.dIData.i2[6])  {fullWater = false;}

                    if (!Globals.dIData.i2[7] && !fullWater){
                        SetDO.waterInOn(getApplicationContext());
                    } else {
                        SetDO.waterInOff(getApplicationContext());
                    }

                    //boiler
                    if (fullWater && Globals.dIData.i2[6]){
                        if(Globals.temperature < 85.0){

                        }

                    } else {

                    }

                    break;

            }
        }
    };





    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
