package com.example.autofarmer.auth.service

import com.example.autofarmer.auth.config.VerificationCodeManager
import jakarta.mail.Message
import jakarta.mail.internet.InternetAddress
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val emailSender: JavaMailSender,
    private val codeManager: VerificationCodeManager
) {
    fun sendVerificationEmail(toEmail: String) {
        val code = codeManager.generateCode(toEmail)
        val message = emailSender.createMimeMessage().apply {
            setFrom(InternetAddress("dream0820nv@naver.com", "AutoFarmer"))
            addRecipient(Message.RecipientType.TO, InternetAddress(toEmail))
            subject = "[인증코드] 이메일 인증을 위한 인증코드입니다."
            setText(
                """
                안녕하세요, AutoFarmer입니다.
                
                아래의 인증코드를 입력하여 이메일 인증을 완료해 주세요.
                
                인증코드: $code
                
                본 메일은 발신 전용입니다. 문의사항은 고객센터로 연락해 주세요.
                
                감사합니다.
                
                """.trimIndent(), "UTF-8"
            )
        }
        emailSender.send(message)
    }
}
