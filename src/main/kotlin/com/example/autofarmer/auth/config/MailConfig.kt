package com.example.autofarmer.auth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig(
    @Value("\${spring.mail.host}")
    private val host: String,
    @Value("\${spring.mail.port}")
    private val port: Int,
    @Value("\${spring.mail.username}")
    private val username: String,
    @Value("\${spring.mail.password}")
    private val password: String

) {
    @Bean
    fun javaMailService(): JavaMailSender {
        val mailSender = JavaMailSenderImpl().apply {
            this.host = host
            this.port = port
            this.username = username
            this.password = password
            setJavaMailProperties(mailProperties())
        }
        return mailSender
    }

    private fun mailProperties(): Properties {
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.ssl.enable"] = "true"
        props["mail.smtp.ssl.trust"] = "smtp.naver.com"
        props["mail.smtp.ssl.protocols"] = "TLSv1.2"

        return props
    }
}
