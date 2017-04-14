package com.example.jebineinstein.doubletap;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.view.View.OnTouchListener;

/**
 * Created by jebineinstein on 13/4/17.
 */

public class Services extends Service implements OnTouchListener {

    WindowManager windowManager;
    LinearLayout linearLayout;
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        linearLayout = new LinearLayout(this);
        // set layout width 30 px and height is equal to full screen
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(30, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,0);
        linearLayout.setLayoutParams(lp);
        // set color if you want layout visible on screen
//  touchLayout.setBackgroundColor(Color.CYAN);
        // set on touch listener
        linearLayout.setOnTouchListener(this);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        // set layout parameter of window manager
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                0, // width of layout 30 px
                0, // height is equal to full screen
                WindowManager.LayoutParams.TYPE_PHONE, // Type Phone, These are non-application windows providing user interaction with the phone (in particular incoming calls).
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, // this window won't ever get key input focus
                PixelFormat.TRANSLUCENT);
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        Log.i("touch", "add View");
        windowManager.addView(linearLayout, mParams);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "MyWakeLock");
        wakeLock.acquire();
        KeyguardManager myKM = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if( myKM.inKeyguardRestrictedInputMode()) {
            //it is locked
            Log.i("touch","0");
        } else {
            //it is not locked
            Log.i("touch","1");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP)
            Log.i("touch", "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :"+ event.getRawY());

        Log.i("touch","asfsaf");
        return false;
    }
}
