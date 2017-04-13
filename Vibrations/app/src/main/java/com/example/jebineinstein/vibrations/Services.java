package com.example.jebineinstein.vibrations;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

/**
 * Created by jebineinstein on 6/4/17.
 */

public class Services extends Service{

    public Vibrator v;
    String a;
    Boolean a1=true;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    PendingIntent pendingIntent, deletependingIntent;
    Intent vibrate;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        v =(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder =new NotificationCompat.Builder(getApplicationContext());
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Vibrate();
            }
        }, new IntentFilter("vibrate"));
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.stopService(new Intent(context, Services.class));
            }
        },new IntentFilter("stopservice"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        vibrate = new Intent("vibrate");
        pendingIntent = PendingIntent.getBroadcast(this, 0, vibrate, 0);
        deletependingIntent = PendingIntent.getBroadcast(Services.this,0,new Intent("stopservice"),0);
        mBuilder.setSmallIcon(R.drawable.vibration);
        mBuilder.setContentTitle("Vibration");
        mBuilder.setContentText("Vibration is Off");
        mBuilder.setOngoing(true);
        mBuilder.setDeleteIntent(deletependingIntent);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(324255, mBuilder.build());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void Vibrate() {
        if(a1){
            v.vibrate(new long[]{0, 1000, 0},0);
            if(a1){
                mBuilder.setOngoing(true);
                a = "On";
            }
            else{
                mBuilder.setOngoing(false);
                a = "Off";
            }
            a1=false;
            mBuilder.setContentText("Vibration is "+a);
            mNotificationManager.notify(324255, mBuilder.build());
        }
        else{
            v.cancel();
            if(a1){
                mBuilder.setOngoing(true);
                a = "On";
            }
            else{
                mBuilder.setOngoing(false);
                a = "Off";
            }
            a1=true;
            mBuilder.setContentText("Vibration is "+a);
            mNotificationManager.notify(324255, mBuilder.build());
        }
    }
}
