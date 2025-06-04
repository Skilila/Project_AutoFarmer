package com.example.autofarmer.auth.service

import com.example.autofarmer.auth.TokenAndCodeGenerator
import com.example.autofarmer.auth.config.SmsConfig
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class SmsService(
    private val redisTemplate: StringRedisTemplate,
    private val smsConfig: SmsConfig
) {
    private lateinit var messageService: DefaultMessageService

    //sms 발송
    fun sendSms(phone: String) {
        //인증코드 생성
        val code = TokenAndCodeGenerator.generateNumericCode()
        //인증코드 redis에 저장
        redisTemplate.opsForValue().set(
            "verification_code",
            code,
            Duration.ofMinutes(10)
        )
        //sms 발송서비스 초기화
        messageService = smsConfig.messageService()
        //sms 발송
        val message = Message().apply {
            from = phone
            to = phone
            text = "인증번호: $code"
        }
        messageService.sendOne(SingleMessageSendingRequest(message))
    }

    //sms 인증코드 검증
    fun verifySms(code: String): Boolean {
        //Redis에서 인증코드 조회
        val savedCode = redisTemplate.opsForValue().get("verification_code")
        //인증코드 검증
        return if (savedCode == code) {
            println("인증코드 검증 성공")
            true
        } else {
            println("인증코드 검증 실패")
            false
        }
    }
}
