package com.example.autofarmer.crop.controller

import com.example.autofarmer.crop.domain.Crop
import com.example.autofarmer.crop.service.CropService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/crops")
class CropController(
    private val cropService: CropService
) {
    //작물 카테고리 조회
    @GetMapping("/categories")
    fun getCropCategories(): ResponseEntity<List<Category>> {
        val categories = cropService.getAllCategories()
        return ResponseEntity.ok(categories)
    }

    //작물 카테고리에 속하는 작물 조회
    @GetMapping("/{category}")
    fun getCropsByCategory(
        @PathVariable category: String,
    ): ResponseEntity<List<Crop>> {
        val crops = cropService.getCropsByCategory(category)
        return ResponseEntity.ok(crops)
    }

    //작물 상세 정보 조회
    @GetMapping("/{cropId}")
    fun getCropDetail(
        @PathVariable cropId: Int
    ): ResponseEntity<Crop> {
        val crop = cropService.getCropDetail(cropId)
        return ResponseEntity.ok(crop)
    }

    //작물 온도/습도 범위로 필터링
    @GetMapping("/filter")
    fun filterCrops(
        @RequestParam minTemp: Double?,
        @RequestParam maxTemp: Double?,
        @RequestParam minHumidity: Double?,
        @RequestParam maxHumidity: Double?,
        @RequestParam filterByTemperature: Boolean,
        @RequestParam filterByHumidity: Boolean,
    ): ResponseEntity<List<Crop>> {
        val crops = cropService.filterCrops(minTemp, maxTemp, minHumidity, maxHumidity, filterByTemperature, filterByHumidity)
        return ResponseEntity.ok(crops)
    }

    //작물 즐겨찾기 등록
    @PostMapping("/{cropId}/favorite")
    fun addFavoriteCrop(
        @PathVariable cropId: Int,
        @RequestParam userId: Long
    ): ResponseEntity<String> {
        cropService.addFavoriteCrop(cropId, userId)
        return ResponseEntity.ok("즐겨찾기 등록 완료")
    }

    //작물 즐겨찾기 조회
    @GetMapping("/favorites")
    fun getFavoriteCrops(
        @RequestParam userId: Long
    ): List<Crop> {
        return cropService.getFavoriteCrops(userId)
    }

    //작물 즐겨찾기 해제
    @DeleteMapping("/{cropId}/favorite")
    fun removeFavoriteCrop(
        @RequestParam userId: Long,
        @PathVariable cropId: Int
    ): ResponseEntity<String> {
        cropService.removeFavoriteCrop(userId, cropId)
        return ResponseEntity.ok("즐겨찾기 해제 완료")
    }
}
