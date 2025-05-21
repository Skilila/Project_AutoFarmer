package com.example.autofarmer.user.domain

import com.example.autofarmer.user.dto.UserDTO
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["email", "nickname", "password"]),
    ]
)

class User(
    @field:NotBlank(message = "이메일은 필수입니다.")
    @Column(nullable = false)
    var email: String,// 이메일 주소
    @field:NotBlank(message = "닉네임은 필수입니다.")
    @Column(nullable = false)
    var nickname: String, // 닉네임
    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @Column(nullable = false)
    var password: String, // 비밀번호
    @Column(nullable = false)
    var status: String = "INACTIVE" // 상태 값 (예: ACTIVE, INACTIVE)
) {
    var lastLogin: LocalDateTime? = null // 마지막 로그인 시간

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now() // 계정 생성 일시

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null // 사용자 정보 수정 일시

    @Column(nullable = false)
    var notificationType: String = "NONE" // 알림 수신 방법 (예: NONE, EMAIL, SMS)

    @Column(nullable = false)
    var role: String = "USER" // 권한 (예: USER, ADMIN)

    @Id
    @Column(nullable = false)
    var userNo: Long = TsidCreator.getTsid().toLong() // 사용자 고유 번호

    fun toDTO(): UserDTO {
        return UserDTO(
            email = this.email,
            nickname = this.nickname,
            role = this.role,
            userNo = this.userNo,
            status = this.status
        )
    }
}
