package com.bookmark.repository

import com.bookmark.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long>{
    fun findByEmailAndPassword(email: String, password: String): UserEntity
}