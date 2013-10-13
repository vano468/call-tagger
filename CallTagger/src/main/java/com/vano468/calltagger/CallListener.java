package com.vano468.calltagger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CallListener extends PhoneStateListener {

    int callStart, ringingStart;
    String callNumber;
    Context context, service;

    CountDownTimer toastTimer;
    Toast toastMessage;

    CallListener(Context _context, Context _service) {
        callStart = 0;
        ringingStart = 0;
        context = _context;
        service = _service;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                if (ringingStart == 1)
                    stopToastMessage();
                if (callStart == 1) {
                    //showNotification("Call ended", callNumber, "Call ended: " + callNumber);
                    Intent intent = new Intent(context, SetTagActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(SetTagActivity.CallNumber, callNumber);
                    context.startActivity(intent);
                }
                callStart = 0;
                ringingStart = 0;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                callStart = 1;
                ringingStart = 0;
                stopToastMessage();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                ringingStart = 1;
                callNumber = incomingNumber;
                //showNotification("Call ringing", callNumber, "Call ringing: " + callNumber);
                DBWorker dbWorker = new DBWorker(context);
                startToastMessage(callNumber, dbWorker.getTagByNumber(callNumber));
                break;
        }
    }

    public void showNotification(String title, String text, String ticker) {

        int MY_NOTIFICATION_ID=1;
        NotificationManager notificationManager;
        Notification myNotification;

        Intent myIntent = new Intent(service, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                service,
                0,
                myIntent,
                Intent.FLAG_ACTIVITY_NEW_TASK);

        myNotification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .build();

        notificationManager = (NotificationManager)service.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

    }

    public void startToastMessage(String text, String tag){

        LayoutInflater li = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastView = li.inflate(R.layout.custom_toast, null);

        TextView toastTag = (TextView) toastView.findViewById(R.id.toastTag);
        toastTag.setText(tag);

        TextView toastPhone = (TextView) toastView.findViewById(R.id.toastPhone);
        toastPhone.setText(text);

        toastMessage = new Toast(context);
        toastMessage.setDuration(Toast.LENGTH_LONG);
        toastMessage.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
        toastMessage.setView(toastView);
        toastMessage.show();

        toastTimer = new CountDownTimer(31000, 1000) {
            public void onTick(long millisUntilFinished) {toastMessage.show();}
            public void onFinish() {toastMessage.show();}
        };
        toastTimer.start();
    }

    public void stopToastMessage() {
        toastTimer.cancel();
        toastMessage.cancel();
    }

}
