package com.example.autofarmer.user.dto

import com.example.autofarmer.user.domain.User
import java.io.Serializable
import java.time.LocalDateTime

data class UpdateResponse(
    val userNo: Long,
    val nickname: String,
    val email: String,
    val password: String,
    val notificationType: String?,
    val updatedAt: LocalDateTime?
) : Serializable {
    companion object {
        fun fromEntity(user: User): UpdateResponse {
            return UpdateResponse(
                user.userNo,
                user.nickname,
                user.email,
                user.password,
                user.notificationType,
                user.updatedAt
            )
        }
    }
}
