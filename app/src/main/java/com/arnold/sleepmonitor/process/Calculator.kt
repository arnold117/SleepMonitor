package com.arnold.sleepmonitor.process

import com.arnold.sleepmonitor.Cache
import com.arnold.sleepmonitor.data_structure.SingleTimeData
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object Calculator {
    fun movesCount(singleTimeData: List<SingleTimeData>) : Int {
        return singleTimeData.filter {
            it.acc_x > 0.1 || it.acc_y > 0.1 || it.acc_z > 0.1
        }.size
    }

    fun snoreCount(singleTimeData: List<SingleTimeData>) : Int {
        return singleTimeData.filter {
            // ai generated, need to be modified
            it.volume > 0.1
        }.size
    }

    fun duration(singleTimeData: List<SingleTimeData>) : Int {
        val start = LocalDateTime.parse(singleTimeData.first().time, Cache.timeFormatter)
        val end = LocalDateTime.parse(singleTimeData.last().time, Cache.timeFormatter)

        return ChronoUnit.MINUTES.between(
            start,
            end
        ).toInt()
    }
}