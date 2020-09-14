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

        @ManyToOne
        @JoinColumn(name = "GROUP_ID")
        var group: GroupEntity? = null,

        @Column(name = "LONG_URL")
        var longUrl: String? = null,

        @Column(name = "DESCRIPTION")
        var description: String? = null,

        @Column(name = "TITLE")
        var title: String? = null,

        @Column(name = "CREATED_ON")
        val createdOn: LocalDateTime? = null
) {
}