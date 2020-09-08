package com.bookmark

import com.bookmark.port.BookmarkService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class URLRedirectControllerTest {
    // bind the above RANDOM_PORT
    @LocalServerPort
    private val port = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var bookmarkService: BookmarkService


    @Test
    fun `should test the redirect to original url when user hits short urls`() {
        // given
        Mockito.`when`(bookmarkService!!.getOriginalUrlByUrl("ab")).thenReturn("https://mkyong.com/spring-boot/test/spring/boo")
        //when
        val url = "$BASE_URL$port/ab"
        val response = restTemplate.getForEntity(url, Void::class.java)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.FOUND)
    }

    companion object {
        private const val BASE_URL = "http://localhost:"
    }
}