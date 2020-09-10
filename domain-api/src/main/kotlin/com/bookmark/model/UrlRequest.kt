package com.bookmark.model

import java.time.LocalDate

data class UrlRequest(
        val longUrl: String,
        val expiryDate: LocalDate,
        val title: String? = null,
        val description: String? = null,
        val bookmarked: Boolean? = false
)