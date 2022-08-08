package com.example.vismamusic.domain.model.common

data class Time(
    val minutes: Int,
    val seconds: Int,
    val totalSeconds: Int,
    val toShow: String = ""
)

fun Int.toTime(): Time {
    val minutes = this / 60
    val seconds = this % 60
    return Time(
        minutes = minutes,
        seconds = seconds,
        totalSeconds = this,
        toShow = minutes.toString() + "m " + seconds.toString() + "s"
    )
}
