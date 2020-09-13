package com.bookmark.entity

import com.bookmark.model.GroupUser
import java.time.LocalDateTime
import javax.persistence.*
import javax.transaction.Transactional

@Table(name = "T_GROUP_USER")
@Entity
data class GroupUserEntity(
        @Id
        @Column(name = "ID")
        @GeneratedValue(generator = "T_GROUP_USER_SEQUENCE", strategy = GenerationType.AUTO)
        @SequenceGenerator(name = "T_GROUP_USER_SEQUENCE", sequenceName = "T_GROUP_USER_SEQUENCE", allocationSize = 1)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "GROUP_ID")
        var group: GroupEntity? = null,

        @Column(name = "USER_ID")
        var userId: Long? = null,

        @Column(name = "USER_NAME")
        val userName: String? = null,

        @Column(name = "EMAIL")
        val email: String? = null,

        @Column(name = "ROLE_NAME")
        val roleName: String? = null,

        @Column(name = "CREATED_ON")
        val createdOn: LocalDateTime? = null
)