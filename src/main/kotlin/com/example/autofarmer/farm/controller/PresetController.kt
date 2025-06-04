package com.example.autofarmer.farm.controller

import com.example.autofarmer.farm.domain.Crop
import com.example.autofarmer.farm.domain.Preset
import com.example.autofarmer.farm.service.PresetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// 프리셋 관련 API 컨트롤러
@RestController
@RequestMapping("/api/preset")
class PresetController(
    private val presetService: PresetService
) {
    //프리셋 등록
    @PostMapping("/{cropId}/add")
    fun addToPreset(
        @PathVariable cropId: Long,
        @RequestParam userId: Long,
        @RequestParam temperature: Double,
        @RequestParam humidity: Double
    ): ResponseEntity<String> {
        // 프리셋 등록 서비스 호출
        presetService.addToPreset(cropId, userId, temperature, humidity)
        // 프리셋 등록 완료 응답
        return ResponseEntity.ok("프리셋 등록 완료")
    }

    //프리셋 조회
    @GetMapping("/search")
    fun getPreset(
        @RequestParam userId: Long
    ): List<Crop> {
        // 사용자 ID로 프리셋 조회 서비스 호출
        return presetService.getPreset(userId)
    }

    //프리셋 해제
    @DeleteMapping("/{cropId}/remove")
    fun removePreset(
        @RequestParam userId: Long,
        @PathVariable cropId: Long
    ): ResponseEntity<String> {
        // 사용자 ID와 작물 ID로 프리셋 해제 서비스 호출
        presetService.removePreset(userId, cropId)
        // 프리셋 해제 완료 응답
        return ResponseEntity.ok("프리셋 해제 완료")
    }

    //프리셋 자동제어
    @PutMapping("/{cropId}/control")
    fun adjustByPreset(
        @PathVariable cropId: Long,
        @RequestBody userPreset: Preset
    ): ResponseEntity<String> {
        // 프리셋 자동제어 서비스 호출
        presetService.adjustByPreset(cropId, userPreset.presetId)
        // 프리셋 자동제어 완료 응답
        return ResponseEntity.ok("프리셋 자동제어 완료")
    }
}
