package com.bookmark.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "T_ROLE")
@Entity
data class RoleEntity(
        @Id
        @Column(name = "ROLE_ID")
        val roleId: Long? = null,

        @Column(name = "ROLE_NAME")
        val email: String? = null
) {
}