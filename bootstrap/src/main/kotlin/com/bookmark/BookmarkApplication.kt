package com.bookmark

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class BookmarkApplication

fun main(args: Array<String>) {
    println("application started")
    runApplication<BookmarkApplication>(*args)
}