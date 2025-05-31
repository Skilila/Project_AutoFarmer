package com.example.autofarmer.user.dto

import com.github.f4b6a3.tsid.TsidCreator

data class UserDTO(
    val userId: Long = TsidCreator.getTsid().toLong(),
    val nickname: String,
    val email: String,
    val alertType: String = "NONE",
    val role: String = "USER",
    var status: String = "ACTIVE"
)
