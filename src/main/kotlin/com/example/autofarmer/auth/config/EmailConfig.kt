package com.example.autofarmer.auth.config

import com.example.autofarmer.auth.dto.EmailProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

// 이메일 관련 설정을 위한 Configuration 클래스
@Configuration
@EnableConfigurationProperties(EmailProperties::class)
class EmailConfig(
    private val emailProperties: EmailProperties
) {
    // JavaMailSender 빈 등록
    @Bean
    fun javaMailSender(): JavaMailSender {
        // JavaMailSenderImpl을 사용하여 이메일 전송 기능을 설정
        val mailSender = JavaMailSenderImpl().apply {
            host = emailProperties.host
            port = emailProperties.port
            username = emailProperties.username
            password = emailProperties.password
        }
        return mailSender
    }
}
