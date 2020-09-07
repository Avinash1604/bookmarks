package com.bookmark

import com.bookmark.port.BookmarkService
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootApplication
@EnableAutoConfiguration
open class Application{
    @MockBean
    lateinit var bookmarkService: BookmarkService
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}