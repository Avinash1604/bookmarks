package com.bookmark.entity

import com.bookmark.model.Url
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "T_URL")
@Entity
data class UrlEntity(
        @Id
        @Column(name = "ID")
        @GeneratedValue(generator = "T_URL_SEQUENCE", strategy = GenerationType.AUTO)
        @SequenceGenerator(name = "T_URL_SEQUENCE", sequenceName = "T_URL_SEQUENCE", allocationSize = 1)
        val id: Long? = null,

        @Column(name = "LONG_URL")
        var longUrl: String? = null,

        @Column(name = "EXPIRY_DATE")
        var expiryDate: LocalDate? = null,

        @Column(name = "TITLE")
        var title: String? = null,

        @Column(name = "DESCRIPTION")
        var description: String? = null,

        @Column(name = "IS_BOOKMARK")
        val bookmarked: Boolean? = false,

        @Column(name = "CREATED_ON")
        val createdOn: LocalDateTime? = null
) {
    fun mapEntityToDto(): Url {
        return Url(
                longUrl = longUrl!!,
                id = id!!,
                expiryDate = expiryDate!!,
                createdOn = createdOn,
                title = title,
                description = description,
                bookmarked = bookmarked
        )
    }
}