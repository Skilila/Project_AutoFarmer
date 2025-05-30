package com.example.autofarmer.alert

import com.example.autofarmer.user.domain.User
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "alert", schema = "smartfarmdb", indexes = [
        Index(name = "fk_alert_user1_idx", columnList = "user_id")
    ]
)
class Alert {
    @Id
    var alertId: Long? = TsidCreator.getTsid().toLong()

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    @Size(max = 10)
    @NotNull
    @Column(nullable = false, length = 10)
    var alertType: String = "NONE"

    @NotNull
    @CreatedDate
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @NotNull
    @Column(nullable = false)
    var isRead: Boolean = false // 기본값을 false로 설정
}
