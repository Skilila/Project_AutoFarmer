package com.example.autofarmer.sensor.controller

import com.example.autofarmer.sensor.domain.Sensor
import com.example.autofarmer.sensor.service.SensorService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sensor")
class SensorController(
    val sensorService: SensorService
) {
    @PostMapping("/save")
    fun receiveSensorData(sensorData: Sensor): Sensor {
        return sensorService.saveSensorData(sensorData)
    }
}
