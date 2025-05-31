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
    var cropId: Long? = null,

    @field:NotNull
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    var name: String = "",

    @field:NotNull
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    var category: String = "",

    var temperature: Double? = null,

    var humidity: Double? = null,

    @field:NotNull
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    var status: String = "NORMAL",

    var isPreset: Boolean = false,
)
