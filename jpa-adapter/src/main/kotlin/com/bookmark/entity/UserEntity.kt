package com.bookmark.entity

import com.bookmark.model.User
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "T_USER")
@Entity
data class UserEntity(
        @Id
        @Column(name = "USER_ID")
        @GeneratedValue(generator = "T_USER_SEQUENCE", strategy = GenerationType.AUTO)
        @SequenceGenerator(name = "T_USER_SEQUENCE", sequenceName = "T_USER_SEQUENCE", allocationSize = 1)
        val userId: Long? = null,

        @Column(name = "EMAIL")
        val email: String? = null,

        @Column(name = "PASSWORD")
        val password: String? = null,

        @Column(name = "USER_NAME")
        val userName: String? = null,

        @Column(name = "CREATED_ON")
        val createdOn: LocalDateTime? = null
) {
    fun mapEntityToDto(): User {
        return User(
                userId = userId!!,
                userName = userName!!,
                email = email!!
        )
    }
}