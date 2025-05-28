package com.example.autofarmer.preset.service

import com.example.autofarmer.crop.repository.CropRepository
import com.example.autofarmer.preset.dto.AdjustResult
import com.example.autofarmer.preset.repository.PresetRepository
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class PresetService(
    private val cropRepository: CropRepository,
    private val presetRepository: PresetRepository
) {
    //프리셋 기온, 습도에 따라 작물 기온, 습도 자동 조절
    fun adjustCropEnvironmentByPreset(cropId: Int, presetId: Long): AdjustResult {
        //작물 조회
        val crop = cropRepository.findById(cropId).orElseThrow { NoSuchElementException("작물 없음 cropId=$cropId") }
        //프리셋 조회
        val preset = presetRepository.findById(presetId).orElseThrow { NoSuchElementException("프리셋 없음 presetId=$presetId") }
        //현재 기온, 습도
        val currentTemp = crop.temperature
        val currentHumidity = crop.humidity
        //목표 기온, 습도
        val targetTemp = preset.temperature
        val targetHumidity = preset.humidity
        //목표와 현재 차이값
        val tempDiff = currentTemp?.minus(targetTemp)?.let { abs(it) }
        val humidityDiff = currentHumidity?.minus(targetHumidity)?.let { abs(it) }
        //최적 기온, 습도 계산
        val optimalTemp = calculateOptimalPreset(currentTemp, targetTemp, tempDiff)
        val optimalHumidity = calculateOptimalPreset(currentHumidity, targetHumidity, humidityDiff)

        return AdjustResult(
            beforeTemp = currentTemp,
            beforeHumidity = currentHumidity,
            afterTemp = optimalTemp.toDouble(),
            afterHumidity = optimalHumidity.toDouble(),
            tempAdjustment = tempDiff,
            humidityAdjustment = humidityDiff
        )
    }

    fun calculateOptimalPreset(cropValue: Double?, presetValue: Double, diff: Double?): Int {
        //기준값 설정
        val base = cropValue ?: return presetValue.toInt()
        //최소, 최대 경계값 설정
        val min = base * 0.8
        val max = base * 1.2
        //diff가 5 이상 차이나면 경계값으로 보정하고 아니면 presetValue사용
        return when {
            diff != null && diff >= 5.0 && presetValue < min -> min.toInt()
            diff != null && diff >= 5.0 && presetValue > max -> max.toInt()
            else -> presetValue.toInt()
        }
    }
}
