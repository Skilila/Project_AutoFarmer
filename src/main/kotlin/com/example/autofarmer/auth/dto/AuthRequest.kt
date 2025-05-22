package com.example.autofarmer.auth.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

sealed class AuthRequest {
    data class Signup(
        @field:NotBlank(message = "이메일은 필수입니다.")
        @field:Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "이메일 형식이 아닙니다."
        )
        val email: String,
        @field:NotBlank(message = "닉네임은 필수입니다.")
        val nickname: String,
        @field:NotBlank(message = "비밀번호는 필수입니다.")
        @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 최소 8자 이상, 문자와 숫자를 포함해야 합니다.")
        val password: String,
    ) : AuthRequest()

    data class SendEmail(val email: String) : AuthRequest()

    data class Login(
        @field:NotBlank(message = "이메일은 필수입니다.")
        @field:Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "이메일 형식이 아닙니다."
        )
        val email: String,
        @field:NotBlank(message = "비밀번호는 필수입니다.")
        @field:Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "비밀번호는 최소 8자 이상, 문자와 숫자를 포함해야 합니다."
        )
        val password: String,
    ) : AuthRequest()

    data class ResetPassword(
        val email: String,
        val token: String,
        val newPassword: String
    ) : AuthRequest()

    data class Logout(
        val accessToken: String
    ) : AuthRequest()
}
