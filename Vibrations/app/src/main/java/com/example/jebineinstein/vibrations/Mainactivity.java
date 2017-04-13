package com.example.jebineinstein.vibrations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jebineinstein on 6/4/17.
 */

public class Mainactivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this,Services.class));
        this.finish();
    }
}
