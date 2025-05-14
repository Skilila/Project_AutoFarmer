package com.example.autofarmer.User.domain

import com.example.autofarmer.User.dto.UserDTO
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class User(
    var email: String? = "",//이메일
    var nickname: String = "",//닉네임
    var lastLogin: LocalDateTime? = null,//마지막 로그인 시간
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now(),// 생성 시간
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),//수정 시간
    var preferredNotification: String? = null,//알림 방식
    var status: String = "inactive",//계정 상태
    var role: String = "user",//권한
    @Id
    var userNo: Long = 0L//유저 식별 번호
) {
    @PrePersist
    fun prePersist() {
        userNo = TsidCreator.getTsid().toLong()
    }

    fun changeStatus(newStatus: String) {
        this.status = newStatus
    }

    fun toDTO(user: User): UserDTO = UserDTO(
        user.email,
        user.nickname
    )

    fun updateEmail(newEmail: String) {
        this.email = newEmail
    }

    fun updateNickname(newNickname: String) {
        this.nickname = newNickname
    }
}
