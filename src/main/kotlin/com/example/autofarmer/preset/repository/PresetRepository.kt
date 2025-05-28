package com.example.autofarmer.preset.repository

import com.example.autofarmer.preset.domain.Preset
import org.springframework.data.jpa.repository.JpaRepository

interface PresetRepository : JpaRepository<Preset, Long>
