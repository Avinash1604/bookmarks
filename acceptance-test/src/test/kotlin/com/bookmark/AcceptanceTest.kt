package com.bookmark

import com.bookmark.model.UrlRequest
import org.assertj.core.api.Assertions
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

    @Test
    fun `create short url and store details on database`() {
        val urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30"))
        val response = bookmarkDomain.createShortUrl(urlRequest, "http://localhost:8080");
        Assertions.assertThat(response.id).isEqualTo(response.id)
    }
}