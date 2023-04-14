package com.arnold.sleepmonitor.process

import com.arnold.sleepmonitor.data_structure.SingleTimeData
import com.arnold.sleepmonitor.data_structure.SingleUnitData

class SingleTime2Unit {
    fun singleTime2Unit(singleTimeData: List<SingleTimeData>): List<SingleUnitData> {
        val singleUnitDataList = mutableListOf<SingleUnitData>()
        var singleUnitData = SingleUnitData()
        var time = 0
        var meanLux = 0.0
        var movesCount = 0
        var snoreCount = 0
        var meanEnvironmentVolume = 0.0
        var noiseVolume = 0.0
        var noiseCount = 0
        var awakeCount = 0
        var i = 0
        while (i < singleTimeData.size) {
            if (i % 10 == 0) {
                if (i != 0) {
                    singleUnitData = SingleUnitData(
                        time.toString(),
                        meanLux.toString(),
                        movesCount.toString(),
                        snoreCount.toString(),
                        meanEnvironmentVolume.toString(),
                        noiseVolume.toString(),
                        noiseCount.toString(),
                        awakeCount.toString()
                    )
                    singleUnitDataList.add(singleUnitData)
                }
                time = singleTimeData[i].time.toInt()
                meanLux = singleTimeData[i].lux.toDouble()
                movesCount = singleTimeData[i].acc_x.toInt() + singleTimeData[i].acc_y.toInt() + singleTimeData[i].acc_z.toInt()
                snoreCount = 0
                meanEnvironmentVolume = singleTimeData[i].volume.toDouble()
                noiseVolume = 0.0
                noiseCount = 0
                awakeCount = 0
            } else {
                meanLux += singleTimeData[i].lux.toDouble()
                movesCount += singleTimeData[i].acc_x.toInt() + singleTimeData[i].acc_y.toInt() + singleTimeData[i].acc_z.toInt()
                meanEnvironmentVolume += singleTimeData[i].volume.toDouble()
                if (singleTimeData[i].volume.toDouble() > 50) {
                    noiseVolume += singleTimeData[i].volume.toDouble()
                    noiseCount++
                }
                if (singleTimeData[i].acc_x.toInt() + singleTimeData[i].acc_y.toInt() + singleTimeData[i].acc_z.toInt() > 100) {
                    awakeCount++
                }
            }
            i++
        }
        return singleUnitDataList
    }
}