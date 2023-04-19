package com.arnold.sleepmonitor.ui.dashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arnold.sleepmonitor.FileHandler
import com.arnold.sleepmonitor.databinding.ActivityDashStorageTestingBinding
import com.arnold.sleepmonitor.process.TestDataFrame

class StorageTesting : AppCompatActivity() {
    private var fileHandler = FileHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDashStorageTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editText = binding.editText

        val buttonRead = binding.buttonRead
        buttonRead.setOnClickListener {
            Log.d("DashboardFragment", "Read Button clicked")
            Toast.makeText(this, "Read Button clicked", Toast.LENGTH_SHORT).show()

            editText.setText(fileHandler.readData("test", "test"))
            val dataTest = TestDataFrame()
            dataTest.sampleTest()
        }

        val buttonWrite = binding.buttonWrite
        buttonWrite.setOnClickListener {
            Log.d("DashboardFragment", "Write Button clicked")
            Toast.makeText(this, "Write Button clicked", Toast.LENGTH_SHORT).show()

            fileHandler.checkFolder("test")
            fileHandler.saveData("test", "test", editText.text.toString())
        }
    }
}