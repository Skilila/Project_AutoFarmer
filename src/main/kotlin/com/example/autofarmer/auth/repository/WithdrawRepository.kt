package com.example.autofarmer.auth.repository

import com.example.autofarmer.auth.domain.Withdrawer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WithdrawRepository : JpaRepository<Withdrawer, Long>
