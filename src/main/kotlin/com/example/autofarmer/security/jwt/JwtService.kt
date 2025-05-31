package com.example.autofarmer.security.jwt

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.security.*
import java.security.SignatureException
import java.util.*

@Component
class JwtService(
    private val redisTemplate: StringRedisTemplate,
    @Value("\${jwt.access-expiration}")
    private val accessTokenExpiration: Long,
) {
    val keyGen: KeyPairGenerator = KeyPairGenerator.getInstance("RSA").apply {
        initialize(3072)
    }
    val keyPair: KeyPair = keyGen.generateKeyPair()
    val privateKey: PrivateKey = keyPair.private
    val publicKey: PublicKey = keyPair.public

    //토큰 생성 메서드
    fun generateToken(
        subject: String,
    ): String {
        val now = Date()
        val expiration = Date(now.time + accessTokenExpiration)
        return Jwts.builder()
            .subject(subject)//주체
            .issuer("autofarmer")//발급자
            .issuedAt(now)//발급일
            .expiration(expiration)//만료일
            .signWith(privateKey, Jwts.SIG.RS384)//서명 알고리즘
            .compact()
    }

    //토큰 검증 메서드
    fun validateToken(token: String): Boolean {
        //로그아웃된 토큰인지 확인
        val pureToken = token.replace("Bearer ", "")
        redisTemplate.hasKey("blacklist:$pureToken") ?: {
            throw JwtException("로그아웃 처리된 토큰입니다")
        }

        return try {
            Jwts.parser()
                .verifyWith(publicKey)//공개키로 서명 검증
                .build()
                .parseSignedClaims(token)//서명된 클레임을 파싱하고 검증
            true
        } catch (e: Exception) {//예외 처리
            when (e) {
                is ExpiredJwtException -> throw JwtException("토큰이 만료되었습니다")
                is UnsupportedJwtException -> throw JwtException("지원하지 않는 토큰 형식입니다")
                is MalformedJwtException -> throw JwtException("잘못된 토큰 구조입니다", e)
                is SignatureException -> throw JwtException("서명 검증에 실패했습니다")
                is IllegalArgumentException -> throw JwtException("잘못된 토큰입니다")
                else -> {
                    false
                    throw JwtException("토큰 검증 실패: ${e.message}")
                }
            }
        }
    }

    //인증정보 추출
    fun extractClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
