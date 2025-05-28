package com.example.autofarmer.crop.repository

import com.example.autofarmer.crop.domain.Category
import com.example.autofarmer.crop.domain.Crop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CropRepository : JpaRepository<Crop, Int> {
    //작물 카테고리 조회
    @Query("SELECT DISTINCT c.category FROM Crop c")
    fun findAllCategories(): List<Category>//모든 작물 카테고리 조회

    //작물 카테고리에 속하는 작물 목록 조회
    fun findByCategory(category: String): List<Crop>//특정 카테고리 작물 목록 조회
}
