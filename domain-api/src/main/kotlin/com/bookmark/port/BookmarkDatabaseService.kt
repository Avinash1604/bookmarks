package com.bookmark.port

import com.bookmark.model.Url
import com.bookmark.model.UrlRequest

interface BookmarkDatabaseService {
    fun createShortUrl(urlRequest: UrlRequest): Url
    fun getOriginalUrlByUrl(urlId: Long): String
}