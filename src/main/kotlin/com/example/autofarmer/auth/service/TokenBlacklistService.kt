package com.example.autofarmer.auth.service

import com.example.autofarmer.jwt.JwtUtil
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class TokenBlacklistService(
    private val redisTemplate: StringRedisTemplate,
    private val jwtUtil: JwtUtil
) {
    companion object {
        private const val BLACKLIST_PREFIX = "blacklist:"
        private const val BLACKLIST_EXPIRATION_TIME = 60 * 60 * 24
    }

    fun invalidateToken(token: String) {
        val pureToken = extractPureToken(token)
        val expiration = calculateRemainingExpiration(pureToken)

        if (expiration > 0) {
            redisTemplate.opsForValue().set(
                "$BLACKLIST_PREFIX$pureToken",
                "blacklisted",
                Duration.ofMillis(expiration)
            )
        }
    }

    fun isTokenBlacklisted(token: String): Boolean {
        val pureToken = extractPureToken(token)
        return redisTemplate.hasKey("$BLACKLIST_PREFIX$pureToken") ?: false
    }

    private fun extractPureToken(token: String): String {
        return token.replace("Bearer ", "")
    }

    private fun calculateRemainingExpiration(token: String): Long {
        return try {
            val claims = jwtUtil.parseClaims(token)
            claims.expiration.time - System.currentTimeMillis()
        } catch (e: Exception) {
            throw IllegalArgumentException("이미 만료된 토큰입니다.")
        }
    }
}
