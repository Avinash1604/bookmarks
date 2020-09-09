package com.bookmark

import com.bookmark.model.UrlRequest
import com.bookmark.model.UserRequest
import com.bookmark.repository.UrlRepository
import com.bookmark.repository.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate


@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookmarkJpaTest{

    @Autowired
    lateinit var urlRepository: UrlRepository

    @Autowired
    lateinit var bookmarkJpa: BookmarkJpa

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
        val response = bookmarkJpa.createShortUrl(urlRequest);
        Assertions.assertThat(response.longUrl).isEqualTo(urlRepository.findAll()[0].longUrl)
    }

    @Test
    fun `Create a user`(){
        val response = bookmarkJpa.createUser(getUserRequestMock());
        Assertions.assertThat(response.userId).isEqualTo(userRepository.findAll()[0].userId)
    }

    @Test
    fun `Create a user by email and password`(){
        val responseUser = bookmarkJpa.createUser(getUserRequestMock());
        val response = bookmarkJpa.getUserByCredentials(getUserRequestMock());
        Assertions.assertThat(response.userId).isEqualTo(userRepository.findAll()[0].userId)
    }


    private fun getUserRequestMock(): UserRequest {
        return UserRequest(userName = "user 1",email = "user1@gmaill.com",password = "pass")
    }

}