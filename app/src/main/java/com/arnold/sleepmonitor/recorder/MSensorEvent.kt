package com.arnold.sleepmonitor.recorder

class MSensorEvent (type: MSensorType, value: String) {
    var type: MSensorType
    var value: String

    init {
        this.type = type
        this.value = value
    }
}

enum class MSensorType (i: Int) {
    LIGHT(1),
    LINEAR_ACCELERATION(2),
    MICROPHONE(3)
}