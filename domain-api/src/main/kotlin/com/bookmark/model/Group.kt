package com.bookmark.model

import java.time.LocalDateTime

data class Group(
        val groupId: Long? = null,
        val groupName: String? = null,
        val groupContextName: String? = null,
        val groupContext: GroupContext? = null,
        val groupUrl: String? = null,
        val createdOn: LocalDateTime? = null,
        val users: List<GroupUser>? = emptyList(),
        val urls: List<GroupUrl>? = emptyList()
)