package com.example.autofarmer.alert.repository

import com.example.autofarmer.alert.domain.Alert
import com.example.autofarmer.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

// 알림 저장소 인터페이스
interface AlertRepository : JpaRepository<Alert, Long> {
    // 사용자별로 알림을 생성 시간 기준으로 내림차순 정렬하여 조회
    fun findAllByUserOrderByCreatedAtDesc(user: User): List<Alert>
}
