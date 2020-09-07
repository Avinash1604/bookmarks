package com.bookmark

import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.port.BookmarkDatabaseService
import com.bookmark.port.BookmarkService
import com.bookmark.utils.Constants.BASE_URL

open class BookmarkDomain(private val bookmarkDatabaseService: BookmarkDatabaseService): BookmarkService {
    override fun createShortUrl(urlRequest: UrlRequest): Url {
        val url =  bookmarkDatabaseService.createShortUrl(urlRequest)
        url.shortUrl = BASE_URL+BaseConversion.encode(url.id)
        return url;
    }
}