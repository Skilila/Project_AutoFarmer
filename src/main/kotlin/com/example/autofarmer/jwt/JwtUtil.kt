package com.example.autofarmer.jwt

import com.example.autofarmer.auth.service.TokenBlacklistService
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.secret}")
    private val jwtSecretKey: String,
    @Value("\${jwt.expiration}")
    private val expiration: Long,
    private val tokenBlacklistService: TokenBlacklistService

) {
    private val signingKey = Keys.hmacShaKeyFor(jwtSecretKey.toByteArray())

    fun generateToken(userNo: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)
        return Jwts.builder()
            .header()
            .add("kid", jwtSecretKey)
            .add("typ", "JWT")
            .add("alg", "ES256")
            .and()
            .subject(userNo.toString())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(signingKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            throw JwtException("무효화된 토큰입니다.")
        }
        return try {
            Jwts.parser()
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: JwtException) {
            false
        }
    }

    fun parseClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun extractuserNo(token: String): Long {
        val claims = Jwts
            .parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.subject.toLong()
    }

    private fun handleJwtException(e: Throwable) {
        when (e) {
            is ExpiredJwtException -> throw JwtValidationException("토큰 만료", 401)
            is UnsupportedJwtException -> throw JwtValidationException("지원하지 않는 형식", 400)
            is MalformedJwtException -> throw JwtValidationException("잘못된 구조", 400)
            is SignatureException -> throw JwtValidationException("서명 불일치", 403)
            else -> throw JwtValidationException("기타 오류: ${e.message}", 500)
        }
    }

    class JwtValidationException(
        override val message: String,
        val errorCode: Int
    ) : RuntimeException(message)
}
