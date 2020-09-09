package com.bookmark.port

import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.model.User
import com.bookmark.model.UserRequest

interface BookmarkService {
    fun createShortUrl(urlRequest: UrlRequest, baseUrl: String): Url
    fun getOriginalUrlByUrl(shortUrl: String): String

    fun createUser(user: UserRequest): User
    fun getUserByCredentials(user: UserRequest): User
}