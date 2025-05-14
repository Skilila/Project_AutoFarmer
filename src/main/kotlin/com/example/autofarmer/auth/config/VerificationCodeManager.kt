package com.example.autofarmer.auth.config

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Component
class VerificationCodeManager {
    private val codeStorage = ConcurrentHashMap<String, CodeInfo>()

    data class CodeInfo(val code: String, val expiry: Instant)

    fun generateCode(email: String): String {
        val code = (100000..999999).random().toString()
        codeStorage[email] = CodeInfo(code, Instant.now().plusSeconds(180))
        return code
    }

    fun verifyCode(email: String, code: String): Boolean {
        val stored = codeStorage[email] ?: return false
        return stored.code == code && Instant.now().isBefore(stored.expiry)
    }

    @Scheduled(fixedRate = 60000)
    fun cleanupExpiredCodes() {
        val now = Instant.now()
        codeStorage.entries.removeIf { entry -> entry.value.expiry.isBefore(now) }
    }
}
