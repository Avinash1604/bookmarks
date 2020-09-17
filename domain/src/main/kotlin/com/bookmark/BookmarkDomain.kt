package com.bookmark

import com.bookmark.model.*
import com.bookmark.port.BookmarkDatabaseService
import com.bookmark.port.BookmarkService
import com.bookmark.utils.Constants
import com.bookmark.utils.Constants.GROUP_POSTFIX
import com.bookmark.utils.Constants.GROUP_PREFIX
import com.bookmark.utils.Constants.UI_BASE_URL

open class BookmarkDomain(private val bookmarkDatabaseService: BookmarkDatabaseService) : BookmarkService {
    override fun createShortUrl(urlRequest: UrlRequest, baseUrl: String): Url {
        val url = bookmarkDatabaseService.createShortUrl(urlRequest)
        url.shortUrl = baseUrl + "/v1/" + BaseConversion.encode(url.id)
        return url;
    }

    override fun getOriginalUrlByShortUrl(shortUrlCode: String): String {
        val urlSection = shortUrlCode.split(GROUP_PREFIX)
        val urlId = if (urlSection.size > 1) BaseConversion.decode(urlSection[1].split(GROUP_POSTFIX)[0]) else BaseConversion.decode(shortUrlCode)
        return if (shortUrlCode.split(GROUP_POSTFIX).size > 1) bookmarkDatabaseService.getUrlFromGroupUrl(shortUrlCode.split(GROUP_POSTFIX)[1].toLong(), urlId) else bookmarkDatabaseService.getOriginalUrlById(urlId)
    }

    override fun createUser(user: UserRequest): User {
        return bookmarkDatabaseService.createUser(user)
    }

    override fun getUserByCredentials(user: UserRequest): User {
        return bookmarkDatabaseService.getUserByCredentials(user);
    }

    override fun getShortUrls(baseUrl: String): List<Url> {
        return bookmarkDatabaseService.getAllUrls().map {
            it.shortUrl = baseUrl + "/v1/" + BaseConversion.encode(it.id)
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

    override fun getAllGroup(baseUrl: String, groupId: Long?): List<Group> {
        return bookmarkDatabaseService.getAllGroup(groupId).map {
            it.urls?.map { url ->
                url.shortUrl = baseUrl + "/v1/" + GROUP_PREFIX + BaseConversion.encode(url.id!!) + GROUP_POSTFIX + it.groupId
                url
            }
            it.groupUrl = baseUrl + "/v1/" + getUriFromContextName(it.groupContext!!, it.groupName!!) + '/' + BaseConversion.encode(it.groupId!!)
            it
        }
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

    override fun getAllUsers(): List<User> {
        return bookmarkDatabaseService.getAllUsers()
    }

    override fun getGroupUrlAndRedirect(contextName: String, shortUrl: String): String {
        val groupId = BaseConversion.decode(shortUrl)
        val exits = bookmarkDatabaseService.checkGroupIdExits(groupId)
        return if (exits) UI_BASE_URL + groupId else throw Exception("There is no entity with groupId$groupId")
    }

    private fun getUriFromContextName(context: GroupContext, contextName: String): String {
        return when (context) {
            GroupContext.USER, GroupContext.APPLICATION, GroupContext.TRIBE -> contextName
            GroupContext.NONE -> "tiny"
        }
    }
}