package com.example.autofarmer.auth.domain

import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import java.time.LocalDateTime

@Entity
class Withdrawer(
    @Id
    var userNo: Long?,
    val email: String?,
    var reason: String?,
    val withdrawnAt: LocalDateTime?
) {
    @PrePersist
    fun prePersist() {
        userNo = TsidCreator.getTsid().toLong()
    }
}
