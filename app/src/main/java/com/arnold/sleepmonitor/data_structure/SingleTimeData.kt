package com.arnold.sleepmonitor.data_structure

data class SingleTimeData(
    val time: String,
    val lux: Double,
    val acc_x: Double,
    val acc_y: Double,
    val acc_z: Double,
    val volume: Double,
    val frequency: Int
)
