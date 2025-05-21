package com.example.autofarmer.auth.dto

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.mail")
data class EmailProperties(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val protocol: String,
    val debug: Boolean
) /*{
    fun javaMailProperties(): Properties {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.starttls.required", "true")
        }
        return props
    }
}*/
