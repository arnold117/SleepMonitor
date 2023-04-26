package com.arnold.sleepmonitor.data_structure

data class WeekData(
    var aveDuration: Int,
    var aveLux: Double,
    var aveVolume: Double,
    var aveDeepRatio: Double,
    var aveLightRatio: Double,
    var aveAwakeCount: Int,
    var aveEnvironmentScore: Int,
    var aveRespirationQualityScore: Int,
    var aveSleepScore: Int
)
