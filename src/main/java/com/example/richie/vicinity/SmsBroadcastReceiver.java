//package com.example.richie.vicinity;
//
///**
// * Created by Richie on 11-10-2016.
// */
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.telephony.SmsMessage;
//
//
//public class SmsBroadcastReceiver extends BroadcastReceiver {
//
//    public static final String SMS_BUNDLE = "pdus";
//  Context con;
//    public void onReceive(Context context, Intent intent) {
//        Bundle intentExtras = intent.getExtras();
//        if (intentExtras != null) {
//            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
//            String smsMessageStr = "";
//            for (int i = 0; i < sms.length; ++i) {
//                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
//
//                String smsBody = smsMessage.getMessageBody().toString();
//                String address = smsMessage.getOriginatingAddress();
//
//                smsMessageStr += "SMS From: " + address + "\n";
//                smsMessageStr += smsBody + "\n";
//            }
////            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
//
//            //this will update the UI with message
//            MainActivity mc  = MainActivity.instance();
//            mc.updateList(smsMessageStr);
//        }
//    }
//
//
//}
//
