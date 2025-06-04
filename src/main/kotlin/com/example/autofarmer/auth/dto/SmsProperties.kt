package com.example.autofarmer.auth.dto

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "coolsms.api")
data class SmsProperties(
    val key: String, // API 키
    val secret: String, // API 비밀 키
    val phone: String, // 발신자 전화번호 (예: "01012345678"
)
