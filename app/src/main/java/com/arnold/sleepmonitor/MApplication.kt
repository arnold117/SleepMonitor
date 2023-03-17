package com.arnold.sleepmonitor

import android.app.Application

class MApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Application
    }
}