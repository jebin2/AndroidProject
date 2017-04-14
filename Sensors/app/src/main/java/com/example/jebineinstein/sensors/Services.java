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
import android.support.v7.app.NotificationCompat;

/**
 * Created by jebineinstein on 13/4/17.
 */

public class Services extends Service implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    PendingIntent pendingIntent, deletependingIntent;
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    Boolean a1=false;
    String a;

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
        deletependingIntent = PendingIntent.getBroadcast(Services.this,0,new Intent("stopservice"),0);
        builder =new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.proximityactionnotiy)
                .setContentTitle("Proximity Sensor")
                .setContentText("Proximity Sensor is On")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setDeleteIntent(deletependingIntent);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                calloff();
            }
        },new IntentFilter("calloff"));
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.stopService(new Intent(context, Services.class));
            }
        },new IntentFilter("stopservice"));
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "ScreenOff");
        notificationManager.notify(324255, builder.build());
//        notificationManager,notify().;
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
                builder.setOngoing(true);
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                a = "On";
                a1=false;
            }
            else{
                builder.setOngoing(false);
                sensorManager.unregisterListener(this);
                a = "Off";
                a1=true;
            }

            builder.setContentText("Proximity Sensor is "+a);
            notificationManager.notify(324255, builder.build());
    }

}
