package com.example.autofarmer.user.domain

import com.example.autofarmer.crop.domain.Crop
import com.example.autofarmer.user.dto.UserDTO
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(
    name = "user", schema = "smartfarmdb", uniqueConstraints = [
        UniqueConstraint(name = "nickname_UNIQUE", columnNames = ["nickname"]),
        UniqueConstraint(name = "email_UNIQUE", columnNames = ["email"]),
        UniqueConstraint(name = "password_UNIQUE", columnNames = ["password"])
    ]
)
class User(
    @Size(max = 255)
    @NotNull
    @Column(nullable = false)
    var email: String = "",
    @Size(max = 30)
    @NotNull
    @Column(nullable = false, length = 30)
    var nickname: String = "",
    @Size(max = 255)
    @NotNull
    @Column(nullable = false)
    var password: String = "",
    @Size(max = 10)
    @NotNull
    @Column(nullable = false, length = 10)
    var status: String = "ACTIVE",
    @Id
    var userId: Long = TsidCreator.getTsid().toLong()
) {
    var lastLogin: Instant? = null

    @NotNull
    @CreatedDate
    @Column(nullable = false)
    var createdAt: Instant = Instant.now()

    var updatedAt: Instant? = null

    @Size(max = 10)
    @NotNull
    @Column(nullable = false, length = 10)
    var alertType: String = "NONE"

    @Size(max = 10)
    @NotNull
    @Column(name = "role", nullable = false, length = 10)
    var role: String? = null

    @ManyToMany
    @JoinTable(
        name = "user_favorite_crop",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "crop_id")]
    )
    var favoriteCrops: MutableSet<Crop> = mutableSetOf()

    fun toDTO(): UserDTO {
        return UserDTO(
            this.email,
            this.nickname,
            this.status
        )
    }
}
