package com.example.autofarmer.farm.domain

import com.example.autofarmer.user.domain.User
import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

// 프리셋 엔티티 클래스
@Entity
@Table(
    name = "preset", schema = "smartfarmdb", indexes = [
        Index(name = "fk_preset_user1_idx", columnList = "user_id"),
        Index(name = "fk_preset_crop1_idx", columnList = "crop_id")
    ]
)
class Preset(
    @Id
    @Tsid
    var presetId: Long? = null, // 프리셋 ID

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User, // 사용자 정보

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_id", nullable = false)
    var crop: Crop, // 작물 정보

    @field:NotNull
    @Column(nullable = false)
    var temperature: Double, // 프리셋 온도

    @field:NotNull
    @Column(nullable = false)
    var humidity: Double, // 프리셋 습도
) {
    @field:NotNull
    @Column(nullable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now() // 생성 시간

    @field:NotNull
    @Column(nullable = false)
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now() // 수정 시간
}
