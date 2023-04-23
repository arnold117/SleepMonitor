package com.arnold.sleepmonitor.ui.home.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.arnold.sleepmonitor.MainActivity
import com.arnold.sleepmonitor.databinding.ActivitySleepBinding

class SleepActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepBinding

    private lateinit var alertBuilder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sleepButton = binding.sleepButton
        alertBuilder = AlertDialog.Builder(this)

        sleepButton.setOnClickListener {
            backToHome()
        }
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
        backToHome()
    }

    private fun startSleep() {

    }

    private fun stopSleep() {

    }

    private fun backToHome(alert: AlertDialog.Builder = alertBuilder) {
        alert.setTitle("Stop Sleep?")
            .setMessage("Are you sure you want to stop sleeping?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                val intent = android.content.Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.setNegativeButton("No") { dialogInterface, it ->
                dialogInterface.cancel()
            }.create().show()
    }
}