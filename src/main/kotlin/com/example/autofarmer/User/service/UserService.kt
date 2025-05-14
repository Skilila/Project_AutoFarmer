package com.example.autofarmer.User.service

import com.example.autofarmer.User.dto.UserDTO
import com.example.autofarmer.User.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun createUser(userDTO: UserDTO): UserDTO {
        if (userRepository.existsByEmail(userDTO.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다. email: ${userDTO.email}")
        }
        val user = userRepository.createUser()
        return user.toDTO(user)
    }

    fun getAllUser(): List<UserDTO> {
        return userRepository.findAll().map { user ->
            UserDTO(
                user.email,
                user.nickname
            )
        }
    }

    fun getUserByUserNo(userNo: Long): UserDTO {
        val user = userRepository.findByUserNo(userNo) ?: throw IllegalArgumentException("해당 사용자 번호를 가진 사용자가 없습니다. userNo: $userNo")
        return user.toDTO(user)
    }

    fun updateUser(userNo: Long, newEmail: String, newNickname: String): UserDTO {
        val user = userRepository.findByUserNo(userNo) ?: throw IllegalArgumentException("해당 사용자 번호를 가진 사용자가 없습니다. userNo: $userNo")
        user.updateEmail(newEmail)
        user.updateNickname(newNickname)
        return user.toDTO(user)
    }

    fun deleteUser(userNo: Long) {
        getUserByUserNo(userNo)
        userRepository.deleteByUserNo(userNo)
    }
}
