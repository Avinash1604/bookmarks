package com.bookmark.cucumber.model

import com.bookmark.model.UrlRequest
import java.time.LocalDate

data class UrlTestModel(
        val longUrl: String,
        var id: Long? = null,
        val expiryDate: String,
        var shortUrl: String? = null,
        val title: String? = null,
        val description: String? = null,
        val bookmarked: Boolean? = false
) {
    fun toModel(): UrlRequest {
        return UrlRequest(
                longUrl = longUrl,
                bookmarked = bookmarked,
                description = description,
                expiryDate = LocalDate.parse(expiryDate),
                title = title
        )
    }

}