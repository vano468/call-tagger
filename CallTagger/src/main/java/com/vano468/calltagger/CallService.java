package com.vano468.calltagger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallService extends Service {

    CallListener listener;
    TelephonyManager telephonyManager;

    public void onCreate() {
        super.onCreate();
        listener = new CallListener(getApplicationContext(), this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

}