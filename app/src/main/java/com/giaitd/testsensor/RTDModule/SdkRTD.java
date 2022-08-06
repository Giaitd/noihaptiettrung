package com.giaitd.testsensor.RTDModule;

import android.util.Log;
import android.widget.TextView;

import com.giaitd.testsensor.Program.Globals;

import java.io.IOException;

import asim.sdk.common.Utils;
import asim.sdk.tempandhum.SDKTemperatureAndHumidity;
import asim.sdk.tempandhum.TempHumiData;

public class SdkRTD extends SDKTemperatureAndHumidity {

    public TempHumiData getTempData() {

        try {
            byte[] buffer = new byte[]{1, 3, 0, 32, 0, 2, -59, -63};
            this.usbSerialPort.write(buffer, this.WRITE_WAIT_MILLIS);
            byte[] bufferStatus = new byte[10];
            this.usbSerialPort.read(bufferStatus, this.READ_WAIT_MILLIS);
            String checkMustHave = Utils.bytesToHex(new byte[]{bufferStatus[0], bufferStatus[1], bufferStatus[2]});
            if (checkMustHave.equals("010304")) {
                String temp1 = Utils.bytesToHex(new byte[]{bufferStatus[3], bufferStatus[4]});
                String temp2 = Utils.bytesToHex(new byte[]{bufferStatus[5], bufferStatus[6]});
                double temp1Data = (double)Integer.parseInt(temp1, 16) / 10.0D;
                double temp2Data = (double)Integer.parseInt(temp2, 16) / 10.0D;
                double unUse = 0;

                this.disconnect();

               return new TempHumiData(temp1Data, temp2Data, unUse);
            } else {
                Log.d("testing==", "running to here");
                this.disconnect();
                return null;
            }
        } catch (IOException var14) {
            Log.d("testing==", "running to here2");
            var14.printStackTrace();
            this.disconnect();
            this.disconnect();
            return null;
        }
    }
}
