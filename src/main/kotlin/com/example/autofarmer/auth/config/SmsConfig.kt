package com.example.autofarmer.auth.config

import com.example.autofarmer.auth.dto.SmsProperties
import net.nurigo.sdk.NurigoApp
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SmsProperties::class)
class SmsConfig(
    private val smsProperties: SmsProperties
) {

    @Bean
    fun messageService(): DefaultMessageService {
        val messageService = NurigoApp.initialize(
            smsProperties.key,
            smsProperties.secret,
            "https://api.coolsms.co.kr"
        )
        return messageService
    }
}
