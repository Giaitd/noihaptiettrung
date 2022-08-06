package com.giaitd.testsensor.DIDOModule;

import android.content.Context;

import com.giaitd.testsensor.Program.Globals;

import java.util.List;

import asim.sdk.locker.DeviceInfo;
import asim.sdk.locker.SDKLocker;

public class SetDO {
    public static byte[] bufferQ00On = {1,5,0,0,-1,0,-116,58};  //thanh dot on
    public static byte[] bufferQ00Off = {1,5,0,0,0,0,-51,-54};  //thanh dot off

    public static byte[] bufferQ01On = {1,5,0,1,-1,0,-35,-6};   //may hck on
    public static byte[] bufferQ01Off = {1,5,0,1,0,0,-100,10};  //may hck off

    public static byte[] bufferQ02On = {1,5,0,2,-1,0,45,-6};    //van cap nuoc on
    public static byte[] bufferQ02Off = {1,5,0,2,0,0,108,10};   //van cap nuoc off

    public static byte[] bufferQ03On = {1,5,0,3,-1,0,124,58};   //van xa hoi
    public static byte[] bufferQ03Off = {1,5,0,3,0,0,61,-54};   //van xa hoi

    public static byte[] bufferQ04On = {1,5,0,4,-1,0,-51,-5};   //van cba on
    public static byte[] bufferQ04Off = {1,5,0,4,0,0,-116,11};  //van cba off

    public static byte[] bufferQ05On = {1,5,0,5,-1,0,-100,59};  //
    public static byte[] bufferQ05Off = {1,5,0,5,0,0,-35,-53};  //


    public static void writeDO(Context context) {

        SdkDIDOModule DOSdk = new SdkDIDOModule();

        List<DeviceInfo> devices = SDKLocker.getAllUsbDevicesHasDriver(context);
        for (DeviceInfo each : devices) {
            boolean connect = DOSdk.connect(context, each, 9600);
            if (connect) {
                DOSdk.setDOData();
            }
        }
    }

    //boiler on/off
    public static void boilerOn(Context context){
        Globals.bufferAll = bufferQ00On;
        writeDO(context);
    }

    public static void boilerOff(Context context){
        Globals.bufferAll = bufferQ00Off;
        writeDO(context);
    }

    //vacuum on/off
    public static void vacuumOn(Context context){
        Globals.bufferAll = bufferQ01On;
        writeDO(context);
    }

    public static void vacuumOff(Context context){
        Globals.bufferAll = bufferQ01Off;
        writeDO(context);
    }

    //waterInValve on/off
    public static void waterInOn(Context context){
        Globals.bufferAll = bufferQ02On;
        writeDO(context);
    }

    public static void waterInOff(Context context){
        Globals.bufferAll = bufferQ02Off;
        writeDO(context);
    }

    //exhaust on/off
    public static void exhaustOn(Context context){
        Globals.bufferAll = bufferQ03On;
        writeDO(context);
    }

    public static void exhaustOff(Context context){
        Globals.bufferAll = bufferQ03Off;
        writeDO(context);
    }

    //balance on/off
    public static void balanceOn(Context context){
        Globals.bufferAll = bufferQ04On;
        writeDO(context);
    }

    public static void balanceOff(Context context){
        Globals.bufferAll = bufferQ04Off;
        writeDO(context);
    }
}
