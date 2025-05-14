package com.example.autofarmer.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class VerificationRequest(
    @field:Email(message = "이메일 형식이 올바르지 않습니다")
    @field:NotBlank(message = "이메일을 입력해주세요")
    val email: String,
    @field:NotBlank(message = "인증코드를 입력해주세요")
    val code: String
)
