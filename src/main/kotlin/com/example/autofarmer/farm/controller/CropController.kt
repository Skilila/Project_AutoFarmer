package com.example.autofarmer.farm.controller

import com.example.autofarmer.farm.domain.Crop
import com.example.autofarmer.farm.service.CropService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// 작물 관련 API 컨트롤러
@RestController
@RequestMapping("/api/crops")
class CropController(
    private val cropService: CropService
) {
    //작물 카테고리 조회
    @GetMapping("/categories")
    fun getCropCategories(): ResponseEntity<List<String>> {
        //작물 카테고리 목록 조회
        val categories = cropService.getAllCategories()
        //조회된 카테고리 목록을 응답으로 반환
        return ResponseEntity.ok(categories)
    }

    //작물 카테고리에 속하는 작물 조회
    @GetMapping("/{category}")
    fun getCropsByCategory(
        @PathVariable category: String,
    ): ResponseEntity<List<Crop>> {
        //카테고리에 해당하는 작물 목록 조회
        val crops = cropService.getCropsByCategory(category)
        //조회된 작물 목록을 응답으로 반환
        return ResponseEntity.ok(crops)
    }

    //작물 상세 정보 조회
    @GetMapping("/{cropId}")
    fun getCropDetail(
        @PathVariable cropId: Long
    ): ResponseEntity<Crop> {
        //작물 ID로 작물 상세 정보 조회
        val crop = cropService.getCropDetail(cropId)
        //조회된 작물 상세 정보를 응답으로 반환
        return ResponseEntity.ok(crop)
    }

    //작물 필터링
    @GetMapping("/filter")
    fun filterCrops(
        @RequestParam minTemp: Double?,
        @RequestParam maxTemp: Double?,
        @RequestParam minHumidity: Double?,
        @RequestParam maxHumidity: Double?,
        @RequestParam filterByTemperature: Boolean,
        @RequestParam filterByHumidity: Boolean,
    ): ResponseEntity<List<Crop>> {
        //온도, 습도 범위로 작물 필터링
        val crops = cropService.filterCrops(minTemp, maxTemp, minHumidity, maxHumidity, filterByTemperature, filterByHumidity)
        //필터링된 작물 목록을 응답으로 반환
        return ResponseEntity.ok(crops)
    }

    //작물 추가
    @PostMapping("/add")
    fun addCrop(
        @RequestBody crop: Crop
    ): ResponseEntity<String> {
        //작물 추가
        cropService.addCrop(crop)
        //작물 추가 완료 메시지 반환
        return ResponseEntity.ok("작물 추가 완료")
    }

    //작물 삭제
    @DeleteMapping("/{cropId}/delete")
    fun deleteCrop(
        @PathVariable cropId: Long
    ): ResponseEntity<String> {
        //작물 ID로 작물 삭제
        cropService.deleteCrop(cropId)
        //작물 삭제 완료 메시지 반환
        return ResponseEntity.ok("작물 삭제 완료")
    }
}
