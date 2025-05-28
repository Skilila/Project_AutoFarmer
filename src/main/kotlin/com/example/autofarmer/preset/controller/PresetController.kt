package com.example.autofarmer.preset.controller

import com.example.autofarmer.crop.domain.Crop
import com.example.autofarmer.preset.domain.Preset
import com.example.autofarmer.preset.service.PresetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/preset")
class PresetController(
    private val presetService: PresetService
) {

    //프리셋 조정
    @PutMapping("/{cropId}/adjust")
    fun adjustPreset(
        @PathVariable cropId: Long,
        @RequestBody userPreset: Preset
    ): ResponseEntity<Crop> {
        val updatedCrop = presetService.adjustPreset(cropId, userPreset)

        return ResponseEntity.ok(updatedCrop)
    }
}
