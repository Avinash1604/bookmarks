package com.bookmark.port

import com.bookmark.model.*

interface BookmarkDatabaseService {
    fun createShortUrl(urlRequest: UrlRequest): Url
    fun getOriginalUrlByUrl(urlId: Long): String
    fun createUser(user: UserRequest): User
    fun getUserByCredentials(user: UserRequest): User
    fun getAllUrls(): List<Url>
    fun updateBookmarkUrl(baseUrl: UrlRequest)
    fun deleteBookmarkUrl(id: Long)
    fun createGroup(group: Group): Group
    fun getAllGroup(): List<Group>
    fun updateGroup(group: Group)
    fun deleteGroup(id: Long)
}