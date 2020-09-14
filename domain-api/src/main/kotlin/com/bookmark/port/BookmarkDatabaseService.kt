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
    fun getAllGroup(groupId: Long?): List<Group>
    fun updateGroup(group: Group)
    fun deleteGroup(id: Long)
    fun addUsersToGroup(group: Group)
    fun updateUsersRoleToGroup(group: Group)
    fun deleteUserForGroup(groupId: Long, userId: Long)
    fun addUrlsToGroup(group: Group)
    fun updateUrlToGroup(group: Group)
    fun deleteUrlForGroup(groupId: Long, urlId: Long)
}