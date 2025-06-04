package com.example.autofarmer.auth.dto

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.mail")
data class EmailProperties(
    val host: String, // 이메일 서버 호스트
    val port: Int, // 이메일 서버 포트
    val username: String, // 이메일 계정 사용자 이름
    val password: String, // 이메일 계정 비밀번호
    val protocol: String, // 이메일 프로토콜 (예: smtp)
    val debug: Boolean // 디버그 모드 활성화 여부
)
