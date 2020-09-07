package com.bookmark

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}