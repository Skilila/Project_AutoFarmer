package com.example.autofarmer.farm.domain

import com.example.autofarmer.user.domain.User
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "preset", schema = "smartfarmdb", indexes = [
        Index(name = "fk_preset_user1_idx", columnList = "user_id"),
        Index(name = "fk_preset_crop1_idx", columnList = "crop_id")
    ]
)
class Preset(
    @Id
    var presetId: Long = TsidCreator.getTsid().toLong(),

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_id", nullable = false)
    var crop: Crop,

    @field:NotNull
    @Column(nullable = false)
    var temperature: Double,

    @field:NotNull
    @Column(nullable = false)
    var humidity: Double,
) {
    @field:NotNull
    @Column(nullable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
}
