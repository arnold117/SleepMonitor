package com.arnold.sleepmonitor.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.arnold.sleepmonitor.utils.MLog;

public class MSensorManager implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor gameRotateSensor;
    private Sensor linearAccelerometer;

    private double lightValue;
    private double vectorX;
    private double vectorY;
    private double vectorZ;

    private double accX;
    private double accY;
    private double accZ;

    public MSensorManager(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        gameRotateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    public void startListener() {
        MLog.d("MSensorManager", "startListener");
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gameRotateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, linearAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopListener() {
        MLog.d("MSensorManager", "stopListener");
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightValue = sensorEvent.values[0];
            MLog.d("MSensorManager", "Light Value: " + lightValue);
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            // x * sin(θ/2)
            vectorX = sensorEvent.values[0];
            vectorY = sensorEvent.values[1];
            vectorZ = sensorEvent.values[2];
            MLog.d("MSensorManager", "Vector Value: " + vectorX + ", " + vectorY + ", " + vectorZ);
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            accX = sensorEvent.values[0];
            accY = sensorEvent.values[1];
            accZ = sensorEvent.values[2];
            MLog.d("MSensorManager", "Acc Value: " + accX + ", " + accY + ", " + accZ);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public double getLightValue() {
        return lightValue;
    }

}
