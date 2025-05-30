package com.example.autofarmer.user.domain

import com.example.autofarmer.user.dto.UserDTO
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
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
    var userId: Long = TsidCreator.getTsid().toLong(),

    @Size(max = 30)
    @NotNull
    @Column(nullable = false, length = 30)
    var nickname: String = "",

    @Size(max = 255)
    @NotNull
    @Column(nullable = false, length = 255)
    var email: String = "",

    @Size(max = 255)
    @NotNull
    @Column(nullable = false, length = 255)
    var password: String = "",

    @Size(max = 10)
    @NotNull
    @Column(nullable = false, length = 10)
    var alertType: String = "NONE",

    @Size(max = 10)
    @NotNull
    @Column(name = "role", nullable = false, length = 10)
    var role: String = "USER",

    @Size(max = 10)
    @NotNull
    @Column(nullable = false, length = 10)
    var status: String = "ACTIVE",
) {
    var lastLogin: LocalDateTime? = null

    @NotNull
    @CreatedDate
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    fun toDTO(): UserDTO {
        return UserDTO(
            nickname = this.nickname,
            email = this.email,
        )
    }
}
