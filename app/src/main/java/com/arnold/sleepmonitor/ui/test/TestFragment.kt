package com.arnold.sleepmonitor.ui.test

import android.annotation.SuppressLint
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.arnold.sleepmonitor.R
import com.arnold.sleepmonitor.databinding.FragmentTestBinding
import com.arnold.sleepmonitor.recorder.*

/**
* A simple [Fragment] subclass.
* Use the [TestFragment.newInstance] factory method to
* create an instance of this fragment.
*/
class TestFragment : Fragment() {
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    val TAG = "TestFragment"

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val testViewModel =
            ViewModelProvider(this)[TestViewModel::class.java]

        _binding = FragmentTestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val button: Button = binding.button
        val lightValue: TextView = binding.lightValue
        val accXValue: TextView = binding.accXValue
        val accYValue: TextView = binding.accYValue
        val accZValue: TextView = binding.accZValue
        val voiceVolumeValue: TextView = binding.voiceVolumeValue
        val voiceMeanHzValue: TextView = binding.voiceMeanHzValue
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
                    voiceMeanHzValue.text = event.value2
                }
            }
        }

        LightRecorder.setHandler(handler)
        LinearAccRecorder.setHandler(handler)
        VoiceRecorder.setHandler(handler)

        var buttonPressed = false
        button.setOnClickListener {
            if (!buttonPressed) {
                buttonPressed = true
                "@string/test_button_stop".also { button.text = it }
                LightRecorder.startSensor()
                LinearAccRecorder.startSensor()
                VoiceRecorder.startSensor()
            } else {
                buttonPressed = false
                "@string/test_button".also { button.text = it }
                LightRecorder.stopSensor()
                LinearAccRecorder.stopSensor()
                VoiceRecorder.stopSensor()
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}