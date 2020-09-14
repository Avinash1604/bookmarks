package com.bookmark

import com.bookmark.model.*
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

    override fun getShortUrls(baseUrl: String): List<Url> {
        return bookmarkDatabaseService.getAllUrls().map {
            it.shortUrl = baseUrl+'/'+BaseConversion.encode(it.id)
            it
        }
    }

    override fun updateBookmarkUrl(baseUrl: UrlRequest) {
        return bookmarkDatabaseService.updateBookmarkUrl(baseUrl);
    }

    override fun deleteBookmarkUrl(id: Long) {
        return bookmarkDatabaseService.deleteBookmarkUrl(id);
    }

    override fun createGroup(group: Group): Group {
        return bookmarkDatabaseService.createGroup(group)
    }

    override fun getAllGroup(baseUrl: String): List<Group> {
        return bookmarkDatabaseService.getAllGroup()
    }

    override fun updateGroup(group: Group) {
        return bookmarkDatabaseService.updateGroup(group);
    }

    override fun deleteGroup(id: Long) {
        return bookmarkDatabaseService.deleteGroup(id);
    }

    override fun addUsersToGroup(group: Group) {
        bookmarkDatabaseService.addUsersToGroup(group);
    }

    override fun updateUsersRoleToGroup(group: Group) {
        bookmarkDatabaseService.updateUsersRoleToGroup(group)
    }

    override fun deleteUserForGroup(groupId: Long, userId: Long) {
        bookmarkDatabaseService.deleteUserForGroup(groupId, userId)
    }

    override fun addUrlsToGroup(group: Group) {
        bookmarkDatabaseService.addUrlsToGroup(group)
    }

    override fun updateUrlToGroup(group: Group) {
        bookmarkDatabaseService.updateUrlToGroup(group);
    }

    override fun deleteUrlForGroup(groupId: Long, urlId: Long) {
        bookmarkDatabaseService.deleteUrlForGroup(groupId, urlId)
    }
}