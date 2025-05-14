package com.example.autofarmer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.autofarmer"])
class AutofarmerApplication

fun main(args: Array<String>) {
    runApplication<AutofarmerApplication>(*args)
}
