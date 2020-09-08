package com.bookmark

import com.bookmark.port.BookmarkDatabaseService
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate


@SpringBootApplication
@EnableAutoConfiguration
open class BookmarkApplication{
    @Bean
    open fun bookmarkDomain(bookmarkDatabaseService: BookmarkDatabaseService) = BookmarkDomain(bookmarkDatabaseService)
}

fun main(args: Array<String>) {
    runApplication<BookmarkApplication>(*args)
}