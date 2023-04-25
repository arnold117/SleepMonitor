package com.arnold.sleepmonitor

import java.time.format.DateTimeFormatter

object Cache {
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.hh:mm:ss")
    var isRecording = false
}