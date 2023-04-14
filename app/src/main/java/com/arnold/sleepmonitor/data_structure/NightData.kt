package com.arnold.sleepmonitor.data_structure

data class NightData(
    val startTime: String,
    val endTime: String,
    val duration: String,
    val meanLux: String,
    val meanVolume: String,
    val noiseCount: String,
    val noiseVolume: String,
    val environmentScore: String,
    val deepSleepRatio: String,
    val lightSleepRatio: String,
    val awakeCount: String,
    val deepContinuesScore: String,
    val snoreCount: String,
    val respirationQualityScore: String,
    val sleepScore: String
)
