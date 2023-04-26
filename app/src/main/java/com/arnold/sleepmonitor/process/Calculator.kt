package com.arnold.sleepmonitor.process

import com.arnold.sleepmonitor.Cache
import com.arnold.sleepmonitor.data_structure.SingleTimeData
import com.arnold.sleepmonitor.data_structure.SingleUnitData
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Calculator {
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

    fun duration(startTime: String, endTime: String) : Int {
        val start = LocalDateTime.parse(startTime)
        val end = LocalDateTime.parse(endTime)

        return ChronoUnit.MINUTES.between(
            start,
            end
        ).toInt()
    }

    fun environmentScore(list: List<SingleUnitData>): Int {

        return 0
    }

    fun deepContinuesScore(list: List<SingleUnitData>): Int {
        return 0
    }

    fun respirationQualityScore(list: List<SingleUnitData>): Int {
        return 0
    }

    fun sleepScore(list: List<SingleUnitData>): Int {
        val envScore = environmentScore(list)
        val deepScore = deepContinuesScore(list)
        val respirationScore = respirationQualityScore(list)



        return 0
    }
}