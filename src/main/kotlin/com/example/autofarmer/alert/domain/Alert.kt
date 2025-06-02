package com.example.autofarmer.alert.domain

import com.example.autofarmer.user.domain.User
import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "alert", schema = "smartfarmdb", indexes = [
        Index(name = "fk_alert_user1_idx", columnList = "user_id")
    ]
)
class Alert(
    @Id
    @Tsid
    var alertId: Long? = null,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @field:NotNull
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    var alertType: String = "",

    @field:NotNull
    @Lob
    @Column(nullable = false)
    var message: String = "",

    @field:NotNull
    @Column(nullable = false)
    var isRead: Boolean = false,

    @field:NotNull
    @CreatedDate
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
)
