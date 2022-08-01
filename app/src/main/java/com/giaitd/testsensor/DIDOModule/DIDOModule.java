package com.giaitd.testsensor.DIDOModule;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.Iterator;


import asim.sdk.common.Utils;
import asim.sdk.locker.CustomProber;
import asim.sdk.locker.DeviceInfo;

public class DIDOModule {
    public UsbSerialPort usbSerialPort;
    public boolean connected = false;
    public int READ_WAIT_MILLIS = 2000;
    public int WRITE_WAIT_MILLIS = 2000;

    public DIDOModule(){

    }

    public static byte[] bufferAll = null;

    public boolean connect(Context context, DeviceInfo deviceInfo, int baudRate) {
        UsbSerialDriver driver = deviceInfo.driver;
        UsbManager usbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
        if (driver.getPorts().size() < deviceInfo.port) {
            Log.d("---sdk-locker---", "connection failed: not enough ports at device");
            return false;
        } else {
            this.usbSerialPort = (UsbSerialPort)driver.getPorts().get(deviceInfo.port);
            UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
            if (usbConnection == null) {
                if (!usbManager.hasPermission(driver.getDevice())) {
                    Log.d("---sdk-locker---", "connection failed: permission denied");
                } else {
                    Log.d("---sdk-locker---", "connection failed: open failed");
                }

                return false;
            } else {
                try {
                    this.usbSerialPort.open(usbConnection);
                    this.usbSerialPort.setParameters(baudRate, 8, 1, UsbSerialPort.PARITY_NONE);
                    Log.d("---sdk-locker---", "connected");
                    this.connected = true;
                    return true;
                } catch (Exception var8) {
                    Log.d("---sdk-locker---", "connection failed: " + var8.getMessage());
                    this.disconnect();
                    return false;
                }
            }
        }
    }

    public boolean connect(Context context, int deviceId, int portNum, int baudRate) {
        UsbDevice device = null;
        UsbManager usbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
        Iterator var7 = usbManager.getDeviceList().values().iterator();

        while(var7.hasNext()) {
            UsbDevice v = (UsbDevice)var7.next();
            Log.d("===deviceIdExist===", String.valueOf(v.getDeviceId()));
            if (Utils.compareTwoDeviceId(v.getDeviceId(), deviceId)) {
                device = v;
            }
        }

        if (device == null) {
            Log.d("---sdk-DIDO---", "connection failed: device not found");
            Log.d("---deviceId---", String.valueOf(deviceId));
            return false;
        } else {
            UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
            if (driver == null) {
                driver = CustomProber.getCustomProber().probeDevice(device);
            }

            if (driver == null) {
                Log.d("---sdk-DIDO---", "No drivers");
                Log.d("---deviceId---", String.valueOf(deviceId));
                return false;
            } else if (driver.getPorts().size() < portNum) {
                Log.d("---sdk-DIDO---", "connection failed: not enough ports at device");
                return false;
            } else {
                this.usbSerialPort = (UsbSerialPort)driver.getPorts().get(portNum);
                UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
                if (usbConnection == null) {
                    if (!usbManager.hasPermission(driver.getDevice())) {
                        Log.d("---sdk-DIDO---", "connection failed: permission denied");
                    } else {
                        Log.d("---sdk-DIDO---", "connection failed: open failed");
                    }

                    return false;
                } else {
                    try {
                        this.usbSerialPort.open(usbConnection);
                        this.usbSerialPort.setParameters(baudRate, 8, 1, UsbSerialPort.PARITY_NONE);
                        Log.d("---sdk-DIDO---", "connected");
                        this.connected = true;
                        return true;
                    } catch (Exception var10) {
                        Log.d("---sdk-DIDO---", "connection failed: " + var10.getMessage());
                        this.disconnect();
                        return false;
                    }
                }
            }
        }
    }

    public DIDOData getDIData() {
        try {
            byte[] buffer = new byte[]{1,2,0,0,0,24,120,0};
            this.usbSerialPort.write(buffer, this.WRITE_WAIT_MILLIS);
            byte[] bufferStatus = new byte[10];
            this.usbSerialPort.read(bufferStatus, this.READ_WAIT_MILLIS);
            String checkMustHave = Utils.bytesToHex(new byte[]{bufferStatus[0], bufferStatus[1]});
            if (checkMustHave.equals("0102")) {
                int valueRead3 = 0;
                int valueRead2 = 0;
                int valueRead1 = 0;
                String numberByte = Utils.bytesToHex(new byte[]{bufferStatus[2]});
                int numberByteMore = Integer.parseInt(numberByte);

                if (numberByteMore==1){
                    String value1 = Utils.bytesToHex(new byte[]{bufferStatus[3]});//8 bit low
                    valueRead1 = Integer.parseInt(value1);
                    valueRead2 = 0;
                    valueRead3 = 0;
                } else if (numberByteMore==2){
                    String value1 = Utils.bytesToHex(new byte[]{bufferStatus[3]});//8 bit low
                    String value2 = Utils.bytesToHex(new byte[]{bufferStatus[4]});//8 bit mid
                    valueRead1 = Integer.parseInt(value1);
                    valueRead2 = Integer.parseInt(value2);
                    valueRead3 = 0;

                } else if (numberByteMore==3){
                    String value1 = Utils.bytesToHex(new byte[]{bufferStatus[3]});//8 bit low
                    String value2 = Utils.bytesToHex(new byte[]{bufferStatus[4]});//8 bit mid
                    String value3 = Utils.bytesToHex(new byte[]{bufferStatus[5]});//8 bit high
                    valueRead1 = Integer.parseInt(value1);
                    valueRead2 = Integer.parseInt(value2);
                    valueRead3 = Integer.parseInt(value3);
                }

                this.disconnect();
                return new DIDOData(valueRead1,valueRead2,valueRead3, numberByteMore);
            } else {
                this.disconnect();
                return null;
            }
        } catch (IOException var14) {
            var14.printStackTrace();
            this.disconnect();
            return null;
        }
    }

    public DIDOData getDOData() {
        try {
            byte[] buffer = new byte[]{1,1,0,0,0,7,125,-56};
            this.usbSerialPort.write(buffer, this.WRITE_WAIT_MILLIS);
            byte[] bufferStatus = new byte[10];
            this.usbSerialPort.read(bufferStatus, this.READ_WAIT_MILLIS);
            String checkMustHave = Utils.bytesToHex(new byte[]{bufferStatus[0], bufferStatus[1]});
            if (checkMustHave.equals("0101")) {
                int valueRead3 = 0;
                int valueRead2 = 0;
                int valueRead1 = 0;
                String numberByte = Utils.bytesToHex(new byte[]{bufferStatus[2]});
                int numberByteMore = Integer.parseInt(numberByte);

                if (numberByteMore==1){
                    String value1 = Utils.bytesToHex(new byte[]{bufferStatus[3]});//8 bit low
                    valueRead1 = Integer.parseInt(value1);
                    valueRead2 = 0;
                    valueRead3 = 0;
                } else if (numberByteMore==2){
                    String value1 = Utils.bytesToHex(new byte[]{bufferStatus[3]});//8 bit low
                    String value2 = Utils.bytesToHex(new byte[]{bufferStatus[4]});//8 bit mid
                    valueRead1 = Integer.parseInt(value1);
                    valueRead2 = Integer.parseInt(value2);
                    valueRead3 = 0;

                } else if (numberByteMore==3){
                    String value1 = Utils.bytesToHex(new byte[]{bufferStatus[3]});//8 bit low
                    String value2 = Utils.bytesToHex(new byte[]{bufferStatus[4]});//8 bit mid
                    String value3 = Utils.bytesToHex(new byte[]{bufferStatus[5]});//8 bit high
                    valueRead1 = Integer.parseInt(value1);
                    valueRead2 = Integer.parseInt(value2);
                    valueRead3 = Integer.parseInt(value3);
                }

                this.disconnect();
                return new DIDOData(valueRead1,valueRead2,valueRead3, numberByteMore);
            } else {
                this.disconnect();
                return null;
            }
        } catch (IOException var14) {
            var14.printStackTrace();
            this.disconnect();
            return null;
        }
    }

    public void setDOData() {
        try {
            byte[] buffer = bufferAll;
            this.usbSerialPort.write(buffer, this.WRITE_WAIT_MILLIS);
            byte[] bufferStatus = new byte[10];
            this.usbSerialPort.read(bufferStatus, this.READ_WAIT_MILLIS);
            this.disconnect();
        } catch (IOException var14) {
            var14.printStackTrace();
            this.disconnect();
        }

    }



    public void disconnect() {
        this.connected = false;

        try {
            this.usbSerialPort.close();
        } catch (IOException var2) {
        }

        this.usbSerialPort = null;
    }


}
