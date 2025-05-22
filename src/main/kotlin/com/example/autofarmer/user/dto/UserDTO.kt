package com.example.autofarmer.user.dto

import com.github.f4b6a3.tsid.TsidCreator

data class UserDTO(
    val email: String,
    val nickname: String,
    var status: String,
    val role: String = "USER",
    val userNo: Long = TsidCreator.getTsid().toLong(),
)
