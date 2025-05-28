package com.example.autofarmer.crop.service

import com.example.autofarmer.crop.domain.Category
import com.example.autofarmer.crop.domain.Crop
import com.example.autofarmer.crop.dto.Favorite
import com.example.autofarmer.crop.repository.CropRepository
import com.example.autofarmer.crop.repository.FavoriteRepository
import com.example.autofarmer.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CropService(
    private val cropRepository: CropRepository,
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    //작물 카테고리 조회
    fun getAllCategories(): List<Category> {
        val categories = cropRepository.findAllCategories()
        return categories
    }

    //카테고리에 해당하는 작물 목록 조회
    fun getCropsByCategory(category: String): List<Crop> {
        val cropList = cropRepository.findByCategory(category)
        return cropList
    }

    //작물 상세 정보 조회
    fun getCropDetail(cropId: Int): Crop {
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

    //작물 즐겨찾기 등록
    fun addFavoriteCrop(cropId: Int, userId: Long) {
        //사용자 조회
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("사용자 없음 userId=$userId") }
        val crop = cropRepository.findById(cropId).orElseThrow { NoSuchElementException("작물 없음 cropId=$cropId") }
        //이미 즐겨찾기 등록한 작물인지 확인
        val isFavorite = favoriteRepository.findByUserAndCrop(user, crop)
        if (isFavorite.isPresent) {
            throw IllegalStateException("이미 즐겨찾기 등록한 작물입니다.")
        }
        //즐겨찾기 등록
        val favorite = Favorite(user = user, crop = crop)
        favoriteRepository.save(favorite)
    }

    //작물 즐겨찾기 조회
    fun getFavoriteCrops(userId: Long): List<Crop> {
        //사용자 조회
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("사용자 없음 userId=$userId") }
        //사용자가 즐겨찾기한 작물 목록 조회
        val favoriteList = favoriteRepository.findAllByUser(user)
        return favoriteList.map { it.crop }
    }

    //작물 즐겨찾기 해제
    fun removeFavoriteCrop(userId: Long, cropId: Int) {
        //사용자 조회
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("사용자 없음 userId=$userId") }
        //작물 조회
        val crop = cropRepository.findById(cropId).orElseThrow { NoSuchElementException("작물 없음 cropId=$cropId") }
        //사용자와 작물로 즐겨찾기 정보 조회
        val favorite = favoriteRepository.findByUserAndCrop(user, crop).orElseThrow { NoSuchElementException("즐겨찾기 정보가 없습니다.") }
        //즐겨찾기 해제
        favoriteRepository.delete(favorite)
    }
}
