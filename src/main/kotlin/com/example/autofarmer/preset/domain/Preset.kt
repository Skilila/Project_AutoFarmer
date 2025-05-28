package com.example.autofarmer.preset.domain

import com.example.autofarmer.crop.domain.Crop
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
    var presetId: Long = TsidCreator.getTsid().toLong(),//Preset테이블 PK

    @field:NotNull
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,//User테이블 참조 FK

    @field:NotNull
    @MapsId("cropId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_id", nullable = false)
    var crop: Crop,//Crop테이블 참조 FK

    @field:NotNull
    @Column(nullable = false)
    var temperature: Double,//사용자 프리셋 기온

    @field:NotNull
    @Column(nullable = false)
    var humidity: Double,//사용자 프리셋 습도

    @field:NotNull
    @Column(nullable = false)
    @CreatedDate
    var createdAt: LocalDateTime,//프리셋 생성일시

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,//프리셋 수정일시
)
