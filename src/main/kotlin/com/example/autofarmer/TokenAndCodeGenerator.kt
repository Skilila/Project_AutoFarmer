package com.example.autofarmer

import java.security.SecureRandom

object TokenAndCodeGenerator {
    private const val TOKEN_LENGTH = 32
    private const val CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    private val secureRandom = SecureRandom()

    fun generateSecureToken(): String {
        return (1..TOKEN_LENGTH)
            .map { CHARACTERS[secureRandom.nextInt(CHARACTERS.length)] }
            .joinToString("")
    }

    //휴대폰 인증번호 생성
    fun generateNumericCode(): String {
        val codeLength = 6 // 인증번호 길이
        val numbers = "0123456789"
        return (1..codeLength)
            .map { numbers[secureRandom.nextInt(numbers.length)] }
            .joinToString("")
    }
}
