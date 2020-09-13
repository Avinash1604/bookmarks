package com.bookmark.model

import java.time.LocalDateTime

data class GroupUser (
        val id: Long? = null,
        val groupId: Long? = null,
        val userId: Long? = null,
        val longUrl: String? = null,
        val userName: String? = null,
        val email: String? = null,
        val roleName: String? = null,
        val createdOn: LocalDateTime? = null
)