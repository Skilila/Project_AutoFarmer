package com.example.autofarmer.user.service

import com.example.autofarmer.user.dto.UpdateRequest
import com.example.autofarmer.user.dto.UpdateResponse
import com.example.autofarmer.user.dto.UserDTO
import com.example.autofarmer.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
) {
    //전체 사용자 조회
    fun getAllUser(): List<UserDTO> {
        return userRepository.findAll().map { user ->
            UserDTO(
                nickname = user.nickname,
                email = user.email
            )
        }
    }

    //사용자 정보 조회
    fun getUserInfo(): UserDTO? {
        // SecurityContextHolder에서 사용자 정보 가져오기
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication?.principal
        // principal에서 사용자 번호 가져오기
        val userId = (principal as? UserDTO)?.userId ?: return null
        // 사용자 번호로 사용자 정보 조회
        val user = userRepository.findById(userId).orElseThrow {
            throw IllegalArgumentException("해당 사용자 번호를 가진 사용자가 없습니다. userId: $userId")
        }
        return UserDTO(
            nickname = user.nickname,
            email = user.email
        )
    }

    //사용자 정보 수정
    fun updateUserInfo(request: UpdateRequest): UpdateResponse {
        val user = userRepository.findById(request.userId).orElseThrow {
            throw IllegalArgumentException("해당 사용자 번호를 가진 사용자가 없습니다. userId: ${request.userId}")
        }
        request.nickname?.let { user.nickname = it }
        request.email?.let { user.email = it }
        request.password?.let { user.password = passwordEncoder.encode(it) }
        val updatedUser = userRepository.save(user)

        return UpdateResponse.fromEntity(updatedUser)
    }
}
