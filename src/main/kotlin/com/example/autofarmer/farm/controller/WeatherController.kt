package com.example.autofarmer.farm.controller

import com.example.autofarmer.farm.domain.Weather
import com.example.autofarmer.farm.service.WeatherService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// 날씨 정보 컨트롤러
@RestController
@RequestMapping("/api/weather")
class WeatherController(
    private val weatherService: WeatherService
) {
    // 날씨 정보 조회 (ID로)
    @GetMapping("/{weatherId}")
    fun getWeatherById(@PathVariable weatherId: Long): ResponseEntity<Weather> =
        ResponseEntity.ok(weatherService.getWeatherById(weatherId))

    // 날씨 정보 생성
    @PostMapping("/create")
    fun createWeather(@RequestBody weather: Weather): ResponseEntity<Weather> =
        ResponseEntity.ok(weatherService.createWeather(weather))

    // 날씨 정보 업데이트
    @PutMapping("/{weatherId}")
    fun updateWeather(@PathVariable weatherId: Long, @RequestBody updated: Weather): ResponseEntity<Weather> =
        ResponseEntity.ok(weatherService.updateWeather(weatherId, updated))
}
