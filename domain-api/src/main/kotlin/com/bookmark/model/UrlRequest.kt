package com.bookmark.model

import java.time.LocalDate

data class UrlRequest(
        val longUrl: String,
        val expiryDate: LocalDate
)