package com.example.autofarmer.alert.controller

import com.example.autofarmer.alert.domain.Alert
import com.example.autofarmer.alert.service.AlertService
import com.example.autofarmer.user.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/alerts")
class AlertController(
    private val alertService: AlertService
) {
    //알림 생성
    @PostMapping("/create")
    fun createAlert(@RequestBody user: User): ResponseEntity<Alert> {
        val alert = alertService.createAlert(user)
        return ResponseEntity.ok().body(alert)
    }

    //알림 조회(사용자별)(최근 알림 순으로 정렬)
    @GetMapping("/{userId}")
    fun getAlertsByUser(@PathVariable userId: Long): ResponseEntity<List<Alert>> {
        val user = User(userId = userId)
        val alerts = alertService.getAlertsByUserSortedByRecent(user)
        return ResponseEntity.ok().body(alerts)
    }

    //안 읽은 알림만 조회(최근 알림 순으로 정렬)
    @GetMapping("/{userId}/unread")
    fun getUnreadAlerts(@PathVariable userId: Long): ResponseEntity<List<Alert>> {
        val user = User(userId = userId)
        val unreadAlerts = alertService.getUnreadAlertsSortedByRecent(user)
        return ResponseEntity.ok().body(unreadAlerts)
    }

    //알림 읽음 처리
    @PatchMapping("/{alertId}/read")
    fun markAlertAsRead(@PathVariable alertId: Long): ResponseEntity<Unit> {
        alertService.markAlertAsRead(alertId)
        return ResponseEntity.noContent().build()
    }

    //알림 삭제
    @DeleteMapping("/{alertId}")
    fun deleteAlert(@PathVariable alertId: Long): ResponseEntity<Unit> {
        alertService.deleteAlert(alertId)
        return ResponseEntity.noContent().build()
    }
}
