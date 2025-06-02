package com.example.autofarmer.auth.service

import com.example.autofarmer.TokenAndCodeGenerator
import com.example.autofarmer.user.repository.UserRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class EmailService(
    private val redisTemplate: StringRedisTemplate,
    private val userRepository: UserRepository,
    private val mailSender: JavaMailSender
) {
    //이메일 발송
    fun sendEmail(email: String): String {
        //이메일 인증토큰 생성
        val token = TokenAndCodeGenerator.generateSecureToken()
        //토큰 redis에 저장
        redisTemplate.opsForValue().set(
            "verification_token:${token}",
            email,
            Duration.ofMinutes(10)
        )
        //이메일 인증링크 생성
        val link = "http://localhost:8080/auth/verify-email?token=$token"
        //이메일 발송
        val message = SimpleMailMessage().apply {
            setTo(email)
            subject = "Autofarmer 이메일 인증"
            text = """
                 안녕하세요, $email 님!\n\n" +
                    "아래 링크를 클릭하여 이메일 인증을 완료해주세요.\n" +
                    "$link\n\n" +
                    "감사합니다."
                """.trimIndent()
        }
        mailSender.send(message)
        return token
    }

    //이메일 인증토큰 검증
    fun verifyEmail(token: String): Boolean {
        //Redis에서 인증토큰 조회
        val email = redisTemplate.opsForValue().get("verification_token:${token}")
        //이메일 주소로 회원 정보 조회
        val user = userRepository.findByEmail(email) ?: throw IllegalArgumentException("존재하지 않는 회원입니다.")
        //인증토큰 검증
        return if (email == user.email) {
            println("인증토큰 검증 성공")
            true
        } else {
            println("인증토큰 검증 실패")
            false
        }
    }
}
