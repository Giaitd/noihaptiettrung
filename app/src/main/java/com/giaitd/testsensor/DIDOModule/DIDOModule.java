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

    public DIData getDIData() {
        try {
            byte[] buffer = new byte[]{1,2,0,0,0,24,120,0};
            this.usbSerialPort.write(buffer, this.WRITE_WAIT_MILLIS);
            byte[] bufferStatus = new byte[10];
            this.usbSerialPort.read(bufferStatus, this.READ_WAIT_MILLIS);
            String checkMustHave = Utils.bytesToHex(new byte[]{bufferStatus[0], bufferStatus[1]});
            if (checkMustHave.equals("0102")) {
                String numberByte = Utils.bytesToHex(new byte[]{bufferStatus[2]});
                String value1 = Utils.bytesToHex(new byte[]{bufferStatus[3]});
                String value2 = Utils.bytesToHex(new byte[]{bufferStatus[4]});
                String value3 = Utils.bytesToHex(new byte[]{bufferStatus[5]});

                int valueRead3 = Integer.parseInt(value3);
                int valueRead2 = Integer.parseInt(value2);
                int valueRead1 = Integer.parseInt(value1);
                int numberByteMore = Integer.parseInt(numberByte);

                this.disconnect();
                return new DIData(valueRead1,valueRead2,valueRead3, numberByteMore);
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
                    //new byte[]{1,5,0,1,-1,0,-35,-6};
            this.usbSerialPort.write(buffer, this.WRITE_WAIT_MILLIS);
            byte[] bufferStatus = new byte[10];
            this.usbSerialPort.read(bufferStatus, this.READ_WAIT_MILLIS);
            //this.disconnect();
        } catch (IOException var14) {
            var14.printStackTrace();
            this.disconnect();
        }

        this.setDOData();
    }

//    public void setOffDOData() {
//        try {
//            byte[] buffer = new byte[]{1,5,0,1,0,0,-100,10};
//            this.usbSerialPort.write(buffer, this.WRITE_WAIT_MILLIS);
//            byte[] bufferStatus = new byte[10];
//            this.usbSerialPort.read(bufferStatus, this.READ_WAIT_MILLIS);
//
//        } catch (IOException var14) {
//            var14.printStackTrace();
//            this.disconnect();
//        }
//
//        this.setOffDOData();
//    }

    public void disconnect() {
        this.connected = false;

        try {
            this.usbSerialPort.close();
        } catch (IOException var2) {
        }

        this.usbSerialPort = null;
    }

}
