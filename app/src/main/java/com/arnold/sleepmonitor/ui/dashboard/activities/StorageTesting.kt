package com.arnold.sleepmonitor.ui.dashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.arnold.sleepmonitor.FileHandler
import com.arnold.sleepmonitor.databinding.ActivityDashStorageTestingBinding

class StorageTesting : AppCompatActivity() {
    private var fileHandler = FileHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDashStorageTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = binding.textDashboard
        val editText = binding.editText

        val buttonRead = binding.buttonRead
        buttonRead.setOnClickListener {
            Log.d("DashboardFragment", "Read Button clicked")
            textView.text = "Read Button clicked"

            editText.setText(fileHandler.readData("test", "test"))
        }

        val buttonWrite = binding.buttonWrite
        buttonWrite.setOnClickListener {
            Log.d("DashboardFragment", "Write Button clicked")
            textView.text = "Write Button clicked"

            fileHandler.checkFolder("test")
            fileHandler.saveData("test", "test", editText.text.toString())
        }
    }
}