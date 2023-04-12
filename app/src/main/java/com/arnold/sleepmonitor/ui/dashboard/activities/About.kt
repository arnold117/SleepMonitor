package com.arnold.sleepmonitor.ui.dashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.arnold.sleepmonitor.R
import com.arnold.sleepmonitor.databinding.ActivityDashAboutBinding

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDashAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val author = binding.aboutAuthor
        val email = binding.aboutEmail
        val github = binding.aboutCode
        val license = binding.aboutLicense

        author.movementMethod = LinkMovementMethod.getInstance()
        email.movementMethod = LinkMovementMethod.getInstance()
        github.movementMethod = LinkMovementMethod.getInstance()
        license.movementMethod = LinkMovementMethod.getInstance()
    }
}