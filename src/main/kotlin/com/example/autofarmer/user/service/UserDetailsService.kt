package com.example.autofarmer.user.service

import com.example.autofarmer.user.domain.UserDetails
import com.example.autofarmer.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다.: $email")
        return UserDetails(user)
    }


    fun loadUserByUserNo(userNo: Long): UserDetails {
        val user = userRepository.findByUserNo(userNo)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다.: $userNo")
        return UserDetails(user)
    }
}
