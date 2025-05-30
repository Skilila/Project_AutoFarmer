package com.example.autofarmer.farm.domain

import com.github.f4b6a3.tsid.TsidCreator
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
    var weatherId: Long = TsidCreator.getTsid().toLong(),

    @field:NotNull
    @Column(nullable = false)
    var fcstAt: LocalDateTime,

    var temperature: Double? = null,

    var humidity: Double? = null,

    @Size(max = 10)
    @Column(length = 10)
    var sky: String? = null
)
