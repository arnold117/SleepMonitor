package com.arnold.sleepmonitor.recorder

class MSensorEvent {
    var type: MSensorType = MSensorType.LIGHT
    var value1: String = ""
    var value2: String = ""
    var value3: String = ""
}

enum class MSensorType (i: Int) {
    LIGHT(1),
    LINEAR_ACCELERATION(2),
    VOICE(3)
}