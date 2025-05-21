package com.example.autofarmer.user.repository

import com.example.autofarmer.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String?): User? //이메일로 유저 찾기
    fun findByUserNo(userNo: Long): User? //유저 번호로 유저 찾기
    fun existsByEmail(email: String): Boolean //이메일로 유저 존재 여부 확인
}
