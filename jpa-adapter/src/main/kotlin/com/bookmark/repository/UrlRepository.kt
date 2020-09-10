package com.bookmark.repository

import com.bookmark.entity.UrlEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository: JpaRepository<UrlEntity, Long> {
     fun findAllByIsBookmark(b: Boolean): List<UrlEntity>
}