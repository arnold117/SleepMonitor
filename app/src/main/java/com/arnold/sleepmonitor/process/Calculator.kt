package com.arnold.sleepmonitor.process

import com.arnold.sleepmonitor.data_structure.SingleTimeData

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
}