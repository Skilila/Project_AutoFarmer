package com.example.autofarmer.farm.domain

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(name = "weather", schema = "smartfarmdb")
class Weather(
    @Id
    @Tsid
    var weatherId: Long? = null,

    @field:NotNull
    @Column(nullable = false)
    var fcstAt: LocalDateTime,

    var temperature: Double? = null,

    var humidity: Double? = null,

    @Size(max = 10)
    @Column(length = 10)
    var sky: String? = null
)
