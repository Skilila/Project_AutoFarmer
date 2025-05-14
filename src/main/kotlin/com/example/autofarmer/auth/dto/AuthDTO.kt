package com.example.autofarmer.auth.dto

data class SignupRequest(val email: String, val nickname: String)
data class LoginRequest(val email: String)
data class TokenResponse(val token: String)
