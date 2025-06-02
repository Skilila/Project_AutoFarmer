package com.example.autofarmer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class AutofarmerApplication

fun main(args: Array<String>) {
    runApplication<AutofarmerApplication>(*args)
}
