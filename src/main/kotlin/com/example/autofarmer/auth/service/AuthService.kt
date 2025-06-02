package com.example.autofarmer.auth.service

import com.example.autofarmer.TokenAndCodeGenerator
import com.example.autofarmer.auth.dto.AuthRequest
import com.example.autofarmer.auth.dto.AuthResponse
import com.example.autofarmer.security.jwt.JwtService
import com.example.autofarmer.user.domain.User
import com.example.autofarmer.user.dto.UserDTO
import com.example.autofarmer.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val redisTemplate: StringRedisTemplate,
    private val mailSender: JavaMailSender,
    @Value("\${jwt.access-expiration}")
    private val accessTokenExpiration: Long,
    @Value("\${jwt.refresh-expiration}")
    private val refreshTokenExpiration: Long,
) {
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    //회원가입
    fun signup(request: AuthRequest.Signup): UserDTO {
        //가입된 이메일인지 확인
        val isUser = userRepository.existsByEmail(request.email)
        if (isUser) {
            throw BadCredentialsException("이미 가입된 이메일입니다.")
        } else {
            //비밀번호 암호화
            val encodedpassword = passwordEncoder.encode(request.password)
            //신규 회원 생성
            val user = User(
                email = request.email,
                nickname = request.nickname,
                password = encodedpassword,
            )
            user.createdAt = LocalDateTime.now()
            //신규 회원 저장 후 반환
            userRepository.save(user)
            return user.toDTO()
        }
    }

    //로그인
    fun login(request: AuthRequest.Login): AuthResponse.Login {
        //가입된 이메일인지 확인
        val user = userRepository.findByEmail(request.email)
        if (user == null) {
            throw BadCredentialsException("가입되지 않은 이메일입니다.")
        } else {
            //이메일 및 비밀번호 확인
            if (user.email != request.email) {
                throw BadCredentialsException("이메일이 일치하지 않습니다.")
            }
            if (!passwordEncoder.matches(request.password, user.password)) {
                throw BadCredentialsException("비밀번호가 일치하지 않습니다.")
            }
            //로그인 시간 수정
            user.lastLogin = LocalDateTime.now()
            //토큰 생성
            val accessToken = jwtService.generateToken(
                user.userId.toString(),
            )
            val refreshToken = jwtService.generateToken(
                user.userId.toString(),
            )
            //리프레시 토큰을 Redis에 저장
            redisTemplate.opsForValue().set(
                "refresh_token:${user.userId}",
                refreshToken,
                Duration.ofMillis(refreshTokenExpiration)
            )
            return AuthResponse.Login(
                true,
                user.userId,
                accessToken,
                refreshToken,
            )
        }
    }

    //비밀번호 재설정 이메일 발송
    fun sendPasswordResetEmail(email: String) {
        //비밀번호 재설정 토큰 생성
        val token = TokenAndCodeGenerator.generateSecureToken()
        //토큰을 Redis에 저장
        redisTemplate.opsForValue().set(
            "reset_password_token:${token}",
            email,
            Duration.ofMinutes(10)
        )
        //비밀번호 재설정링크 생성
        val resetUrl = "https://localhost:8080/api/auth/reset-password/?token=$token"
        //비밀번호 재설정 이메일 발송
        val message = SimpleMailMessage().apply {
            setTo(email)
            subject = "Autofarmer 비밀번호 재설정"
            text = """
                안녕하세요!
                비밀번호 재설정을 위해 아래 링크를 클릭해주세요:
                $resetUrl
                감사합니다.
            """.trimIndent()
        }
        mailSender.send(message)
    }

    //비밀번호 재설정
    fun resetPasswordWithToken(token: String, newPassword: String): Boolean {
        //Redis에서 리셋 토큰 조회
        val email = redisTemplate.opsForValue().get("reset_password_token:${token}")
        //이메일 주소로 회원 정보 조회
        val user = userRepository.findByEmail(email) ?: throw IllegalArgumentException("존재하지 않는 회원입니다.")
        return if (email == user.email) {
            //비밀번호 암호화
            user.password = passwordEncoder.encode(newPassword)
            //비밀번호 업데이트
            userRepository.save(user)
            //redis에서 토큰 삭제
            redisTemplate.delete("reset_password_token")
            true
        } else {
            false
        }

    }

    //로그아웃
    fun logout(accessToken: String, refreshToken: String) {
        // 액세스 토큰 유효성 검사
        val pureAccessToken = accessToken.replace("Bearer ", "")
        if (!jwtService.validateToken(pureAccessToken)) {
            throw IllegalArgumentException("유효하지 않은 토큰입니다.")
        }
        // 액세스 토큰에서 회원 정보 추출
        val userId = jwtService.extractClaims(pureAccessToken).subject.toLongOrNull()
            ?: throw IllegalArgumentException("회원 정보 없음")
        // Redis에서 리프레시 토큰 조회
        val storedRefreshToken = redisTemplate.opsForValue().get("refresh_token:$userId")
        // 토큰 일치 여부 확인
        if (storedRefreshToken != null && storedRefreshToken == refreshToken) {
            // Redis에서 리프레시 토큰 삭제
            redisTemplate.delete("refresh_token:$userId")
            // 액세스 토큰 블랙리스트 등록
            val remainingTime = accessTokenExpiration - System.currentTimeMillis()
            if (remainingTime > 0) {
                redisTemplate.opsForValue().set(
                    "BLACKLIST:$accessToken",
                    "withdrawn",
                    Duration.ofMillis(remainingTime)
                )
            }
        }
    }

    //회원탈퇴
    fun withdraw(accessToken: String) {
        // 액세스 토큰 유효성 검사
        val pureAccessToken = accessToken.replace("Bearer ", "")
        if (!jwtService.validateToken(pureAccessToken)) {
            throw IllegalArgumentException("유효하지 않은 토큰입니다.")
        }
        // 액세스 토큰에서 회원 정보 추출
        val userId = jwtService.extractClaims(pureAccessToken).subject.toLongOrNull()
            ?: throw IllegalArgumentException("회원 정보 없음")
        // 2. Redis에서 리프레시 토큰 조회
        redisTemplate.opsForValue().get("refresh_token:$userId")
        // 5. Redis에서 리프레시 토큰 삭제
        redisTemplate.delete("refresh_token:$userId")
        // 6. 액세스 토큰 블랙리스트 등록
        val remainingTime = accessTokenExpiration - System.currentTimeMillis()
        if (remainingTime > 0) {
            redisTemplate.opsForValue().set(
                "BLACKLIST:$accessToken",
                "withdrawn",
                Duration.ofMillis(remainingTime)
            )
        }
        // 4. 회원 정보 영구 삭제
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 회원입니다.")
        }
        userRepository.delete(user)
    }
}
