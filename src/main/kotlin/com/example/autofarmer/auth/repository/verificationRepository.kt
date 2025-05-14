package com.example.autofarmer.auth.repository

import com.example.autofarmer.auth.domain.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface verificationRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): Optional<VerificationToken>
}
