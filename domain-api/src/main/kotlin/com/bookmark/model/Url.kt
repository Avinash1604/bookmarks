package com.bookmark.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Url (
    val longUrl: String,
    var id: Long,
    val expiryDate: LocalDate,
    var shortUrl: String? = null,
    var createdOn: LocalDateTime? = null,
    val title: String? = null,
    val description: String? = null,
    val isBookmark: Boolean? = false
)