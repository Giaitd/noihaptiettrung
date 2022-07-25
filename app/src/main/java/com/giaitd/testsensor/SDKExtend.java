package com.giaitd.testsensor;

import android.widget.TextView;

import java.io.IOException;

import asim.sdk.common.Utils;
import asim.sdk.tempandhum.SDKTemperatureAndHumidity;
import asim.sdk.tempandhum.TempHumiData;

public class SDKExtend extends SDKTemperatureAndHumidity {

    public TempHumiData getTempHumiData() {

        try {
            byte[] buffer = new byte[]{1, 3, 0, 32, 0, 2, -59, -63};
            this.usbSerialPort.write(buffer, this.WRITE_WAIT_MILLIS);
            byte[] bufferStatus = new byte[10];
            this.usbSerialPort.read(bufferStatus, this.READ_WAIT_MILLIS);
            String checkMustHave = Utils.bytesToHex(new byte[]{bufferStatus[0], bufferStatus[1], bufferStatus[2]});
            if (checkMustHave.equals("010304")) {
                String temp = Utils.bytesToHex(new byte[]{bufferStatus[3], bufferStatus[4]});
                String humi = Utils.bytesToHex(new byte[]{bufferStatus[5], bufferStatus[6]});
                //String dewdrop = Utils.bytesToHex(new byte[]{bufferStatus[7], bufferStatus[8]});
                double temp2 = (double)Integer.parseInt(temp, 16) / 10.0D;
                double humi2 = (double)Integer.parseInt(humi, 16) / 10.0D;
                double dewdrop2 = 0;
                this.disconnect();

               return new TempHumiData(temp2, humi2, dewdrop2);
            } else {
                this.disconnect();
                return null;
            }
        } catch (IOException var14) {
            var14.printStackTrace();
            this.disconnect();
            this.disconnect();
            return null;
        }
    }
}
