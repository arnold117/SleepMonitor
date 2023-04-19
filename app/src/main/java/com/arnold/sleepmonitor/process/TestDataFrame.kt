package com.arnold.sleepmonitor.process

import android.util.Log
import com.arnold.sleepmonitor.data_structure.SingleTimeData
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf

class TestDataFrame {
    fun list2DataFrameB(list: List<SingleTimeData>): DataFrame<*> {
        val time by column(list.map { it.time })
        val lux by column(list.map { it.lux })
        val acc_x by column(list.map { it.acc_x })
        val acc_y by column(list.map { it.acc_y })
        val acc_z by column(list.map { it.acc_z })
        val volume by column(list.map { it.volume })
        val frequency by column(list.map { it.frequency })

        return dataFrameOf(
            time, lux, acc_x, acc_y, acc_z, volume, frequency
        )
    }

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

        val dfB = list2DataFrameB(sample)
        Log.d("TestDataFrame", "dfB: $dfB")
    }
}