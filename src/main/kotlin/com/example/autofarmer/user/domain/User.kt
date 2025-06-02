package com.example.autofarmer.user.domain

import com.example.autofarmer.user.dto.UserDTO
import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "user", schema = "smartfarmdb", uniqueConstraints = [
        UniqueConstraint(name = "nickname_UNIQUE", columnNames = ["nickname"]),
        UniqueConstraint(name = "email_UNIQUE", columnNames = ["email"]),
        UniqueConstraint(name = "password_UNIQUE", columnNames = ["password"])
    ]
)
class User(
    @Id
    @Tsid
    var userId: Long? = null,

    @Size(max = 30)
    @field:NotNull
    @Column(nullable = false, length = 30)
    var nickname: String = "",

    @Size(max = 255)
    @field:NotNull
    @Column(nullable = false, length = 255)
    var email: String = "",

    @Size(max = 255)
    @field:NotNull
    @Column(nullable = false, length = 255)
    var password: String = "",

    @Size(max = 10)
    @field:NotNull
    @Column(name = "role", nullable = false, length = 10)
    var role: String = "USER",

    @Size(max = 10)
    @field:NotNull
    @Column(nullable = false, length = 10)
    var status: String = "ACTIVE",
) {
    var lastLogin: LocalDateTime? = null

    @field:NotNull
    @CreatedDate
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @field:NotNull
    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun toDTO(): UserDTO {
        return UserDTO(
            nickname = this.nickname,
            email = this.email,
        )
    }
}
