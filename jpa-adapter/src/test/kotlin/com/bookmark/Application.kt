package com.bookmark

import com.bookmark.repository.UrlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableAutoConfiguration
open class Application {
    @Bean
    open fun testRestTemplate(): TestRestTemplate {
        return TestRestTemplate()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}