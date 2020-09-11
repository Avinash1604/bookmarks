package com.bookmark.model

import java.time.LocalDate

data class UrlRequest(
        var id: Long? = null,
        val longUrl: String? = null,
        val expiryDate: LocalDate? = null,
        val title: String? = null,
        var description: String? = null,
        val bookmarked: Boolean? = false
)