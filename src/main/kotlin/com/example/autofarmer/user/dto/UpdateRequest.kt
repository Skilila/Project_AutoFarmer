package com.example.autofarmer.user.dto

import com.github.f4b6a3.tsid.TsidCreator
import java.io.Serializable

data class UpdateRequest(
    val userNo: Long = TsidCreator.getTsid().toLong(),
    val nickname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val notificationType: String? = null,
) : Serializable
