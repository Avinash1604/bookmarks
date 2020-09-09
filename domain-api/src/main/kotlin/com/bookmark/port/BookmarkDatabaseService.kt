package com.bookmark.port

import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.model.User
import com.bookmark.model.UserRequest

interface BookmarkDatabaseService {
    fun createShortUrl(urlRequest: UrlRequest): Url
    fun getOriginalUrlByUrl(urlId: Long): String

     fun createUser(user: UserRequest): User
}