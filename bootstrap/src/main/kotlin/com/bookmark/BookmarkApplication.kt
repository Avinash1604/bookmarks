package com.bookmark

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class BookmarkApplication

fun main(args: Array<String>) {
    runApplication<BookmarkApplication>(*args)
}