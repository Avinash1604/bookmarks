package com.bookmark.port

import com.bookmark.model.*

interface BookmarkService {
    fun createShortUrl(urlRequest: UrlRequest, baseUrl: String): Url
    fun getOriginalUrlByUrl(shortUrl: String): String

    fun createUser(user: UserRequest): User
    fun getUserByCredentials(user: UserRequest): User
    fun getShortUrls(baseUrl: String): List<Url>
    fun updateBookmarkUrl(baseUrl: UrlRequest)
    fun deleteBookmarkUrl(id: Long)
    fun createGroup(group: Group): Group
    fun getAllGroup(baseUrl: String): List<Group>
    fun updateGroup(group: Group)
    fun deleteGroup(id: Long)
}