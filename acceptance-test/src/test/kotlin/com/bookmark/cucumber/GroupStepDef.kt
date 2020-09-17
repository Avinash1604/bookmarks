package com.bookmark.cucumber

import com.bookmark.BookmarkApplication
import com.bookmark.cucumber.model.UrlTestModel
import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import cucumber.api.DataTable
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BookmarkApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class GroupStepDef {

    @LocalServerPort
    private val port = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    private lateinit var response: ResponseEntity<Url>


    @When("^user request with following params$")
    @Throws(Throwable::class)
    open fun user_request_with_following_params(arg1: DataTable) { // Write code here that turns the phrase above into concrete actions
        val userRequest = arg1.asList(UrlTestModel::class.java)[0].toModel()
        val request: HttpEntity<UrlRequest> = HttpEntity(userRequest)
        //when
        val url = BASE_URL + port + API_END_POINTS
        response = restTemplate.postForEntity(url, request, Url::class.java)
    }

    @Then("^successfully store response on the database$")
    @Throws(Throwable::class)
    open fun successfully_store_response_on_the_database(arg1: DataTable) { // Write code here that turns the phrase above into concrete actions
        val databaseResponse = arg1.asList(UrlTestModel::class.java)[0]
        Assertions.assertThat(response.body.longUrl).isEqualTo(databaseResponse.longUrl)
    }

    @Then("^the client receives status code of (\\d+)$")
    @Throws(Throwable::class)
    open fun the_client_receives_status_code_of(arg1: Int) { // Write code here that turns the phrase above into concrete actions
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    companion object {
        private const val BASE_URL = "http://localhost:"
        private const val API_END_POINTS = "/api/v1/bookmarks/urls/shorts"
    }

}
