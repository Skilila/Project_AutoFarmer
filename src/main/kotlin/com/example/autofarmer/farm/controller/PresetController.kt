package com.example.autofarmer.farm.controller

import com.example.autofarmer.farm.domain.Crop
import com.example.autofarmer.farm.domain.Preset
import com.example.autofarmer.farm.service.PresetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
        presetService.addToPreset(cropId, userId, temperature, humidity)
        return ResponseEntity.ok("프리셋 등록 완료")
    }

    //프리셋 조회
    @GetMapping("/search")
    fun getPreset(
        @RequestParam userId: Long
    ): List<Crop> {
        return presetService.getPreset(userId)
    }

    //프리셋 해제
    @DeleteMapping("/{cropId}/remove")
    fun removePreset(
        @RequestParam userId: Long,
        @PathVariable cropId: Long
    ): ResponseEntity<String> {
        presetService.removePreset(userId, cropId)
        return ResponseEntity.ok("프리셋 해제 완료")
    }

    //프리셋 자동제어
    @PutMapping("/{cropId}/control")
    fun adjustByPreset(
        @PathVariable cropId: Long,
        @RequestBody userPreset: Preset
    ): ResponseEntity<String> {
        presetService.adjustByPreset(cropId, userPreset.presetId)
        return ResponseEntity.ok("프리셋 조정 완료")
    }
}
