package com.bonsai.sciencetodo.data

enum class IntervalUnit {
    Minute,
    Hour,
    Day,
    Week,
}

object IntervalSeconds {
    const val HOUR = 3_600
    const val DAY = 86_400
    const val WEEK = 604_800
}