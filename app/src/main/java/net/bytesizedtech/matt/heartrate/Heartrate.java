package net.bytesizedtech.matt.heartrate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Heartrate extends AppCompatActivity implements SensorEventListener {
    final int bodySensCode = 0;

    final TextView display = findViewById(R.id.textView);


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate);


        if (checkSelfPermission(Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BODY_SENSORS}, bodySensCode);
        }

        SensorManager sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        Sensor heartRate = null;
        heartRate = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if (heartRate != null) {
            display.setText("Sensor loaded");
        } else {
            display.setText("Sensor not loaded");
        }

        sensorManager.registerListener(this, heartRate, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = "Sensor reporting value: " + (int)event.values[0];
            display.setText(msg);
        }
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("onAccuracyChanged - accuracy: " + accuracy);
    }
}
