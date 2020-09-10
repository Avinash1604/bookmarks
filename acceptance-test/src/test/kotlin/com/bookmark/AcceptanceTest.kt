package com.bookmark

import com.bookmark.model.UrlRequest
import com.bookmark.model.UserRequest
import com.bookmark.repository.UrlRepository
import com.bookmark.repository.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BookmarkApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {

    @Autowired
    lateinit var bookmarkDomain: BookmarkDomain

    @Autowired
    lateinit var urlRepository: UrlRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun init(){
        userRepository.deleteAll()
        urlRepository.deleteAll()
    }

    @Test
    fun `create short url and store details on database`(){
        val urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30"))
        val response = bookmarkDomain.createShortUrl(urlRequest,"test");
        Assertions.assertThat(response.longUrl).isEqualTo(urlRepository.findAll()[0].longUrl)
    }

    @Test
    fun `list all the bookmarked short urls`(){
        val urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30") , title = "test", description = "test",isBookmark = true)
        bookmarkDomain.createShortUrl(urlRequest, "test");
        Assertions.assertThat(bookmarkDomain.getShortUrls("http://localhost").size).isEqualTo(1)
    }

    @Test
    fun `Create a user`(){
        val response = bookmarkDomain.createUser(getUserRequestMock());
        Assertions.assertThat(response.userId).isEqualTo(userRepository.findAll()[0].userId)
    }


    @Test
    fun `Create a user by email and password`(){
        val responseUser = bookmarkDomain.createUser(getUserRequestMock());
        val response = bookmarkDomain.getUserByCredentials(getUserRequestMock());
        Assertions.assertThat(response.userId).isEqualTo(userRepository.findAll()[0].userId)
    }


    private fun getUserRequestMock(): UserRequest {
        return UserRequest(userName = "user 1",email = "user1@gmaill.com",password = "pass")
    }
}