package com.example.autofarmer.crop.dto

import com.example.autofarmer.crop.domain.Crop
import com.example.autofarmer.user.domain.User
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.Id

data class Favorite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L, // 식별자

    @ManyToOne
    val user: User, // 유저 정보

    @ManyToOne
    val crop: Crop // 작물 정보
)
