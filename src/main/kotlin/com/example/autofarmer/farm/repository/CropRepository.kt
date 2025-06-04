package com.example.autofarmer.farm.repository

import com.example.autofarmer.farm.domain.Crop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

// 작물 정보 인터페이스
interface CropRepository : JpaRepository<Crop, Long> {
    //작물 카테고리 조회
    @Query("SELECT DISTINCT c.category FROM Crop c")
    fun findAllCategories(): List<String>

    //작물 카테고리에 속하는 작물 목록 조회
    fun findByCategory(category: String): List<Crop>
}
