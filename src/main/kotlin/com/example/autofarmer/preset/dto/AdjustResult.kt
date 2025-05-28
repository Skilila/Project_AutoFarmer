package com.example.autofarmer.preset.dto

data class AdjustResult(
    val beforeTemp: Double?,
    val beforeHumidity: Double?,
    val afterTemp: Double,
    val afterHumidity: Double,
    val tempAdjustment: Double?,
    val humidityAdjustment: Double?
)
