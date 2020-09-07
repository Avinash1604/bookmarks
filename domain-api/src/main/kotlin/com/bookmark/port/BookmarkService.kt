package com.bookmark.port

import com.bookmark.model.Url
import com.bookmark.model.UrlRequest

interface BookmarkService {
    fun createShortUrl(urlRequest: UrlRequest): Url
}