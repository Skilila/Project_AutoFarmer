package com.example.autofarmer.alert.service

import com.example.autofarmer.alert.domain.Alert
import com.example.autofarmer.alert.repository.AlertRepository
import com.example.autofarmer.user.domain.User
import org.springframework.stereotype.Service

// 알림 서비스 클래스
@Service
class AlertService(
    private val alertRepository: AlertRepository
) {
    object AlertType {
        const val ENVIRONMENT = "ENVIRONMENT" // 환경 알림
        const val SYSTEM = "SYSTEM" // 시스템 장애 알림
        const val TASK = "TASK" // 작업/일정 알림
        const val OTHER = "OTHER" // 기타 알림
    }

    //환경 알림 생성
    fun createEnvironmentAlert(user: User, message: String): Alert {
        return Alert(
            user = user,
            type = AlertType.ENVIRONMENT,
            message = message,
        )
    }

    //시스템 장애 알림 생성
    fun createSystemAlert(user: User, message: String): Alert {
        return Alert(
            user = user,
            type = AlertType.SYSTEM,
            message = message,
        )
    }

    //작업/일정 알림 생성
    fun createTaskAlert(user: User, message: String): Alert {
        return Alert(
            user = user,
            type = AlertType.TASK,
            message = message,
        )
    }

    //기타 알림 생성
    fun createOtherAlert(user: User, message: String): Alert {
        return Alert(
            user = user,
            type = AlertType.OTHER,
            message = message,
        )
    }

    //단일 알림 조회(알림 ID로 조회)
    fun getAlertById(alertId: Long): Alert? {
        // 알림 ID로 알림을 조회
        return alertRepository.findById(alertId).orElse(null)
    }

    //알림 조회(사용자별)(최근 알림 순으로 정렬)
    fun getAlertsByUserSortedByRecent(user: User): List<Alert> {
        // 사용자별로 알림을 생성 시간 기준으로 내림차순 정렬하여 조회
        return alertRepository.findAllByUserOrderByCreatedAtDesc(user)
    }

    //안 읽은 알림만 조회(최근 알림 순으로 정렬)
    fun getUnreadAlertsSortedByRecent(user: User): List<Alert> {
        // 사용자별로 안 읽은 알림을 생성 시간 기준으로 내림차순 정렬하여 조회
        return alertRepository.findAllByUserOrderByCreatedAtDesc(user).filter { !it.isRead }
    }

    //알림 읽음 처리
    fun markAlertAsRead(alertId: Long) {
        // 알림 ID로 알림을 조회하여 읽음 처리
        val alert = alertRepository.findById(alertId).orElseThrow { NoSuchElementException("알림 없음 alertId=$alertId") }
        alert.isRead = true
        // 읽음 처리된 알림을 저장
        alertRepository.save(alert)
    }

    //알림 삭제
    fun deleteAlert(alertId: Long) {
        // 알림 ID로 알림이 존재하는지 확인 후 삭제
        if (!alertRepository.existsById(alertId)) {
            throw NoSuchElementException("알림 없음 alertId=$alertId")
        }
        alertRepository.deleteById(alertId)
    }
}
