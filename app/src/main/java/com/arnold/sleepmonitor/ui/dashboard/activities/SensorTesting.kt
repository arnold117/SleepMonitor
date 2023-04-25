package com.arnold.sleepmonitor.ui.dashboard.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.arnold.sleepmonitor.FileHandler
import com.arnold.sleepmonitor.R
import com.arnold.sleepmonitor.data_structure.SingleTimeData
import com.arnold.sleepmonitor.databinding.ActivityDashSensorTestingBinding
import com.arnold.sleepmonitor.process.Convert
import com.arnold.sleepmonitor.recorder.*
import java.time.format.DateTimeFormatter

class SensorTesting : AppCompatActivity() {
    private lateinit var binding: ActivityDashSensorTestingBinding
    val TAG = "SensorTesting"

    val dataArray = ArrayList<SingleTimeData>()

    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")

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

        val startButText = resources.getString(R.string.test_button)
        val stopButText = resources.getString(R.string.test_button_stop)
        var buttonPressed = false

        val handler: Handler = object : Handler(Looper.getMainLooper()) {
            var lightCount = false
            var accCount = false
            var voiceCount = false

            override fun handleMessage(msg: Message) {
                val event = msg.obj as MSensorEvent
                if (event.type == MSensorType.LIGHT) {
                    lightValue.text = event.value1
                    lightCount = true
                }
                if (event.type == MSensorType.LINEAR_ACCELERATION) {
                    accXValue.text = event.value1
                    accYValue.text = event.value2
                    accZValue.text = event.value3
                    accCount = true
                }
                if (event.type == MSensorType.VOICE) {
                    voiceVolumeValue.text = event.value1
                    voiceReverseCount.text = event.value2
                    voiceCount = true
                }

                if (lightCount && accCount && voiceCount) {
                    dataArray.add(SingleTimeData(
                        java.time.LocalDateTime.now().format(timeFormatter),
                        lightValue.text.toString().toDouble(),
                        accXValue.text.toString().toDouble(),
                        accYValue.text.toString().toDouble(),
                        accZValue.text.toString().toDouble(),
                        voiceVolumeValue.text.toString().toDouble(),
                        voiceReverseCount.text.toString().toInt(),
                    ))

                    if (dataArray.size > 100) {
                        buttonPressed = false
                        Log.i(TAG, "Recording Stopped")
                        startButText.also { button.text = it }
                        LightRecorder.stopSensor()
                        LinearAccRecorder.stopSensor()
                        VoiceRecorder.stopSensor()
                        val convert = Convert()
                        val fileHandler = FileHandler()
                        val df = convert.singleTime2DataFrame(dataArray)
                        fileHandler.checkFolder("test")
                        fileHandler.saveDataFrame("test", "singleTime", df)
                    }
                }
            }
        }

        LightRecorder.setHandler(handler)
        LinearAccRecorder.setHandler(handler)
        VoiceRecorder.setHandler(handler)

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

    override fun onDestroy() {
        super.onDestroy()
        LightRecorder.stopSensor()
        LinearAccRecorder.stopSensor()
        VoiceRecorder.stopSensor()
    }
}