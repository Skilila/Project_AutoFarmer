package com.example.autofarmer.crop.repository

import com.example.autofarmer.crop.domain.Sensor
import org.springframework.data.jpa.repository.JpaRepository

interface SensorRepository : JpaRepository<Sensor, Long>
