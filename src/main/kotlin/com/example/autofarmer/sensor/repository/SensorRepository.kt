package com.example.autofarmer.sensor.repository

import com.example.autofarmer.sensor.domain.Sensor
import org.springframework.data.jpa.repository.JpaRepository

interface SensorRepository : JpaRepository<Sensor, Long>
