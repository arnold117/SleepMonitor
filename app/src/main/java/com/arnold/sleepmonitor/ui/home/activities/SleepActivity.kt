package com.arnold.sleepmonitor.ui.home.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.arnold.sleepmonitor.MainActivity
import com.arnold.sleepmonitor.R
import com.arnold.sleepmonitor.databinding.ActivitySleepBinding
import java.time.format.DateTimeFormatter

class SleepActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepBinding

    private lateinit var alertBuilder: AlertDialog.Builder

    val channelId = "com.arnold.sleepmonitor"
    val channelName = "com.arnold.sleepmonitor"
    val notifId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sleepButton = binding.sleepButton
        alertBuilder = AlertDialog.Builder(this)

        Thread(timeRunnable).start()

        createNotificationChannel()

        sleepButton.setOnClickListener {
            backToHome()
        }
    }

    private val timeRunnable = object : Runnable {
        override fun run() {
            val formatter = DateTimeFormatter.ofPattern("hh:mm:ss")
            val time = java.time.LocalTime.now().format(formatter)
            binding.currentTime.text = time
            binding.currentTime.postDelayed(this, 1000)
        }
    }

    private fun createNotificationChannel(){
        val notificationChannel = android.app.NotificationChannel(
            channelId,
            channelName,
            android.app.NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(android.app.NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)

        val intent = android.content.Intent(this, SleepActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this)
            .addNextIntentWithParentStack(intent)
            .getPendingIntent(0, android.app.PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Arnold's Sleep Monitor")
            .setContentText("Recording your sleep... Good night!")
            .setSmallIcon(R.mipmap.launcher_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notifId, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
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