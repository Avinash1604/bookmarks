package com.bookmark.model

import java.time.LocalDateTime

data class GroupUrl (
        val id: Long? = null,
        val groupId: Long? = null,
        val title: String? = null,
        val longUrl: String? = null,
        var shortUrl: String? = null,
        val description: String? = null,
        val createdOn: LocalDateTime? = null
)