package com.example.autofarmer.user.dto

import com.github.f4b6a3.tsid.TsidCreator

data class UserDTO(
    val userId: Long = TsidCreator.getTsid().toLong(), // TSID를 사용하여 고유한 ID 생성
    val nickname: String, // 닉네임은 필수 입력
    val email: String, // 이메일은 필수 입력
    val alertType: String = "NONE", // 기본값을 NONE으로 설정
    val role: String = "USER", // 기본값을 USER로 설정
    var status: String = "ACTIVE" // 기본값을 ACTIVE로 설정
)
