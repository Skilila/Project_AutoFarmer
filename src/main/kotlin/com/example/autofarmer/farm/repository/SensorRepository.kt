package com.example.autofarmer.farm.repository

import com.example.autofarmer.farm.domain.Sensor
import org.springframework.data.jpa.repository.JpaRepository

interface SensorRepository : JpaRepository<Sensor, Long>
