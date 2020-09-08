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
        val longUrl: String? = null,

        @Column(name = "EXPIRY_DATE")
        val expiryDate: LocalDate? = null,

        @Column(name = "CREATED_ON")
        val createdOn: LocalDateTime? = null
) {
    fun mapEntityToDto(): Url {
        return Url(
                longUrl = longUrl!!,
                id = id!!,
                expiryDate = expiryDate!!,
                createdOn = createdOn
        )
    }
}