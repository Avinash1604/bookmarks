package com.bookmark

import com.bookmark.entity.GroupEntity
import com.bookmark.entity.GroupUserEntity
import com.bookmark.entity.UrlEntity
import com.bookmark.entity.UserEntity
import com.bookmark.model.*
import com.bookmark.port.BookmarkDatabaseService
import com.bookmark.repository.GroupRepository
import com.bookmark.repository.UrlRepository
import com.bookmark.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException


@Service
class BookmarkJpa(private val urlRepository: UrlRepository,
                  private val userRepository: UserRepository,
                  private val groupRepository: GroupRepository) : BookmarkDatabaseService {
    override fun createShortUrl(urlRequest: UrlRequest): Url {
        val urlEntity = UrlEntity(
                longUrl = urlRequest.longUrl,
                expiryDate = urlRequest.expiryDate,
                createdOn = LocalDateTime.now(),
                title = urlRequest.title,
                description = urlRequest.description,
                bookmarked = urlRequest.bookmarked
        )
        return urlRepository.save(urlEntity).mapEntityToDto()
    }

    override fun getOriginalUrlByUrl(urlId: Long): String {
        val urlEntity = urlRepository.findById(urlId)
                .orElseThrow { EntityNotFoundException("There is no entity with") }
        if (urlEntity.expiryDate != null && (urlEntity.expiryDate!! < LocalDate.now())) {
            urlRepository.delete(urlEntity)
            throw EntityNotFoundException("Link expired!")
        }
        return urlEntity.longUrl!!
    }

    override fun createUser(user: UserRequest): User {
        val userEntity = UserEntity(
                email = user.email,
                userName = user.userName,
                password = user.password
        )
        return userRepository.save(userEntity).mapEntityToDto()
    }

    override fun getAllUrls(): List<Url> {
        return urlRepository.findAllByBookmarked(true).map {
            it.mapEntityToDto()
        }
    }

    override fun getUserByCredentials(user: UserRequest): User {
        return userRepository.findByEmailAndPassword(user.email, user.password).mapEntityToDto()

    }

    override fun updateBookmarkUrl(baseUrl: UrlRequest) {
        urlRepository.findById(baseUrl.id).ifPresent {
            it.description = baseUrl.description ?: it.description
            it.title = baseUrl.title ?: it.title
            it.expiryDate = baseUrl.expiryDate ?: it.expiryDate
            it.longUrl = baseUrl.longUrl ?: it.longUrl
            urlRepository.save(it)
        }
    }


    override fun createGroup(group: Group): Group {
        val groupEntity = GroupEntity(
                groupName = group.groupName,
                groupUrl = group.groupUrl,
                groupContext = group.groupContext.toString(),
                groupContextName = group.groupContextName,
                createdOn = LocalDateTime.now()
        )

        val response = groupRepository.save(groupEntity)
        var groupUser = groupUsersToEntityList(group.users!!, response).toMutableList()
        if (response.users != null && response.users.isNotEmpty()) {
            groupUser.addAll(response.users)
        }
        response.users = groupUser;
        return groupRepository.save(response).toDto()
    }

    private fun groupUsersToEntityList(users: List<GroupUser>, groupEntity: GroupEntity): List<GroupUserEntity> {
        return users.map {
            GroupUserEntity(
                    userId = it.userId,
                    userName = it.userName,
                    group = groupEntity,
                    email = it.email,
                    roleName = it.roleName,
                    createdOn = LocalDateTime.now()
            )
        }
    }

    override fun getAllGroup(): List<Group> {
        return groupRepository.findAll().map {
            it.toDto()
        }
    }

    override fun updateGroup(group: Group) {
        groupRepository.findById(group.groupId).ifPresent {
            it.groupName = group.groupName?: it.groupName
            it.groupContext = group.groupContext?.toString() ?: it.groupContext
            it.groupContextName = group.groupContextName ?: it.groupContextName
            groupRepository.save(it)
        }
    }

    override fun deleteGroup(id: Long) {
        groupRepository.deleteById(id)
    }
    override fun deleteBookmarkUrl(id: Long) {
        urlRepository.deleteById(id)
    }
}