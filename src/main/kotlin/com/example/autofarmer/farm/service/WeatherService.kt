package com.example.autofarmer.farm.service

import com.example.autofarmer.farm.domain.Weather
import com.example.autofarmer.farm.repository.WeatherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// 날씨 서비스
@Service
class WeatherService(
    private val weatherRepository: WeatherRepository
) {
    // 날씨 상세정보 조회 (ID로)
    fun getWeatherById(weatherId: Long): Weather =
        weatherRepository.findById(weatherId).orElseThrow { NoSuchElementException("Weather not found") }

    // 날씨 정보 생성
    @Transactional
    fun createWeather(weather: Weather): Weather = weatherRepository.save(weather)

    // 날씨 정보 업데이트
    @Transactional
    fun updateWeather(weatherId: Long, updated: Weather): Weather {
        val weather = getWeatherById(weatherId)
        weather.fcstAt = updated.fcstAt
        weather.temperature = updated.temperature
        weather.temperatureMin = updated.temperatureMin
        weather.temperatureMax = updated.temperatureMax
        weather.humidity = updated.humidity
        weather.sky = updated.sky
        return weatherRepository.save(weather)
    }
}
