package com.arnold.sleepmonitor.data_structure

data class SingleUnitData(
    var time: String,
    var meanLux: Double,
    var movesCount: Int,
    var snoreCount: Int,
    var meanEnvironmentVolume: Double,
    var noiseVolume: Double,
    var noiseCount: Int,
    var awakeCount: Int
)
