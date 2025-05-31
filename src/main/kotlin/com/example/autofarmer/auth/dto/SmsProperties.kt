package com.example.autofarmer.auth.dto

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "coolsms.api")
data class SmsProperties(
    val key: String,
    val secret: String,
    val phone: String,
)
