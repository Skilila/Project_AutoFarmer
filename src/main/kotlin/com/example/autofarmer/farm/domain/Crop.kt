package com.example.autofarmer.farm.domain

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(
    name = "crop", schema = "smartfarmdb", uniqueConstraints = [
        UniqueConstraint(name = "name_UNIQUE", columnNames = ["name"])
    ]
)
class Crop(
    @Id
    @Tsid
    var cropId: Long? = null, // 작물 ID

    @field:NotNull
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    var name: String = "", // 작물 이름

    @field:NotNull
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    var category: String = "", // 작물 품종

    var temperature: Double? = null, // 작물 현재 온도

    var humidity: Double? = null, // 작물 현재 습도

    @field:NotNull
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    var status: String = "NORMAL", // 작물 상태

    var isPreset: Boolean = false, // 프리셋 여부 (기본값: false, 프리셋으로 등록된 작물은 true로 설정됨
)
