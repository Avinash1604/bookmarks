package com.bookmark

import com.bookmark.model.User
import com.bookmark.model.UserRequest
import com.bookmark.port.BookmarkService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus

@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @LocalServerPort
    private val port = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var bookmarkService: BookmarkService

    @Test
    fun createUser() {
        // given
        val request: HttpEntity<UserRequest> = HttpEntity(getUserRequestMock())
        Mockito.`when`(bookmarkService!!.createUser(getUserRequestMock())).thenReturn(getUserMock())
        //when
        val url = BASE_URL + port + API_END_POINTS
        val response = restTemplate.postForEntity(url, request, User::class.java)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        Assertions.assertThat(response.body.userId).isEqualTo(1)
    }

    @Test
    fun getUserByCredentials() {
        // given
        Mockito.`when`(bookmarkService!!.getUserByCredentials(UserRequest(password = "test123",email = "test123@gmaill.com"))).thenReturn(getUserMock())
        //when
        val url = "$BASE_URL$port$API_END_POINTS/by-credentials?email=test123@gmail.com&password=test123"
        val response = restTemplate.getForEntity(url, User::class.java)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
      //  Assertions.assertThat(response.body.userId).isEqualTo(1)
    }


    private fun getUserRequestMock(): UserRequest{
        return UserRequest(userName = "user 1",email = "user1@gmaill.com",password = "pass")
    }

    private fun getUserMock(): User{
        return User(userId = 1,userName = "user 1",email = "user1@gmaill.com")
    }

    companion object {
        private const val BASE_URL = "http://localhost:"
        private const val API_END_POINTS = "/api/v1/users"
    }
}