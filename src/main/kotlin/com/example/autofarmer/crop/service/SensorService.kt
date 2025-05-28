package com.example.autofarmer.crop.service

import com.example.autofarmer.crop.domain.Sensor
import com.example.autofarmer.crop.repository.SensorRepository
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
