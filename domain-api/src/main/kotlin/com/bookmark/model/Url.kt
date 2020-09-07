package com.bookmark.model

import java.time.LocalDate

data class Url (
    val longUrl: String,
    var id: Long,
    val expiryDate: LocalDate,
    var shortUrl: String? = null
)