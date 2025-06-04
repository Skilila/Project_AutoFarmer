package com.example.autofarmer.farm.repository

import com.example.autofarmer.farm.domain.Crop
import com.example.autofarmer.farm.domain.Preset
import com.example.autofarmer.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

// 프리셋 정보 인터페이스
interface PresetRepository : JpaRepository<Preset, Long> {
    // 사용자에 해당하는 프리셋 목록 조회
    fun findAllByUser(user: User): List<Preset>

    // 사용자와 작물에 해당하는 프리셋 조회
    fun findByUserAndCrop(user: User, crop: Crop): Preset?
}
