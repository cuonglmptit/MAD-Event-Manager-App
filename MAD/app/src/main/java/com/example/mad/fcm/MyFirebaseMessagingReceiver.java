package com.example.mad.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyFirebaseMessagingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Nhan duoc thong bao");
    }
}
