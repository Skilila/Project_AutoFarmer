package com.example.autofarmer.auth.config

import com.example.autofarmer.auth.dto.SmsProperties
import net.nurigo.sdk.NurigoApp
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// SMS 관련 설정을 위한 Configuration 클래스
@Configuration
@EnableConfigurationProperties(SmsProperties::class)
class SmsConfig(
    private val smsProperties: SmsProperties
) {
    // DefaultMessageService 빈 등록
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
