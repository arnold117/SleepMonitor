package com.arnold.sleepmonitor.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arnold.sleepmonitor.FileHandler
import com.arnold.sleepmonitor.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var fileHandler = FileHandler()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val editText: EditText = binding.editText

        val buttonRead: Button = binding.buttonRead
        buttonRead.setOnClickListener() {
            Log.d("DashboardFragment", "Read Button clicked")
            textView.text = "Read Button clicked"

            editText.setText(fileHandler.readData("test", "test"))
        }

        val buttonWrite: Button = binding.buttonWrite
        buttonWrite.setOnClickListener() {
            Log.d("DashboardFragment", "Write Button clicked")
            textView.text = "Write Button clicked"

            fileHandler.checkFolder("test")
            fileHandler.saveData("test", "test", editText.text.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}