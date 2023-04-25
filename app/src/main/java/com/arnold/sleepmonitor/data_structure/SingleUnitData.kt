package com.arnold.sleepmonitor.data_structure

data class SingleUnitData(
    var time: String,
    var meanLux: Double,
    var movesCount: Int,
    var snoreCount: Int,
    var meanEnvironmentVolume: Double,
    var status: Int, // 0 for deep sleep, 1 for light sleep, 2 for awake
)
