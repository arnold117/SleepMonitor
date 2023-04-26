package com.arnold.sleepmonitor

import java.time.format.DateTimeFormatter

object Cache {
    var isRecording = false

    val timeGap = 5

    val stdDurationLow = 360
    val stdDurationHigh = 600

    val stdDeepRatioLow = 0.2
    val stdDeepRatioHigh = 0.6

    val stdLightRatioHigh = 0.55

    val stdAwakeHigh = 2
    val stdSnoreHighPerHour = 1

    val stdLuxHigh = 5.0
    val stdVolHigh = 32.0
}