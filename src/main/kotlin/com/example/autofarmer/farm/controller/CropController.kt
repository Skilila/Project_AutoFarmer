package com.example.autofarmer.farm.controller

import com.example.autofarmer.farm.domain.Crop
import com.example.autofarmer.farm.service.CropService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/crops")
class CropController(
    private val cropService: CropService
) {
    //작물 카테고리 조회
    @GetMapping("/categories")
    fun getCropCategories(): ResponseEntity<List<String>> {
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
        @PathVariable cropId: Long
    ): ResponseEntity<Crop> {
        val crop = cropService.getCropDetail(cropId)
        return ResponseEntity.ok(crop)
    }

    //작물- 필터링
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

    //작물 추가
    @PostMapping("/add")
    fun addCrop(
        @RequestBody crop: Crop
    ): ResponseEntity<String> {
        cropService.addCrop(crop)
        return ResponseEntity.ok("작물 추가 완료")
    }

    //작물 삭제
    @DeleteMapping("/{cropId}")
    fun deleteCrop(
        @PathVariable cropId: Long
    ): ResponseEntity<String> {
        cropService.deleteCrop(cropId)
        return ResponseEntity.ok("작물 삭제 완료")
    }


}
