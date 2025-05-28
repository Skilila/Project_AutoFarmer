package com.example.autofarmer.crop.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id

class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val categoryId: Int, // Category 테이블 PK
    var name: String // 카테고리 이름
)
