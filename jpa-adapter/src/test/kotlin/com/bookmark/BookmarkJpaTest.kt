package com.bookmark

import com.bookmark.model.UrlRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
class BookmarkJpaTest{

    @InjectMocks
    lateinit var bookmarkJpa: BookmarkJpa

    @Test
    fun `create short url and store details on database`(){
        val urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30"))
        val response = bookmarkJpa.createShortUrl(urlRequest);
        Assertions.assertThat(response.id).isEqualTo(response.id)
    }

}