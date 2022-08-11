package com.giaitd.testsensor.Program;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.giaitd.testsensor.DIDOModule.SetDO;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ControlOutput extends android.app.Service{

    public ControlOutput() { super(); }

       public static TimerTask timerControlOutput(Context context){
        return new TimerTask() {
            public void run() {
                Globals.mTimerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //DataRealtime.initializedTimerTask(context);

                        switch (Globals.runStop){

                            //chua an start
                            case 0:

                                Globals.errorStatus = false;
                                Globals.countTimerBalance = 300; //tg mo van can bang
                                Globals.countTimeVacuumAirRemove = 300;     //tg HCK qtdk
                                Globals.countTimeExhaustAirRemove = 300;    //tg xa hoi qtdk

                                Globals.reachTAndP = false;

                                Globals.countTimeSterilization = (int) (Globals.sterTimeSet*60);
                                Globals.countTimeDry = Globals.dryTimeSet*60;

                                Globals.fullWater = false;
                                Globals.exhaustVacuum = false;

                                if (Globals.temperature >107.0){
                                    SetDO.exhaustOn(context);
                                }else if (Globals.temperature <105.0){
                                    SetDO.exhaustOff(context);
                                }
                                SetDO.waterInOff(context);
                                SetDO.balanceOff(context);
                                SetDO.boilerOff(context);
                                SetDO.vacuumOff(context);
                                break;

                            //da an start
                            case 1:
                                //check water level
                                if (Globals.dIData.i2[7])   {Globals.fullWater = true;}     //high level
                                //if (!Globals.dIData.i2[6])  {Globals.fullWater = false;}    //low level

                                if (!Globals.dIData.i2[7] && !Globals.fullWater){
                                    SetDO.waterInOn(context);
                                } else {
                                    SetDO.waterInOff(context);
                                }

                                /*===== Boiler ======================================*/
                                if (Globals.fullWater && Globals.dIData.i2[6]){
                                    switch (Globals.progress){

                                        //air removal state
                                        case 1:
                                            if(Globals.temperature <= 105.0){
                                                SetDO.boilerOn(context);
                                            } else {
                                                Globals.exhaustVacuum = true;
                                                SetDO.boilerOff(context);
                                            }
                                            break;

                                        //sterilization state
                                        case 2:
                                            if (Globals.temperature < Globals.tempSet){
                                                SetDO.boilerOn(context);
                                            } else if (Globals.temperature > (Globals.tempSet+0.5)){
                                                SetDO.boilerOff(context);
                                                Globals.reachTAndP = true;
                                            }
                                            break;
                                        default:
                                            SetDO.boilerOff(context);
                                            break;
                                    }

                                } else if(Globals.fullWater && !Globals.dIData.i2[6]){
                                    Globals.errorStatus = true; //thieu nuoc trong qttt
                                    SetDO.boilerOff(context);
                                }

                                /*===== Exhaust valve, vacuum ==============================*/
                                switch (Globals.progress){
                                    case 0:     //stop
                                        if (Globals.temperature >106.0){
                                            SetDO.exhaustOn(context);
                                        } else if(Globals.temperature < 104){
                                            SetDO.exhaustOff(context);
                                        }
                                        break;

                                    case 1:  //air removal state

                                        //indicate time countdown
                                        Globals.minCount = Globals.countTimeSterilization / 60;
                                        Globals.secCount = Globals.countTimeSterilization % 60;

                                        if (Globals.temperature > 90.0 && Globals.temperature < 100.0
                                                && !Globals.exhaustVacuum){
                                            SetDO.exhaustOn(context);
                                        } else if(Globals.temperature > 100.0 && !Globals.exhaustVacuum){
                                            SetDO.exhaustOff(context);
                                        } else if (Globals.exhaustVacuum){
                                            SetDO.exhaustOn(context);

                                            //countdown time?????????????
                                            Globals.countTimeExhaustAirRemove--;       //dem lui thoi gian xa hoi
                                            if (Globals.countTimeExhaustAirRemove == 0){
                                                SetDO.exhaustOff(context);

                                                //vacuum
                                                SetDO.vacuumOn(context);
                                                //countdown time?????????????
                                                Globals.countTimeVacuumAirRemove--;
                                                if ( Globals.countTimeVacuumAirRemove==0){
                                                    SetDO.vacuumOff(context);
                                                    Globals.numberVacuumCount = 1;
                                                }
                                            }
                                        }
                                        break;

                                    case 2: //sterilization state
                                        if (Globals.reachTAndP){
                                            Globals.countTimeSterilization--;
                                        }
                                        break;

                                    case 3:  //drying state
                                        //indicate time countdown
                                        Globals.minCount = Globals.countTimeDry / 60;
                                        Globals.secCount = Globals.countTimeDry % 60;

                                        if (Globals.temperature>105.0 && !Globals.dOData.q0[1]){
                                            SetDO.exhaustOn(context);
                                        } else {
                                            SetDO.exhaustOff(context);
                                        }
                                        if (!Globals.dOData.q0[3]){
                                            SetDO.vacuumOn(context);
                                            //countdown time????????????????????
                                            Globals.countTimeDry--;
                                            if (Globals.countTimeDry == 0){
                                                SetDO.vacuumOff(context);
                                            }
                                        }

                                        break;
                                    case 4:
                                        SetDO.balanceOn(context);
                                        Globals.countTimerBalance--;
                                        if (Globals.countTimerBalance == 0){
                                            SetDO.balanceOff(context);
                                        }
                                        break;
                                }
                                break;
                        }
                    }
                });
            }
        };
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
