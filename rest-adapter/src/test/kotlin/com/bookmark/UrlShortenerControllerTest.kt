package com.bookmark

import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.port.BookmarkService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import java.time.LocalDate


@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenerControllerTest {
    // bind the above RANDOM_PORT
    @LocalServerPort
    private val port = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var bookmarkService: BookmarkService

    @Test
    fun createShortUrl() {
        // given
        val urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30"))
        val request: HttpEntity<UrlRequest> = HttpEntity(urlRequest)
        Mockito.`when`(bookmarkService!!.createShortUrl(urlRequest)).thenReturn(Url(longUrl = "https://mkyong.com/spring-boot/test/spring/boo", expiryDate = LocalDate.parse("2020-09-30"), shortUrl = "https://localhost:8080/abcd", id = 1))
        //when
        val url = BASE_URL + port + API_END_POINTS
        val response = restTemplate.postForEntity(url, request, Url::class.java)
        //then
        assertThat(response).isNotNull
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body.shortUrl).isEqualTo("https://localhost:8080/abcd")
    }

    companion object {
        private const val BASE_URL = "http://localhost:"
        private const val API_END_POINTS = "/api/v1/urls/shorts"
    }

}