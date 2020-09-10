package com.bookmark

import com.bookmark.entity.UrlEntity
import com.bookmark.entity.UserEntity
import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.model.User
import com.bookmark.model.UserRequest
import com.bookmark.port.BookmarkDatabaseService
import com.bookmark.repository.UrlRepository
import com.bookmark.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException


@Service
class BookmarkJpa(private val urlRepository: UrlRepository, private val userRepository: UserRepository) : BookmarkDatabaseService {
    override fun createShortUrl(urlRequest: UrlRequest): Url {
        val urlEntity = UrlEntity(
                longUrl = urlRequest.longUrl,
                expiryDate = urlRequest.expiryDate,
                createdOn = LocalDateTime.now(),
                title = urlRequest.title,
                description = urlRequest.description,
                isBookmark = urlRequest.isBookmark
        )
        return urlRepository.save(urlEntity).mapEntityToDto()
    }

    override fun getOriginalUrlByUrl(urlId: Long): String {
        val urlEntity = urlRepository.findById(urlId)
                .orElseThrow { EntityNotFoundException("There is no entity with") }
        if (urlEntity.expiryDate != null && (urlEntity.expiryDate < LocalDate.now())) {
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
        return urlRepository.findAllByIsBookmark(true).map {
            it.mapEntityToDto()
        }
    }

    override fun getUserByCredentials(user: UserRequest): User {
        return userRepository.findByEmailAndPassword(user.email, user.password).mapEntityToDto()

    }
}