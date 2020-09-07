package com.bookmark

import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.port.BookmarkDatabaseService
import org.springframework.stereotype.Service

@Service
class BookmarkJpa: BookmarkDatabaseService {
    override fun createShortUrl(urlRequest: UrlRequest): Url {
      return Url(longUrl = urlRequest.longUrl, expiryDate = urlRequest.expiryDate, id =1)
    }
}