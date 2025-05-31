package com.example.autofarmer.farm.repository

import com.example.autofarmer.farm.domain.Crop
import com.example.autofarmer.farm.domain.Preset
import com.example.autofarmer.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface PresetRepository : JpaRepository<Preset, Long> {
    fun findAllByUser(user: User): List<Preset>
    fun findByUserAndCrop(user: User, crop: Crop): Preset?
}
