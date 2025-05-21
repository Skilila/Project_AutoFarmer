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
import org.springframework.web.servlet.mvc.support.RedirectAttributes

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
        @Valid @RequestBody request: AuthRequest.Signup,
        redirectAttributes: RedirectAttributes
    ): ResponseEntity<UserDTO> {
        //authService를 통해 회원가입 처리
        return try {
            authService.signup(request)
            ResponseEntity.status(HttpStatus.CREATED).body(UserDTO(request.email, request.nickname, "ACTIVE"))
        } catch (_: BadCredentialsException) {
            redirectAttributes.addFlashAttribute("error", "이미 가입된 이메일입니다.")
            ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/login")
                .body(UserDTO(request.email, request.nickname, "ACTIVE"))
        }
    }

    //이메일 인증번호 발송
    @PostMapping("/send-email")
    fun sendEmail(@RequestBody request: AuthRequest.SendEmail): ResponseEntity<Map<String, String>> {
        val token = emailService.sendEmail(request.email)
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                mapOf(
                    "message" to "이메일 인증번호가 발송되었습니다.",
                    "token" to token
                )
            )
    }

    //이메일 인증 확인
    @GetMapping("/verify-email")
    fun verifyEmail(
        @RequestParam("token") token: String,
    ): ResponseEntity<String> {
        //이메일 인증 토큰 검증
        val isVerified = emailService.verifyEmail(token)
        return if (isVerified) {
            ResponseEntity.ok("이메일 인증이 완료되었습니다.")
            ResponseEntity.status(HttpStatus.FOUND).header("Location", "/login").build()
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증에 실패했습니다.")
        }
    }

    //휴대폰 인증번호 발송
    @PostMapping("/send-sms")
    fun sendSms(@RequestBody phone: String) = smsService.sendSms(phone)

    //휴대폰 인증 확인
    @PostMapping("/verify-sms")
    fun verifySms(@RequestBody code: String): ResponseEntity<String> {
        val isVerified = smsService.verifySms(code)
        return if (isVerified) {
            ResponseEntity.ok("휴대폰 인증이 완료되었습니다.")
            ResponseEntity.status(HttpStatus.FOUND).header("Location", "/login").build()
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
            return ResponseEntity.status(HttpStatus.OK).body(
                AuthResponse.Login(
                    true,
                    loginResponse.userNo,
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
            //비밀번호 재설정 요청
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
    ): ResponseEntity<out Map<String, Any?>> {
        return try {
            authService.logout(accessToken, refreshToken)
            ResponseEntity.ok(mapOf("success" to true, "message" to "로그아웃이 완료되었습니다."))
        } catch (e: JwtException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("success" to false, "error" to e.message))
        }
    }

    //회원탈퇴
    @DeleteMapping("/withdraw")
    fun withdraw(
        @RequestHeader("Authorization") accessToken: String
    ): ResponseEntity<String> {
        authService.withdraw(accessToken)
        return ResponseEntity.ok("회원탈퇴가 완료되었습니다.")
    }
}
