package com.arnold.sleepmonitor.ui.home

import android.annotation.SuppressLint
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arnold.sleepmonitor.Cache
import com.arnold.sleepmonitor.R
import com.arnold.sleepmonitor.databinding.FragmentHomeBinding
import com.arnold.sleepmonitor.recorder.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val TAG = "HomeFragment"

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val overview = binding.homeCardSleepOverview
        val buttonRecording = binding.buttonRecording
        val btnStat = binding.homeBtnStat

        overview.setOnClickListener {
            Toast.makeText(context, "Go to Records Page for more detail!", Toast.LENGTH_SHORT).show()
        }

        if (Cache.isRecording) {
            buttonRecording.setImageResource(R.mipmap.ic_action_stop)
            btnStat.text = "Recording, Have a good sleep!"
        } else {
            buttonRecording.setImageResource(R.mipmap.ic_action_play)
            btnStat.text = "Press the button to start recording"
        }

        buttonRecording.setOnClickListener {
            Cache.isRecording = !Cache.isRecording
            if (Cache.isRecording) {
                buttonRecording.setImageResource(R.mipmap.ic_action_stop)
                btnStat.text = "Recording, Have a good sleep!"
            } else {
                buttonRecording.setImageResource(R.mipmap.ic_action_play)
                btnStat.text = "Press the button to start recording"
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}