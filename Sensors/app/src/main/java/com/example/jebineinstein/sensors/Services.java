package com.example.jebineinstein.sensors;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by jebineinstein on 13/4/17.
 */

public class Services extends Service implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    PendingIntent pendingIntent;
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    Boolean a1=false;
    String a;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(this,0,new Intent("calloff"),0);
        builder =new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Sensor")
                .setContentText("ScreenService is On")
                .setOngoing(true).setContentIntent(pendingIntent);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                calloff();
            }
        },new IntentFilter("calloff"));
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "ScreenOff");
        notificationManager.notify(324255, builder.build());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) {
            if(!wakeLock.isHeld()) {
                wakeLock.acquire();
            }
        } else {
            if(wakeLock.isHeld()) {
                wakeLock.release();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    void calloff(){
        if(a1){
            if(a1){
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                a = "On";
            }
            else{
                sensorManager.unregisterListener(this);
                a = "Off";
            }
            a1=false;
            builder.setContentText("ScreenService is "+a);
            notificationManager.notify(324255, builder.build());
        }
        else{
            if(a1){
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                a = "On";
            }
            else{
                sensorManager.unregisterListener(this);
                a = "Off";
            }
            a1=true;
            builder.setContentText("ScreenService is "+a);
            notificationManager.notify(324255, builder.build());
        }
    }

}
