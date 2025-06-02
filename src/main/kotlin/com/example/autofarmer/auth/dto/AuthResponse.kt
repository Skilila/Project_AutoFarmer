package com.example.autofarmer.auth.dto

sealed class AuthResponse {
    data class Login(
        val isSuccess: Boolean,//로그인 성공 여부
        val userId: Long?,//사용자 번호
        val accessToken: String?,//엑세스 토큰
        val refreshToken: String?,//리프레시 토큰
    ) : AuthResponse()
}
