package com.bookmark.model

import java.time.LocalDate
import java.time.LocalDateTime

data class GroupUrl (
        val id: Long? = null,
        val groupId: Long? = null,
        val longUrl: String? = null,
        val expiryDates: LocalDate? = null,
        val roleName: String? = null,
        val createdOn: LocalDateTime? = null
)