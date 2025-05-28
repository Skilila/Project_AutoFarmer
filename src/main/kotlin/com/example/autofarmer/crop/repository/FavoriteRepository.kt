package com.example.autofarmer.crop.repository

import com.example.autofarmer.crop.domain.Crop
import com.example.autofarmer.crop.dto.Favorite
import com.example.autofarmer.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FavoriteRepository : JpaRepository<Favorite, Long> {
    //유저와 작물로 즐겨찾기 조회
    fun findByUserAndCrop(user: User, crop: Crop): Optional<Favorite>

    //유저가 즐겨찾기한 작물 목록 조회
    fun findAllByUser(user: User): List<Favorite>
}
