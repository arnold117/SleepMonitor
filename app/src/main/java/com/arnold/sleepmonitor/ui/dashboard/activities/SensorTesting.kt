package com.arnold.sleepmonitor.ui.dashboard.activities

import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.arnold.sleepmonitor.R
import com.arnold.sleepmonitor.databinding.ActivityDashSensorTestingBinding
import com.arnold.sleepmonitor.recorder.*

class SensorTesting : AppCompatActivity() {
    private lateinit var binding: ActivityDashSensorTestingBinding
    val TAG = "SensorTesting"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashSensorTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: Button = binding.button
        val lightValue: TextView = binding.lightValue
        val accXValue: TextView = binding.accXValue
        val accYValue: TextView = binding.accYValue
        val accZValue: TextView = binding.accZValue
        val voiceVolumeValue: TextView = binding.voiceVolumeValue
        val voiceReverseCount: TextView = binding.voiceReverseCount
        val moveValue: TextView = binding.moveValue
        val snoreValue: TextView = binding.snoreValue
        val timeValue: TextView = binding.timeValue
        val runningTimeValue: TextView = binding.curTimeValue

        val handler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val event = msg.obj as MSensorEvent
                if (event.type == MSensorType.LIGHT) {
                    lightValue.text = event.value1
                }
                if (event.type == MSensorType.LINEAR_ACCELERATION) {
                    accXValue.text = event.value1
                    accYValue.text = event.value2
                    accZValue.text = event.value3
                }
                if (event.type == MSensorType.VOICE) {
                    voiceVolumeValue.text = event.value1
                    voiceReverseCount.text = event.value2
                }
            }
        }

        LightRecorder.setHandler(handler)
        LinearAccRecorder.setHandler(handler)
        VoiceRecorder.setHandler(handler)

        val startButText = resources.getString(R.string.test_button)
        val stopButText = resources.getString(R.string.test_button_stop)

        var buttonPressed = false
        button.setOnClickListener {
            if (!buttonPressed) {
                buttonPressed = true
                Log.i(TAG, "Start Button pressed")
                stopButText.also { button.text = it }
                LightRecorder.startSensor()
                LinearAccRecorder.startSensor()
                VoiceRecorder.startSensor()
            } else {
                buttonPressed = false
                Log.i(TAG, "Stop Button pressed")
                startButText.also { button.text = it }
                LightRecorder.stopSensor()
                LinearAccRecorder.stopSensor()
                VoiceRecorder.stopSensor()
            }
        }
    }
}