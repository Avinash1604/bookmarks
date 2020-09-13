package com.bookmark.entity

import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "T_GROUP")
@Entity
data class GroupEntity(
        @Id
        @Column(name = "GROUP_ID")
        @GeneratedValue(generator = "T_GROUP_SEQUENCE", strategy = GenerationType.AUTO)
        @SequenceGenerator(name = "T_GROUP_SEQUENCE", sequenceName = "T_GROUP_SEQUENCE", allocationSize = 1)
        val groupId: Long? = null,

        @Column(name = "GROUP_NAME")
        val groupName: String? = null,

        @Column(name = "GROUP_URL")
        val groupUrl: String? = null,

        @Column(name = "CREATED_ON")
        val createdOn: LocalDateTime? = null,

        @OneToMany(
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        private val users: List<GroupUserEntity> = emptyList(),

        @OneToMany(
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        private val urls: List<GroupUrlEntity> = emptyList()

) {
}