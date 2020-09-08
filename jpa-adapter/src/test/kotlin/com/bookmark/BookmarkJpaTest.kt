package com.bookmark

import com.bookmark.model.UrlRequest
import com.bookmark.repository.UrlRepository
import org.assertj.core.api.Assertions
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

    @Test
    fun `create short url and store details on database`(){
        val urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30"))
        val response = bookmarkJpa.createShortUrl(urlRequest);
        Assertions.assertThat(response.id).isEqualTo(response.id)
    }

}