package com.bookmark.port

import com.bookmark.model.*

interface BookmarkService {
    fun createShortUrl(urlRequest: UrlRequest, baseUrl: String): Url
    fun getOriginalUrlByShortUrl(shortUrl: String): String
    fun createUser(user: UserRequest): User
    fun getUserByCredentials(user: UserRequest): User
    fun getShortUrls(baseUrl: String): List<Url>
    fun updateBookmarkUrl(baseUrl: UrlRequest)
    fun deleteBookmarkUrl(id: Long)
    fun createGroup(group: Group): Group
    fun getAllGroup(baseUrl: String, groupId: Long?): List<Group>
    fun updateGroup(group: Group)
    fun deleteGroup(id: Long)
    fun addUsersToGroup(group: Group)
    fun updateUsersRoleToGroup(group: Group)
    fun deleteUserForGroup(groupId: Long, userId: Long)
    fun addUrlsToGroup(group: Group)
    fun updateUrlToGroup(group: Group)
    fun deleteUrlForGroup(groupId: Long, urlId: Long)
    fun getAllUsers(): List<User>
    fun getGroupUrlAndRedirect(contextName: String, shortUrl: String): String
}