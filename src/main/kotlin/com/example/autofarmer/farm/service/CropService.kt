package com.example.autofarmer.farm.service

import com.example.autofarmer.farm.domain.Crop
import com.example.autofarmer.farm.repository.CropRepository
import org.springframework.stereotype.Service

// 작물 서비스
@Service
class CropService(
    private val cropRepository: CropRepository,
) {
    //작물 카테고리 조회
    fun getAllCategories(): List<String> {
        val categories = cropRepository.findAllCategories()
        return categories
    }

    //카테고리에 해당하는 작물 목록 조회
    fun getCropsByCategory(category: String): List<Crop> {
        val cropList = cropRepository.findByCategory(category)
        return cropList
    }

    //작물 상세 정보 조회
    fun getCropDetail(cropId: Long): Crop {
        val crop = cropRepository.findById(cropId).orElseThrow { NoSuchElementException("작물 없음 cropId=$cropId") }
        return crop
    }

    //작물 온도/습도 범위로 필터링
    fun filterCrops(
        minTemp: Double? = null,
        maxTemp: Double? = null,
        minHumidity: Double? = null,
        maxHumidity: Double? = null,
        filterByTemperature: Boolean = true,
        filterByHumidity: Boolean = true
    ): List<Crop> {
        return cropRepository.findAll().filter { crop ->
            //온도 범위로 필터링
            val temperaturePass = if (filterByTemperature) {
                (minTemp == null || crop.temperature!! >= minTemp) &&
                        (maxTemp == null || crop.temperature!! <= maxTemp)
            } else {
                true
            }
            //습도 범위로 필터링
            val humidityPass = if (filterByHumidity) {
                (minHumidity == null || crop.humidity!! >= minHumidity) &&
                        (maxHumidity == null || crop.humidity!! <= maxHumidity)
            } else {
                true
            }
            //온도, 습도 조건에 따라 필터링
            temperaturePass && humidityPass
        }
    }

    //작물 추가
    fun addCrop(crop: Crop): Crop {
        //이미 추가한 작물인지 확인
        if (cropRepository.existsById(crop.cropId!!)) {
            throw IllegalArgumentException("작물 ID가 이미 존재합니다: ${crop.cropId}")
        }
        //작물 추가 및 저장
        val newCrop = Crop(
            cropId = crop.cropId,
            name = crop.name,
            category = crop.category,
            temperature = crop.temperature,
            humidity = crop.humidity,
            status = crop.status,
            isPreset = crop.isPreset
        )
        return cropRepository.save(newCrop)
    }

    //작물 삭제
    fun deleteCrop(cropId: Long) {
        //작물 ID가 존재하는지 확인 및 삭제
        if (!cropRepository.existsById(cropId)) {
            throw NoSuchElementException("작물 없음 cropId=$cropId")
        }
        cropRepository.deleteById(cropId)
    }
}
