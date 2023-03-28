package com.arnold.sleepmonitor.ui.home

import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arnold.sleepmonitor.databinding.FragmentHomeBinding
import com.arnold.sleepmonitor.recorder.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val TAG = "HomeFragment"

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val buttonVoice: Button = binding.buttonVoice
        val buttonSensors: Button = binding.buttonSensors
        val textLight: TextView = binding.textLight
        val textAcc: TextView = binding.textAcc
        val textVoice: TextView = binding.textVoice

        val handler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val event = msg.obj as MSensorEvent
                if (event.type == MSensorType.LIGHT) {
                    textLight.text = "Light: ${event.value1}"
                }
                if (event.type == MSensorType.LINEAR_ACCELERATION) {
                    textAcc.text = "Acceleration: X:${event.value1}, Y:${event.value2}, Z:${event.value3}"
                }
                if (event.type == MSensorType.VOICE) {
                    textVoice.text = "Voice: Vol:${event.value1} (dB)"
                }
            }
        }

        LightRecorder.setHandler(handler)
        LinearAccRecorder.setHandler(handler)
        VoiceRecorder.setHandler(handler)

        homeViewModel.homeText.observe(viewLifecycleOwner) {
            textView.text = it
        }
        homeViewModel.lightText.observe(viewLifecycleOwner) {
            textLight.text = it
        }
        homeViewModel.accText.observe(viewLifecycleOwner) {
            textAcc.text = it
        }
        homeViewModel.voiceText.observe(viewLifecycleOwner) {
            textVoice.text = it
        }

        var buttonVoicePressed = false
        buttonVoice.setOnClickListener() {
            Log.d(TAG, "Voice Button clicked")
            buttonVoicePressed = !buttonVoicePressed


            if (buttonVoicePressed) {
                buttonVoice.text = "Stop Voice"
                VoiceRecorder.startSensor()
            } else {
                buttonVoice.text = "Start Voice"
                VoiceRecorder.stopSensor()
            }
        }

        var buttonSensorPressed = false
        buttonSensors.setOnClickListener() {
            Log.d(TAG, "Sensors Button clicked")
            buttonSensorPressed = !buttonSensorPressed

            if (buttonSensorPressed) {
                buttonSensors.text = "Stop Sensors"
                LightRecorder.startSensor()
                LinearAccRecorder.startSensor()
            } else {
                buttonSensors.text = "Start Sensors"
                LightRecorder.stopSensor()
                LinearAccRecorder.stopSensor()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}