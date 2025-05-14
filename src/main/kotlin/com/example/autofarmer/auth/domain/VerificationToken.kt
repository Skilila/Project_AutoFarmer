package com.example.autofarmer.auth.domain

import com.example.autofarmer.User.domain.User
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import lombok.Getter
import java.time.LocalDateTime

@Entity
@Getter
class VerificationToken {
    @Id
    var id: Long = 0L
    var token: String = ""

    @OneToOne(fetch = FetchType.LAZY)
    var user: User? = null

    var expiryDate: LocalDateTime = LocalDateTime.now()

    constructor(user: User) {
        this.token = TsidCreator.getTsid().toLong().toString()
        this.user = user
        this.expiryDate = LocalDateTime.now().plusHours(24)
    }
}
