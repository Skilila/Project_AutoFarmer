package com.example.autofarmer.auth.controller

import com.example.autofarmer.auth.dto.AuthRequest
import com.example.autofarmer.auth.dto.AuthResponse
import com.example.autofarmer.auth.service.AuthService
import com.example.autofarmer.auth.service.EmailService
import com.example.autofarmer.auth.service.SmsService
import com.example.autofarmer.user.dto.UserDTO
import io.jsonwebtoken.JwtException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val emailService: EmailService,
    private val smsService: SmsService,
) {
    //회원가입
    @PostMapping("/signup")
    fun signup(
        @Valid
        @RequestBody request: AuthRequest.Signup
    ): ResponseEntity<UserDTO> {
        return try {
            //authService를 통해 회원가입 처리
            authService.signup(request)
            ResponseEntity.status(HttpStatus.CREATED).body(UserDTO(nickname = request.nickname, email = request.email))
        } catch (_: BadCredentialsException) {
            //이미 가입된 이메일인 경우
            throw BadCredentialsException("이미 가입된 이메일입니다.")
        }
    }

    //이메일 인증번호 발송
    @PostMapping("/send-email")
    fun sendEmail(@RequestBody request: AuthRequest.SendEmail): ResponseEntity<String> {

        return try {
            //emailService를 통해 이메일 인증번호 발송
            val token = emailService.sendEmail(request.email)
            ResponseEntity.ok("이메일 인증번호가 발송되었습니다.token: $token")
        } catch (_: Exception) {
            //이메일 발송 실패
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 인증번호 발송에 실패했습니다.")
        }
    }

    //이메일 인증 확인
    @GetMapping("/verify-email")
    fun verifyEmail(
        @RequestParam("token") token: String,
    ): ResponseEntity<String> {
        //emailService를 통해 이메일 인증 확인
        val isVerified = emailService.verifyEmail(token)
        return if (isVerified) {
            ResponseEntity.ok("이메일 인증이 완료되었습니다.")
        } else {
            //이메일 인증 실패
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증에 실패했습니다.")
        }
    }

    //휴대폰 인증번호 발송
    @PostMapping("/send-sms")
    fun sendSms(@RequestBody phone: String): ResponseEntity<String> {

        return try {
            //smsService를 통해 휴대폰 인증번호 발송
            smsService.sendSms(phone)
            ResponseEntity.ok("휴대폰 인증번호가 발송되었습니다.")
        } catch (_: Exception) {
            //휴대폰 인증번호 발송 실패
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("휴대폰 인증번호 발송에 실패했습니다.")
        }
    }

    //휴대폰 인증 확인
    @PostMapping("/verify-sms")
    fun verifySms(@RequestBody code: String): ResponseEntity<String> {
        //smsService를 통해 휴대폰 인증 확인
        val isVerified = smsService.verifySms(code)
        return if (isVerified) {
            ResponseEntity.ok("휴대폰 인증이 완료되었습니다.")
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("휴대폰 인증에 실패했습니다.")
        }
    }

    //로그인
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: AuthRequest.Login
    ): ResponseEntity<AuthResponse.Login> {
        val loginResponse = authService.login(request)
        if (loginResponse.isSuccess) {
            println("로그인 성공")
            return ResponseEntity.ok(
                AuthResponse.Login(
                    true,
                    loginResponse.userId,
                    loginResponse.accessToken,
                    loginResponse.refreshToken
                )
            )
        } else {
            println("로그인 실패")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                AuthResponse.Login(
                    false,
                    null,
                    null,
                    null
                )
            )
        }
    }

    //비밀번호 재설정
    @PostMapping("/password-reset")
    fun passwordReset(
        @RequestParam(required = false) token: String?,
        @RequestBody(required = false) request: AuthRequest.ResetPassword?
    ): Any {
        return if (token.isNullOrBlank()) {
            // authService를 통해 비밀번호 재설정 이메일 발송
            authService.sendPasswordResetEmail(request?.email ?: "")
        } else {
            //비밀번호 재설정
            val isSuccess = authService.resetPasswordWithToken(token, request?.newPassword ?: "")
            if (!isSuccess) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않거나 만료된 토큰입니다.")
            }
            ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.")
        }
    }

    //로그아웃
    @PostMapping("/logout")
    fun logout(
        @RequestHeader("Authorization") accessToken: String,
        @RequestHeader("Refresh-Token") refreshToken: String
    ): ResponseEntity<String> {
        return try {
            // authService를 통해 로그아웃 처리
            authService.logout(accessToken, refreshToken)
            ResponseEntity.ok("로그아웃이 완료되었습니다.")
        } catch (_: JwtException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그아웃에 실패했습니다")
        }
    }

    //회원탈퇴
    @DeleteMapping("/withdraw")
    fun withdraw(
        @RequestHeader("Authorization") accessToken: String
    ): ResponseEntity<String> {
        return try {
            // authService를 통해 회원탈퇴 처리
            authService.withdraw(accessToken)
            ResponseEntity.ok("회원탈퇴가 완료되었습니다.")
        } catch (_: JwtException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원탈퇴에 실패했습니다.")
        }
    }
}
