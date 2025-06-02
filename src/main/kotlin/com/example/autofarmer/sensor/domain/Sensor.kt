package com.example.autofarmer.sensor.domain

import com.example.autofarmer.farm.domain.Crop
import io.hypersistence.utils.hibernate.id.Tsid
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
    @Tsid
    var sensorId: Long? = null,

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
    @Column(nullable = false)
    var description: String = ""
)
