package com.example.autofarmer.farm.domain

import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(
    name = "sensor", schema = "smartfarmdb", indexes = [
        Index(name = "fk_sensor_crop1_idx", columnList = "crop_id")
    ]
)
class Sensor(
    @Id
    var sensorId: Long = TsidCreator.getTsid().toLong(),

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_id")
    var crop: Crop,

    @field:NotNull
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    var type: String = "TEMPERATURE",

    @field:NotNull
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    var status: String = "NORMAL",

    @field:NotNull
    @Lob
    var description: String = ""
)
