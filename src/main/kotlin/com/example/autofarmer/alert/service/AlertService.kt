package com.example.autofarmer.alert.service

import com.example.autofarmer.alert.domain.Alert
import com.example.autofarmer.alert.repository.AlertRepository
import com.example.autofarmer.user.domain.User
import org.springframework.stereotype.Service

@Service
class AlertService(
    private val alertRepository: AlertRepository
) {
    //알림 생성
    fun createAlert(user: User): Alert {
        val alert = Alert(user = user)
        return alertRepository.save(alert)
    }

    //알림 조회(사용자별)(최근 알림 순으로 정렬)
    fun getAlertsByUserSortedByRecent(user: User): List<Alert> {
        return alertRepository.findAllByUserOrderByCreatedAtDesc(user)
    }

    //안 읽은 알림만 조회(최근 알림 순으로 정렬)
    fun getUnreadAlertsSortedByRecent(user: User): List<Alert> {
        return alertRepository.findAllByUserOrderByCreatedAtDesc(user).filter { !it.isRead }
    }

    //알림 읽음 처리
    fun markAlertAsRead(alertId: Long) {
        val alert = alertRepository.findById(alertId).orElseThrow { NoSuchElementException("알림 없음 alertId=$alertId") }
        alert.isRead = true
        alertRepository.save(alert)
    }

    //알림 삭제
    fun deleteAlert(alertId: Long) {
        if (!alertRepository.existsById(alertId)) {
            throw NoSuchElementException("알림 없음 alertId=$alertId")
        }
        alertRepository.deleteById(alertId)
    }
}
