package com.example.autofarmer.alert.controller

import com.example.autofarmer.alert.domain.Alert
import com.example.autofarmer.alert.service.AlertService
import com.example.autofarmer.user.domain.User
import com.example.autofarmer.user.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

// 알림 관련 API 컨트롤러
@RestController
@RequestMapping("/api/alerts")
class AlertController(
    private val alertService: AlertService,
    private val userRepository: UserRepository
) {
    // 알림 생성 요청 DTO
    data class AlertRequest(
        val userId: Long, // 사용자 ID
        val type: String, // 알림 유형 (예: ENVIRONMENT, SYSTEM, TASK, OTHER)
        val message: String // 알림 메시지 내용
    )

    // 알림 생성
    @PostMapping("/create")
    fun createAlert(@RequestBody request: AlertRequest): ResponseEntity<Alert> {
        // userId로 User 엔티티 조회
        val user = userRepository.findById(request.userId)
            .orElseThrow { IllegalArgumentException("User not found") }
        // 알림 유형에 따라 알림 생성
        val alert = when (request.type) {
            "ENVIRONMENT" -> alertService.createEnvironmentAlert(user, request.message)
            "SYSTEM" -> alertService.createSystemAlert(user, request.message)
            "TASK" -> alertService.createTaskAlert(user, request.message)
            "OTHER" -> alertService.createOtherAlert(user, request.message)
            else -> throw IllegalArgumentException("Invalid alert type")
        }
        // 알림 생성 시간 업데이트
        alert.createdAt = LocalDateTime.now()
        // 생성된 알림을 응답으로 반환
        return ResponseEntity.ok(alert)
    }

    // 단일 알림 조회(알림 ID로 조회)
    @GetMapping("/{alertId}")
    fun getAlertById(@PathVariable alertId: Long): ResponseEntity<Alert> {
        // 알림 ID로 알림 조회
        val alert = alertService.getAlertById(alertId) ?: return ResponseEntity.notFound().build() // 알림이 없으면 404 응답
        // 조회된 알림을 응답으로 반환
        return ResponseEntity.ok(alert)
    }

    //알림 조회(사용자별)(최근 알림 순으로 정렬)
    @GetMapping("/{userId}")
    fun getAlertsByUser(@PathVariable userId: Long): ResponseEntity<List<Alert>> {
        // 사용자 ID로 User 객체 생성
        val user = User(userId = userId)
        // 해당 사용자의 알림을 최근 알림 순으로 조회
        val alerts = alertService.getAlertsByUserSortedByRecent(user)
        // 조회된 알림 목록을 응답으로 반환
        return ResponseEntity.ok().body(alerts)
    }

    //안 읽은 알림만 조회(최근 알림 순으로 정렬)
    @GetMapping("/{userId}/unread")
    fun getUnreadAlerts(@PathVariable userId: Long): ResponseEntity<List<Alert>> {
        // 사용자 ID로 User 객체 생성
        val user = User(userId = userId)
        // 안 읽은 알림을 최근 알림 순으로 조회
        val unreadAlerts = alertService.getUnreadAlertsSortedByRecent(user)
        // 조회된 안 읽은 알림 목록을 응답으로 반환
        return ResponseEntity.ok().body(unreadAlerts)
    }

    //알림 읽음 처리
    @PatchMapping("/{alertId}/update")
    fun markAlertAsRead(@PathVariable alertId: Long): ResponseEntity<Unit> {
        // 알림 ID로 알림을 읽음 처리
        alertService.markAlertAsRead(alertId)
        // 처리 완료 응답 반환
        return ResponseEntity.noContent().build()
    }

    //알림 삭제
    @DeleteMapping("/{alertId}/delete")
    fun deleteAlert(@PathVariable alertId: Long): ResponseEntity<Unit> {
        // 알림 ID로 알림 삭제
        alertService.deleteAlert(alertId)
        // 삭제 완료 응답 반환
        return ResponseEntity.noContent().build()
    }
}
