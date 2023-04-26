package com.arnold.sleepmonitor.process

import android.util.Log
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
        val meanLux = list.map { it.meanLux }.average()
        val meanVolume = list.map { it.meanEnvironmentVolume }.average()

        val divLight = (meanLux - Cache.stdLuxHigh) / Cache.stdLuxHigh
        val divVolume = (meanVolume - Cache.stdVolHigh) / Cache.stdVolHigh

        val score = (0.5 - divLight) * 50 + (0.5 - divVolume) * 50
        if (score >= 100) {
            return 100
        } else if (score <= 0) {
            return 0
        }

        return score.toInt()
    }

    fun deepContinuesScore(list: List<SingleUnitData>): Int {


        return 0
    }

    fun respirationQualityScore(list: List<SingleUnitData>): Int {
        val calculator = Calculator()

        val snoreCount = list.map { it.snoreCount }.sum()
        val duration = calculator.duration(list[0].time, list[list.size - 1].time)

        val hour = duration / 60
        val snoreHigh = Cache.stdSnoreHighPerHour * hour
        val div : Double = (snoreCount.toDouble() - snoreHigh) / snoreHigh

        Log.d("snoreCount", snoreCount.toString())
        Log.d("duration", duration.toString())
        Log.d("hour", hour.toString())
        Log.d("snoreHigh", snoreHigh.toString())
        Log.d("div", div.toString())

        val score = (0.5 - div) * 100
        if (score >= 100) {
            return 100
        } else if (score <= 0) {
            return 0
        }

        return score.toInt()
    }

    fun sleepScore(list: List<SingleUnitData>): Int {
        val envScore = environmentScore(list)
        val deepScore = deepContinuesScore(list)
        val respirationScore = respirationQualityScore(list)



        return 0
    }
}