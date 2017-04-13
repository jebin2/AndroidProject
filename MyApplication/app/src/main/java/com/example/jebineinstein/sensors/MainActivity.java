package com.example.jebineinstein.sensors;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sMgr;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        List<Sensor> list = sMgr.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: list){
            tv1.setText((sensor.getName()).toString());
        }
    }


    }
