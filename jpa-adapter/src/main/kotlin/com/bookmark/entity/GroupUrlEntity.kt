package com.bookmark.entity

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "T_GROUP_URL")
@Entity
data class GroupUrlEntity(
        @Id
        @Column(name = "ID")
        @GeneratedValue(generator = "T_GROUP_URL_SEQUENCE", strategy = GenerationType.AUTO)
        @SequenceGenerator(name = "T_GROUP_URL_SEQUENCE", sequenceName = "T_GROUP_URL_SEQUENCE", allocationSize = 1)
        val id: Long? = null,

        @Column(name = "GROUP_ID")
        val groupId: Long? = null,

        @Column(name = "LONG_URL")
        val longUrl: String? = null,

        @Column(name = "EXPIRY_DATE")
        val expiryDates: LocalDate? = null,

        @Column(name = "CREATED_ON")
        val createdOn: LocalDateTime? = null
) {
}