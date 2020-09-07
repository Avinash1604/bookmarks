package com.bookmark

import com.bookmark.port.BookmarkDatabaseService
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableAutoConfiguration
open class BookmarkApplication{
    @Bean
    open fun bookmarkDomain(bookmarkDatabaseService: BookmarkDatabaseService) = BookmarkDomain(bookmarkDatabaseService)
}

fun main(args: Array<String>) {
    runApplication<BookmarkApplication>(*args)
}