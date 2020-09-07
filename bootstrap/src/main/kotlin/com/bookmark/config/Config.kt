package com.bookmark.config

import com.bookmark.BookmarkDomain
import com.bookmark.port.BookmarkDatabaseService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Config {
    @Bean
    open fun bookmarkDomain(bookmarkDatabaseService: BookmarkDatabaseService) = BookmarkDomain(bookmarkDatabaseService)
}