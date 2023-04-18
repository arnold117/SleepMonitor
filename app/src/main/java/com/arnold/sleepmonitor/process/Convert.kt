package com.arnold.sleepmonitor.process

import com.arnold.sleepmonitor.data_structure.NightData
import com.arnold.sleepmonitor.data_structure.SingleTimeData
import com.arnold.sleepmonitor.data_structure.SingleUnitData

class Convert {
    private val calculator = Calculator()
    fun singleTime2Unit(singleTimeData: List<SingleTimeData>) : SingleUnitData {
        val singleUnitData = SingleUnitData(
            "0",
            0.0,
            0,
            0,
            0.0,
            0.0,
            0,
            0
        )

        singleUnitData.time = singleTimeData[0].time
        singleUnitData.meanLux = singleTimeData.map { it.lux }.average()
        singleUnitData.movesCount = calculator.movesCount(singleTimeData)
        singleUnitData.SnoreCount = calculator.snoreCount(singleTimeData)
        singleUnitData.meanEnvironmentVolume = singleTimeData.map { it.volume }.average()
        // ai generated, need to be modified
        singleUnitData.noiseVolume = singleTimeData.map { it.volume }.average()
        singleUnitData.noiseCount = singleTimeData.filter { it.volume > 0.1 }.size
        singleUnitData.awakeCount = singleTimeData.filter { it.lux > 0.1 }.size
        // end

        return singleUnitData
    }

    fun singleUnit2Night(singleUnitData: List<SingleUnitData>) : NightData {
        val nightData = NightData(
            "0",
            "0",
            0,
            0,
            0.0,
            0.0,
            0.0,
            0,
            0.0,
            0.0,
            0,
            0,
            0,
            0,
            0
        )

        nightData.startTime = singleUnitData[0].time
        nightData.endTime = singleUnitData[singleUnitData.size - 1].time
        // ai generated, need to be modified
        nightData.duration = singleUnitData.size
        nightData.meanLux = singleUnitData.map { it.meanLux }.average().toInt()
        nightData.meanVolume = singleUnitData.map { it.meanEnvironmentVolume }.average()
        nightData.noiseCount = singleUnitData.map { it.noiseCount }.average()
        nightData.noiseVolume = singleUnitData.map { it.noiseVolume }.average()
        nightData.environmentScore = singleUnitData.map { it.meanLux }.average().toInt()
        nightData.deepSleepRatio = singleUnitData.filter { it.meanLux < 0.1 }.size.toDouble() / singleUnitData.size
        nightData.lightSleepRatio = singleUnitData.filter { it.meanLux > 0.1 }.size.toDouble() / singleUnitData.size
        nightData.awakeCount = singleUnitData.map { it.awakeCount }.average().toInt()
        nightData.deepContinuesScore = singleUnitData.filter { it.meanLux < 0.1 }.size
        nightData.snoreCount = singleUnitData.map { it.SnoreCount }.average().toInt()
        nightData.respirationQualityScore = singleUnitData.map { it.meanEnvironmentVolume }.average().toInt()
        nightData.sleepScore = singleUnitData.map { it.meanLux }.average().toInt()

        return nightData
    }
}