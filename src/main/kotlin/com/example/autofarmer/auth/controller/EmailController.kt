package com.example.autofarmer.auth.controller

import com.example.autofarmer.auth.config.VerificationCodeManager
import com.example.autofarmer.auth.service.EmailService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/email")
class EmailController(
    private val emailService: EmailService,
    private val codeManager: VerificationCodeManager
) {
    @PostMapping("/send")
    fun sendEmail(
        @Valid
        @RequestParam email: String,
    ): ResponseEntity<String> {
        emailService.sendVerificationEmail(email)
        return ResponseEntity.ok("인증코드가 발송되었습니다.")
    }

    @PostMapping("/verify")
    fun verify(
        @Valid
        @RequestParam email: String,
        @RequestParam code: String,
    ): ResponseEntity<String> {
        val isValid = codeManager.verifyCode(email, code)
        return if (isValid) {
            ResponseEntity.ok("이메일 인증에 성공하였습니다.")
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증코드가 일치하지 않습니다.")
        }
    }
}
