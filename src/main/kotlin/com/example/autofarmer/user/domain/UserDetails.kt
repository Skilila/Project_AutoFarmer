package com.example.autofarmer.user.domain

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetails(
    private val user: User
) : UserDetails {
    override fun getAuthorities() = user.role
        ?.split(",")
        ?.map { it.trim() }
        ?.filter { it.isNotEmpty() }
        ?.map { SimpleGrantedAuthority(it) } // GrantedAuthority로 변환

    override fun getPassword() = user.password
    override fun getUsername() = user.email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = user.status == "ACTIVE"
}
