package com.example.autofarmer.alert.domain

import com.example.autofarmer.user.domain.User
import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

// 알림 엔티티 클래스
@Entity
@Table(name = "alert", schema = "smartfarmdb", indexes = [Index(name = "fk_alert_user1_idx", columnList = "user_id")])
class Alert(
    @Id
    @Tsid
    var alertId: Long? = null,// TSID를 사용하여 고유한 알림 ID 생성

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User, // 사용자와의 연관 관계 설정

    @field:NotNull
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    var type: String, // 알림 유형

    @field:NotNull
    @Lob
    @Column(nullable = false)
    var message: String, // 알림 메시지 내용

    @field:NotNull
    @Column(nullable = false)
    var isRead: Boolean = false, // 알림 읽음 여부 (기본값: false)

    @field:NotNull
    @CreatedDate
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now() // 알림 생성 시간 (기본값: 현재 시간)
)
