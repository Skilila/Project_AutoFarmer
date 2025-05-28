package com.example.autofarmer.crop.domain

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
class Sensor {
    @Id
    var sensorId: Long = TsidCreator.getTsid().toLong() // TSID를 사용하여 고유한 ID 생성

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_id")
    var crop: Crop? = null // Crop 테이블과의 연관 관계

    @Size(max = 10)
    @NotNull
    @Column(nullable = false, length = 10)
    var type: String = "TEMPERATURE" // 기본값을 TEMPERATURE로 설정

    @Size(max = 10)
    @NotNull
    @Column(nullable = false, length = 10)
    var status: String = "NORMAL" // 기본값을 NORMAL로 설정

    @Lob
    var description: String? = null
}
