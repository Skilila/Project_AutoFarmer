package com.example.autofarmer.sensor.service

import com.example.autofarmer.sensor.domain.Sensor
import com.example.autofarmer.sensor.repository.SensorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SensorService @Autowired constructor(
    private val sensorRepository: SensorRepository
) {
    fun saveSensorData(sensorData: Sensor): Sensor {
        return sensorRepository.save(sensorData)
    }

}
