package com.example.autofarmer.auth.service

import com.example.autofarmer.User.domain.User
import com.example.autofarmer.User.repository.UserRepository
import com.example.autofarmer.auth.domain.Withdrawer
import com.example.autofarmer.auth.dto.LoginRequest
import com.example.autofarmer.auth.dto.SignupRequest
import com.example.autofarmer.auth.dto.TokenResponse
import com.example.autofarmer.auth.repository.WithdrawRepository
import com.example.autofarmer.jwt.JwtUtil
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val blacklistService: TokenBlacklistService,
    private val withdrawerRepository: WithdrawRepository
) {
    fun signUp(request: SignupRequest) {
        if (userRepository.existsByEmail(request.email)) throw IllegalArgumentException("이미 가입된 이메일입니다.")
        val user = User(
            request.email,
            request.nickname,
        )
        userRepository.save(user)
    }

    fun login(request: LoginRequest): TokenResponse {
        val user = userRepository.findByEmail(request.email) ?: throw NoSuchElementException("가입되지 않은 이메일입니다.")
        val token = jwtUtil.generateToken(user.userNo)
        return TokenResponse(token)
    }

    fun logout(accessToken: String) {
        blacklistService.invalidateToken(accessToken)
    }

    fun withdraw(userNo: Long, reason: String, withDrawnat: LocalDateTime) {
        val user = userRepository.findByUserNo(userNo) ?: throw NoSuchElementException("사용자를 찾을 수 없습니다")
        val withdrawer = Withdrawer(
            user.userNo,
            user.email,
            reason, 
            withDrawnat
            )
        withdrawerRepository.save(withdrawer)
        userRepository.deleteByUserNo(userNo)
    }

    fun validateCredentials(request: LoginRequest): Boolean =
        userRepository.findByEmail(request.email) != null
}
