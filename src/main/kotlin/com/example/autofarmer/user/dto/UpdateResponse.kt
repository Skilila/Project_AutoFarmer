package com.example.autofarmer.user.dto

import com.example.autofarmer.user.domain.User
import java.io.Serializable
import java.time.Instant

data class UpdateResponse(
    val userId: Long?,
    val nickname: String,
    val email: String,
    val password: String,
    val alertType: String?,
    val updatedAt: Instant?
) : Serializable {
    companion object {
        fun fromEntity(user: User): UpdateResponse {
            return UpdateResponse(
                user.userId,
                user.nickname,
                user.email,
                user.password,
                user.alertType,
                user.updatedAt
            )
        }
    }
}
