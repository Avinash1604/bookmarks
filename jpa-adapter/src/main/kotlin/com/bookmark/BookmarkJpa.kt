package com.bookmark

import com.bookmark.entity.*
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

    override fun addUsersToGroup(group: Group) {
        groupRepository.findById(group.groupId).ifPresent {
            it.users.addAll(groupUsersToEntityList(group.users!!, it));
            groupRepository.save(it)
        }
    }

    override fun updateUsersRoleToGroup(group: Group) {
        groupRepository.findById(group.groupId).ifPresent { it ->
            if (it.users != null && it.users.isNotEmpty()) {
                it.users.filter {
                    it.userId == group.users?.get(0)?.userId
                }.map {
                    it.roleName = group.users?.get(0)?.roleName
                }
            }
            groupRepository.save(it)
        }
    }

    override fun deleteUserForGroup(groupId: Long, userId: Long) {
        groupRepository.findById(groupId).ifPresent {
            val list = it.users.filter { groupUser ->
                groupUser.userId != userId
            }
            it.users.clear()
            it.users.addAll(list)
            groupRepository.save(it)
        }
    }

    override fun addUrlsToGroup(group: Group) {
        groupRepository.findById(group.groupId).ifPresent {
            it.urls.addAll(groupUrlsToEntityList(group.urls!!, it));
            groupRepository.save(it)
        }
    }

    override fun updateUrlToGroup(group: Group) {
        groupRepository.findById(group.groupId).ifPresent { it ->
            it.urls.filter {
                it.id == group.urls?.get(0)?.id
            }.map {
                it.description = group.urls?.get(0)?.description ?: it.description
                it.title = group.urls?.get(0)?.title ?: it.title
                it.longUrl = group.urls?.get(0)?.longUrl ?: it.longUrl
            }
            groupRepository.save(it)
        }
    }

    override fun deleteUrlForGroup(groupId: Long, urlId: Long) {
        groupRepository.findById(groupId).ifPresent {
            val list = it.urls.filter { groupUser ->
                groupUser.id != urlId
            }
            it.urls.clear()
            it.urls.addAll(list)
            groupRepository.save(it)
        }
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

    private fun groupUrlsToEntityList(urls: List<GroupUrl>, groupEntity: GroupEntity): List<GroupUrlEntity> {
        return urls.map {
            GroupUrlEntity(
                    longUrl = it.longUrl,
                    title = it.title,
                    description = it.description,
                    group = groupEntity,
                    createdOn = LocalDateTime.now()
            )
        }
    }

    override fun getAllGroup(groupId: Long?): List<Group> {
        if(groupId != null){
            var group: Group? = null
              groupRepository.findById(groupId).ifPresent {
                  group = it.toDto()
            }
            return if(group != null) listOf(group!!) else emptyList()
        }
        return groupRepository.findAll().map {
            it.toDto()
        }
    }

    override fun updateGroup(group: Group) {
        groupRepository.findById(group.groupId).ifPresent {
            it.groupName = group.groupName ?: it.groupName
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