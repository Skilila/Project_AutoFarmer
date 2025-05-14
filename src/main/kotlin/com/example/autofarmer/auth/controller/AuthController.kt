package com.example.autofarmer.auth.controller

import com.example.autofarmer.auth.dto.LoginRequest
import com.example.autofarmer.auth.dto.SignupRequest
import com.example.autofarmer.auth.dto.WithdrawRequest
import com.example.autofarmer.auth.service.AuthService
import com.example.autofarmer.auth.service.TokenBlacklistService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val tokenBlacklistService: TokenBlacklistService
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignupRequest): ResponseEntity<String> {
        authService.signUp(request)
        return ResponseEntity.ok("회원가입에 성공하였습니다.")
    }


    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
        session: HttpSession,
    ): ResponseEntity<String> {
        val isValid = authService.validateCredentials(request)
        return if (isValid) {
            session.setAttribute("userEmail", request.email)
            ResponseEntity.ok("로그인에 성공하였습니다.")
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("가입되지 않은 이메일입니다.")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<Void> {
        tokenBlacklistService.invalidateToken(token)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/withdraw")
    fun withdraw(
        @RequestBody request: WithdrawRequest,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<String> {
        authService.withdraw(token, request)
        return ResponseEntity.ok("회원탈퇴에 성공하였습니다.")
    }
}
