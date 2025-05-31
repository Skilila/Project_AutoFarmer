package com.example.autofarmer.alert.repository

import com.example.autofarmer.alert.domain.Alert
import com.example.autofarmer.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface AlertRepository : JpaRepository<Alert, Long> {
    fun findAllByUser(user: User): List<Alert>
}
