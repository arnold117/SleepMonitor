package com.arnold.sleepmonitor.recorder

import android.app.Service
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.annotation.RequiresApi
import com.arnold.sleepmonitor.MApplication

object LinearAccRecorder: HandlerThread("LinearAccRecorder"), SensorEventListener {
    private val TAG = "LinearAccRecorder"
    private var handler: Handler? = null
    private var sensorThread: HandlerThread? = null
    
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    private var sensorExists = false
    
    init {
        sensorManager = (MApplication.context.getSystemService(Service.SENSOR_SERVICE)) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        // Check sensor exists
        sensorExists = (sensor != null)
    }


    fun startSensor() {
        Log.i(TAG, "Starting sensor")
        sensorThread = HandlerThread(TAG, Thread.NORM_PRIORITY)
        sensorThread!!.start()
        handler = Handler(sensorThread!!.looper) //Blocks until looper is prepared, which is fairly quick
        sensorManager!!.registerListener(this,
            sensor, SensorManager.SENSOR_DELAY_NORMAL,
            handler
        )
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun stopSensor() {
        Log.i(TAG, "Stopping sensor")
        sensorManager!!.unregisterListener(this)
        sensorThread!!.quitSafely()
    }

    fun sensorExists(): Boolean {
        return sensorExists
    }

    fun setHandler(handler: Handler) {
        this.handler = handler
    }

    fun sendMessage(event: MSensorEvent) {
        if (handler == null) {
            return
        }

        handler?.obtainMessage(event.type.ordinal, event)?.apply {
            sendToTarget()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.values.isNotEmpty()) {
            if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
                val accX = event.values[0]
                val accY = event.values[1]
                val accZ = event.values[2]

                Log.i(TAG, "X: $accX, Y: $accY, Z: $accZ")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i(TAG, "The accuracy of the sensor have changed.")
    }
}