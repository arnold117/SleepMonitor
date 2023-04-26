package com.arnold.sleepmonitor.process

import com.arnold.sleepmonitor.data_structure.NightData
import com.arnold.sleepmonitor.data_structure.SingleTimeData
import com.arnold.sleepmonitor.data_structure.SingleUnitData
import com.arnold.sleepmonitor.data_structure.WeekData
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf

class Converter {
    private val calculator = Calculator()

    fun singleTime2DataFrame(list: List<SingleTimeData>) : AnyFrame {
        val time by column(list.map { it.time })
        val lux by column(list.map { it.lux })
        val acc_x by column(list.map { it.acc_x })
        val acc_y by column(list.map { it.acc_y })
        val acc_z by column(list.map { it.acc_z })
        val volume by column(list.map { it.volume })
        val reverse by column(list.map { it.reverse })

        return dataFrameOf(time, lux, acc_x, acc_y, acc_z, volume, reverse)
    }

    fun dataFrame2SingleTime(dataFrame: DataFrame<*>) : List<SingleTimeData> {
        val time = dataFrame["time"].toList()
        val lux = dataFrame["lux"].toList()
        val acc_x = dataFrame["acc_x"].toList()
        val acc_y = dataFrame["acc_y"].toList()
        val acc_z = dataFrame["acc_z"].toList()
        val volume = dataFrame["volume"].toList()
        val reverse = dataFrame["reverse"].toList()

        val list = mutableListOf<SingleTimeData>()
        for (i in time.indices) {
            list.add(
                SingleTimeData(
                    time[i].toString(),
                    lux[i].toString().toDouble(),
                    acc_x[i].toString().toDouble(),
                    acc_y[i].toString().toDouble(),
                    acc_z[i].toString().toDouble(),
                    volume[i].toString().toDouble(),
                    reverse[i].toString().toInt()
                )
            )
        }

        return list
    }

    fun singleUnit2DataFrame(list: List<SingleUnitData>) : AnyFrame {
        val time by column(list.map { it.time })
        val meanLux by column(list.map { it.meanLux })
        val movesCount by column(list.map { it.movesCount })
        val snoreCount by column(list.map { it.snoreCount })
        val meanEnvironmentVolume by column(list.map { it.meanEnvironmentVolume })
        val status by column(list.map { it.status })

        return dataFrameOf(time, meanLux, movesCount, snoreCount, meanEnvironmentVolume, status)
    }

    fun dataFrame2SingleUnit(dataFrame: DataFrame<*>) : List<SingleUnitData> {
        val time = dataFrame["time"].toList()
        val meanLux = dataFrame["meanLux"].toList()
        val movesCount = dataFrame["movesCount"].toList()
        val snoreCount = dataFrame["snoreCount"].toList()
        val meanEnvironmentVolume = dataFrame["meanEnvironmentVolume"].toList()
        val status = dataFrame["status"].toList()

        val list = mutableListOf<SingleUnitData>()
        for (i in time.indices) {
            list.add(
                SingleUnitData(
                    time[i].toString(),
                    meanLux[i].toString().toDouble(),
                    movesCount[i].toString().toInt(),
                    snoreCount[i].toString().toInt(),
                    meanEnvironmentVolume[i].toString().toDouble(),
                    status[i].toString().toInt()
                )
            )
        }

        return list
    }

    fun singleUnit2Night(list: List<SingleUnitData>) : NightData {

        return NightData(
            list.first().time,
            list.last().time,
            calculator.duration(list.first().time, list.last().time),
            list.map { it.meanLux }.average(),
            list.map { it.meanEnvironmentVolume }.average(),
            calculator.environmentScore(list),
            calculator.deepRatio(list),
            calculator.lightRatio(list),
            list.filter { it.status == 2 }.size,
            calculator.respirationQualityScore(list),
            calculator.sleepScore(list),
        )
    }

    fun night2Week(list: List<NightData>) : WeekData {
        return WeekData(
            list.map {it.duration}.average().toInt(),
            list.map {it.meanLux}.average(),
            list.map {it.meanVolume}.average(),
            list.map {it.deepSleepRatio}.average(),
            list.map { it.lightSleepRatio }.average(),
            list.map { it.awakeCount }.average().toInt(),
            list.map { it.environmentScore }.average().toInt(),
            list.map { it.respirationQualityScore }.average().toInt(),
            list.map { it.sleepScore }.average().toInt()
        )
    }

/*
 fun dataFrame2SingleTime(dataFrame: DataFrame) : List<SingleTimeData> {
     val list = mutableListOf<SingleTimeData>()

     for (i in 0 until dataFrame.nrow) {
         val time = dataFrame[i, 0].toString()
         val lux = dataFrame[i, 1].toString().toDouble()
         val acc_x = dataFrame[i, 2].toString().toDouble()
         val acc_y = dataFrame[i, 3].toString().toDouble()
         val acc_z = dataFrame[i, 4].toString().toDouble()
         val volume = dataFrame[i, 5].toString().toDouble()
         val reverse = dataFrame[i, 6].toString().toInt()

         list.add(SingleTimeData(time, lux, acc_x, acc_y, acc_z, volume, reverse))
     }

     return list
 }

 fun singleTime2Unit(singleTimeData: Li<SingleTimeData>) : SingleUnitData {
     val singleUnitData = SingleUnitData(
         "0",
         0.0,
         0,
         0,
         0,
         0.0,
         0,
     )

     singleUnitData.time = singleTimeData[0].time
     singleUnitData.meanLux = singleTimeData.map { it.lux }.average()
     singleUnitData.movesCount = calculator.movesCount(singleTimeData)
     singleUnitData.snoreCount = calculator.snoreCount(singleTimeData)
     singleUnitData.meanEnvironmentVolume = singleTimeData.map { it.volume }.average()

     return singleUnitData
 }

 fun singleUnit2Night(singleUnitData: List<SingleUnitData>) : NightData {
     val nightData = NightData(
         "0",
         "0",
         0,
         0,
         0.0,
         0,
         0.0,
         0.0,
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
     nightData.environmentScore = singleUnitData.map { it.meanLux }.average().toInt()
     nightData.deepSleepRatio = singleUnitData.filter { it.meanLux < 0.1 }.size.toDouble() / singleUnitData.size
     nightData.lightSleepRatio = singleUnitData.filter { it.meanLux > 0.1 }.size.toDouble() / singleUnitData.size
     //nightData.awakeCount = singleUnitData.map { it.awakeCount }.average().toInt()
     nightData.deepContinuesScore = singleUnitData.filter { it.meanLux < 0.1 }.size
     nightData.respirationQualityScore = singleUnitData.map { it.meanEnvironmentVolume }.average().toInt()
     nightData.sleepScore = singleUnitData.map { it.meanLux }.average().toInt()

     return nightData
 }

 fun singleUnit2DataFrame(list: List<SingleUnitData>) : AnyFrame {
     val time by column(list.map { it.time })
     val meanLux by column(list.map { it.meanLux })
     val movesCount by column(list.map { it.movesCount })
     val SnoreCount by column(list.map { it.snoreCount })
     val meanEnvironmentVolume by column(list.map { it.meanEnvironmentVolume })
     val noiseVolume by column(list.map { it.noiseVolume })
     val noiseCount by column(list.map { it.noiseCount })
     val awakeCount by column(list.map { it.awakeCount })

     return dataFrameOf(
         time, meanLux, movesCount, SnoreCount, meanEnvironmentVolume, noiseVolume, noiseCount, awakeCount
     )
 }

 fun dataFrame2SingleUnit(dataFrame: DataFrame<*>) : List<SingleUnitData> {
     val time = dataFrame["time"].toList()
     val meanLux = dataFrame["meanLux"].toList()
     val movesCount = dataFrame["movesCount"].toList()
     val snoreCount = dataFrame["snoreCount"].toList()
     val meanEnvironmentVolume = dataFrame["meanEnvironmentVolume"].toList()
     val noiseVolume = dataFrame["noiseVolume"].toList()
     val noiseCount = dataFrame["noiseCount"].toList()
     val awakeCount = dataFrame["awakeCount"].toList()

     val list = mutableListOf<SingleUnitData>()
     for (i in time.indices) {
         list.add(
             SingleUnitData(
                 time[i].toString(),
                 meanLux[i].toString().toDouble(),
                 movesCount[i].toString().toInt(),
                 snoreCount[i].toString().toInt(),
                 meanEnvironmentVolume[i].toString().toDouble(),
                 noiseVolume[i].toString().toDouble(),
                 noiseCount[i].toString().toInt(),
                 awakeCount[i].toString().toInt()
             )
         )
     }

     return list
 }*/
}
