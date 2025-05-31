package com.example.autofarmer.alert.controller

import com.example.autofarmer.alert.domain.Alert
import com.example.autofarmer.alert.service.AlertService
import com.example.autofarmer.user.domain.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/alerts")
class AlertController(
    private val alertService: AlertService
) {
    //알림 생성
    @PostMapping("/create")
    fun createAlert(@RequestBody user: User): Alert {
        return alertService.createAlert(user)
    }

    //알림 조회(사용자별)(최근 알림 순으로 정렬)
    @GetMapping("/user/{userId}")
    fun getAlertsByUser(@PathVariable userId: Long): List<Alert> {
        val user = User(userId = userId)
        return alertService.getAlertsByUserSortedByRecent(user)
    }

    //안 읽은 알림만 조회(최근 알림 순으로 정렬)
    @GetMapping("/unread/{userId}")
    fun getUnreadAlerts(@PathVariable userId: Long): List<Alert> {
        val user = User(userId = userId)
        return alertService.getUnreadAlertsSortedByRecent(user)
    }

    //알림 읽음 처리
    @PostMapping("/read/{alertId}")
    fun markAlertAsRead(@PathVariable alertId: Long) {
        alertService.markAlertAsRead(alertId)
    }

}
