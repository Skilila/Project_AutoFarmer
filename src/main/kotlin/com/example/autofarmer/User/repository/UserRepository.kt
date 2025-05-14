package com.example.autofarmer.User.repository

import com.example.autofarmer.User.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun createUser(): User //유저 생성
    fun findByEmail(email: String?): User? //이메일로 유저 찾기
    fun findByUserNo(userNo: Long): User? //유저 번호로 유저 찾기
    fun existsByEmail(email: String?): Boolean //이메일로 유저 존재 여부 확인
    fun deleteByUserNo(userNo: Long): Int //유저 번호로 유저 삭제
}
