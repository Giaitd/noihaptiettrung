package com.giaitd.testsensor.Program;

import java.util.TimerTask;

public class ProgressAndStatus {
    public ProgressAndStatus() {
        super();
    }

    TimerTask timerProgressStatus = new TimerTask() {
        @Override
        public void run() {
            Globals.mTimerHandler.post(new Runnable() {
                @Override
                public void run() {
                    switch (Globals.runStop){
                        case 0:

                                Globals.progress = 0;
                                Globals.status = "...";
                                Globals.statusDetail = "Sẵn sàng chạy";

                            break;
                        case 1:
                            //status of autoclave
                            if (Globals.numberVacuumCount < Globals.numberHCKSet){
                                Globals.progress = 1; //Air removal state
                                Globals.status = "Đang chạy";
                                Globals.statusDetail = "Quá trình đuổi khí";

                            }else if (Globals.numberVacuumCount == Globals.numberHCKSet
                                    && Globals.countTimeSterilization > 0) {
                                Globals.progress = 2; //Sterilization state
                                Globals.status = "Đang chạy";
                                Globals.statusDetail = "Quá trình tiệt trùng";

                            }else if (Globals.countTimeSterilization == 0
                                    && Globals.countTimeDry > 0) {
                                Globals.progress = 3; //Drying state
                                Globals.status = "Đang chạy";
                                Globals.statusDetail = "Quá trình làm nguội";

                            }else {
                                Globals.progress = 4; //Finish
                                Globals.status = "Kết thúc";
                                Globals.statusDetail = "Mẻ hấp hoàn thành";
                            }


                            break;
                    }

                }
            });
        }
    };
}
