package com.arnold.sleepmonitor

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class MSensorManager(context: Context?) : SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null
    var lightValue = 0f

    init {
        if (context != null) {
            this.sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        }
        lightSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    fun startListener() {
        Log.d("SensorManager", "Starting listener.")
        sensorManager?.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListener() {
        Log.d("SensorManager", "Stopping listener.")
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                lightValue = event.values[0]
                Log.d("SensorChanged", "Light value: $lightValue")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i("SensorAccuracyChanged", "The accuracy of the sensor have changed.")
    }
}