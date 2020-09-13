package com.bookmark.repository

import com.bookmark.entity.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository: JpaRepository<GroupEntity, Long> {
}