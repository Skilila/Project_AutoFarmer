package com.example.autofarmer.farm.domain

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

// 날씨 엔티티 클래스
@Entity
@Table(name = "weather", schema = "smartfarmdb")
class Weather(
    @Id
    @Tsid
    var weatherId: Long? = null, // 날씨 ID

    @field:NotNull
    @Column(nullable = false)
    var fcstAt: LocalDateTime, // 예보 시간

    var temperature: Double? = null, // 현재 온도

    var temperatureMin: Double? = null, // 최저 온도

    var temperatureMax: Double? = null, // 최고 온도

    var humidity: Double? = null, // 현재 습도

    @Size(max = 10)
    @Column(length = 10)
    var sky: String? = null // 하늘 상태
)
