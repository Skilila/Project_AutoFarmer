package com.example.autofarmer.farm.repository

import com.example.autofarmer.farm.domain.Weather
import org.springframework.data.jpa.repository.JpaRepository

// 날씨 정보 인터페이스
interface WeatherRepository : JpaRepository<Weather, Long>
