package com.marianoroces.sireba.utils;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.marianoroces.sireba.utils.MyReceiver;

public class MyIntentService extends IntentService {
    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent){
        Intent notification = new Intent();
        notification.setAction(MyReceiver.REPORT_CREATED_EVENT);
        sendBroadcast(notification);
    }
}
