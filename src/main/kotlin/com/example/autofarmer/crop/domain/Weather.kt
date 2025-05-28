package com.example.autofarmer.crop.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "weather", schema = "smartfarmdb")
class Weather {
    @Id
    @Column(name = "weather_id", columnDefinition = "int UNSIGNED not null")
    var id: Long? = null

    @NotNull
    @Column(name = "fcst_at", nullable = false)
    var fcstAt: Instant? = null

    @Column(name = "temperature")
    var temperature: Float? = null

    @Column(name = "humidity")
    var humidity: Float? = null

    @Size(max = 10)
    @Column(name = "sky", length = 10)
    var sky: String? = null
}
