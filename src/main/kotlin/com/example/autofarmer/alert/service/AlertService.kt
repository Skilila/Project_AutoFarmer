package com.example.autofarmer.alert.service

import com.example.autofarmer.alert.domain.Alert
import com.example.autofarmer.alert.repository.AlertRepository
import com.example.autofarmer.user.domain.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AlertService(
    private val alertRepository: AlertRepository
) {
    //알림 생성
    fun createAlert(user: User): Alert {
        val alert = Alert(
            user = user,
            createdAt = LocalDateTime.now()
        )
        return alertRepository.save(alert)
    }

    //알림 조회(사용자별)(최근 알림 순으로 정렬)
    fun getAlertsByUserSortedByRecent(user: User): List<Alert> {
        return alertRepository.findAllByUser(user).sortedByDescending { it.createdAt }
    }

    //안 읽은 알림만 조회(최근 알림 순으로 정렬)
    fun getUnreadAlertsSortedByRecent(user: User): List<Alert> {
        return alertRepository.findAllByUser(user).filter { !it.user.isRead }.sortedByDescending { it.createdAt }
    }

    //알림 읽음 처리
    fun markAlertAsRead(alertId: Long) {
        val alert = alertRepository.findById(alertId).orElseThrow { NoSuchElementException("알림 없음 alertId=$alertId") }
        val user = alert.user
        user.isRead = true
        alertRepository.save(alert)
    }
}
