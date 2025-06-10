package com.example.autofarmer.farm.service

import com.example.autofarmer.farm.domain.Crop
import com.example.autofarmer.farm.domain.Preset
import com.example.autofarmer.farm.repository.CropRepository
import com.example.autofarmer.farm.repository.PresetRepository
import com.example.autofarmer.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.math.abs

// 프리셋 서비스
@Service
class PresetService(
    private val cropRepository: CropRepository,
    private val userRepository: UserRepository,
    private val presetRepository: PresetRepository
) {
    //프리셋 등록
    fun addToPreset(cropId: Long?, userId: Long?, temperature: Double, humidity: Double) {
        //사용자 조회
        val user = userRepository.findById(userId!!).orElseThrow { NoSuchElementException("사용자 없음 userId=$userId") }
        //작물 조회
        val crop = cropRepository.findById(cropId!!).orElseThrow { NoSuchElementException("작물 없음 cropId=$cropId") }
        //이미 프리셋으로 등록한 작물인지 확인
        if (crop.isPreset) {
            throw IllegalStateException("이미 프리셋으로 등록된 작물입니다.")
        }
        //프리셋 등록
        val preset = Preset(
            user = user,
            crop = crop,
            temperature = temperature,
            humidity = humidity,
        )
        //프리셋 생성 시간 업데이트
        preset.createdAt = LocalDateTime.now()
        //작물의 isPreset 상태 변경
        crop.isPreset = true
        //프리셋 저장
        presetRepository.save(preset)
    }

    //프리셋 조회
    fun getPreset(userId: Long?): List<Crop> {
        //사용자 조회
        val user = userRepository.findById(userId!!).orElseThrow { NoSuchElementException("사용자 없음 userId=$userId") }
        //사용자가 프리셋한 작물 목록 조회
        val presetList = presetRepository.findAllByUser(user)
        return presetList.map { it.crop }
    }

    //프리셋 수정
    fun updatePreset(presetId: Long?, temperature: Double, humidity: Double) {
        //프리셋 조회
        val preset = presetRepository.findById(presetId!!).orElseThrow { NoSuchElementException("프리셋 없음 presetId=$presetId") }
        //프리셋 정보 업데이트
        preset.temperature = temperature
        preset.humidity = humidity
        //프리셋 수정 시간 업데이트
        preset.updatedAt = LocalDateTime.now()
        //프리셋 저장
        presetRepository.save(preset)
    }

    //프리셋 해제
    fun removePreset(userId: Long?, cropId: Long?) {
        //사용자 조회
        val user = userRepository.findById(userId!!).orElseThrow { NoSuchElementException("사용자 없음 userId=$userId") }
        //작물 조회
        val crop = cropRepository.findById(cropId!!).orElseThrow { NoSuchElementException("작물 없음 cropId=$cropId") }
        //사용자와 작물로 프리셋 정보 조회
        val preset = presetRepository.findByUserAndCrop(user, crop) ?: throw NoSuchElementException("프리셋 정보가 없습니다.")
        //프리셋 해제
        presetRepository.delete(preset)
    }

    //프리셋 자동제어
    fun adjustByPreset(cropId: Long??, presetId: Long?) {
        //작물 조회
        val crop = cropRepository.findById(cropId!!).orElseThrow { NoSuchElementException("작물 없음 cropId=$cropId") }
        //프리셋 조회
        val preset = presetRepository.findById(presetId!!).orElseThrow { NoSuchElementException("프리셋 없음 presetId=$presetId") }
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
        //작물의 기온, 습도 업데이트
        crop.temperature = optimalTemp.toDouble()
        crop.humidity = optimalHumidity.toDouble()
    }

    // 프리셋 기온, 습도에 따라 최적의 기온, 습도 계산
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
