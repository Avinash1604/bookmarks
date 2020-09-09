package com.bookmark

import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.model.User
import com.bookmark.model.UserRequest
import com.bookmark.port.BookmarkDatabaseService
import com.bookmark.port.BookmarkService

open class BookmarkDomain(private val bookmarkDatabaseService: BookmarkDatabaseService) : BookmarkService {
    override fun createShortUrl(urlRequest: UrlRequest, baseUrl: String): Url {
        val url = bookmarkDatabaseService.createShortUrl(urlRequest)
        url.shortUrl = baseUrl+'/'+BaseConversion.encode(url.id)
        return url;
    }

    override fun getOriginalUrlByUrl(shortUrlCode: String): String {
        val urlId = BaseConversion.decode(shortUrlCode);
        return bookmarkDatabaseService.getOriginalUrlByUrl(urlId)
    }

    override fun createUser(user: UserRequest): User {
        return bookmarkDatabaseService.createUser(user)
    }

    override fun getUserByCredentials(user: UserRequest): User {
        return bookmarkDatabaseService.getUserByCredentials(user);
    }
}