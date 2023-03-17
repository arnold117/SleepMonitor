package com.arnold.sleepmonitor

import android.util.Log
import com.arnold.sleepmonitor.MApplication.Companion.context
import java.io.File

class FileHandler{
    fun listFiles() : Array<out String>? {
        return context.fileList()
    }

    fun readData(identifier: String) : String {
        val filename = "recording-$identifier"
        Log.d("FileHandler", "Reading file: $filename")
        return File(context.filesDir, filename).readText()
    }

    fun saveData(data: String, identifier: String) {
        val filename = "recording-$identifier"
        Log.d("FileHandler", "Saving to file: $filename")
        File(context.filesDir, filename).writeText(data)
    }
}