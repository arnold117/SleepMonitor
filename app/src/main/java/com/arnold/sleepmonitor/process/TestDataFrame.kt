package com.arnold.sleepmonitor.process

import android.util.Log
import com.arnold.sleepmonitor.FileHandler

class TestDataFrame {
    private val convert = Convert()
    private val fileHandler = FileHandler()
    fun sampleTest() {
        val sample = listOf(
            SampleData.singleTimeData1,
            SampleData.singleTimeData2,
            SampleData.singleTimeData3,
            SampleData.singleTimeData4,
            SampleData.singleTimeData5,
            SampleData.singleTimeData6,
            SampleData.singleTimeData7,
            SampleData.singleTimeData8,
            SampleData.singleTimeData9,
            SampleData.singleTimeData10
        )

        val dfB = convert.singleTime2DataFrame(sample)
        Log.d("TestDataFrame", "dfB: $dfB")

        fileHandler.saveDataFrame("test", "test", dfB)

        val dfC = fileHandler.readDataFrame("test", "test")
        Log.d("TestDataFrame", "dfC: $dfC")
        fileHandler.saveDataFrame("test", "test1", dfC)
    }
}