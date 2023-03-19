package com.arnold.sleepmonitor

import android.util.Log
import java.io.File

class FileHandler{
    private val context = MApplication.context
    fun checkFolder(folderName: String) {
        val folder = File(context.getExternalFilesDir(null), folderName)
        if (!folder.exists()) {
            Log.d("FileHandler", "Creating folder: $folderName")
            folder.mkdir()
        } else {
            Log.d("FileHandler", "Folder already exists: $folderName")
        }
    }

    fun listFiles(folderName: String) : Array<out File>? {
        val folder = File(context.getExternalFilesDir(null), folderName)
        return folder.listFiles()
    }

    fun readData(folderName: String, identifier: String) : String {
        val filename = "$folderName-$identifier"
        val path = File(context.getExternalFilesDir(null), folderName)

        Log.d("FileHandler", "Reading file: $folderName/$filename")
        return File(path, filename).readText()
    }

    fun saveData(folderName: String, identifier: String, data: String) {
        val filename = "$folderName-$identifier"
        val path = File(context.getExternalFilesDir(null), folderName)

        Log.d("FileHandler", "Saving to file: $folderName/$filename")
        File(path, filename).writeText(data)
    }
}