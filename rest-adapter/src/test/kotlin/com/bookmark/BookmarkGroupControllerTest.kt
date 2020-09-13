package com.bookmark

import com.bookmark.model.Group
import com.bookmark.model.GroupContext
import com.bookmark.model.GroupDto
import com.bookmark.model.GroupUser
import com.bookmark.port.BookmarkService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus


@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookmarkGroupControllerTest {
    // bind the above RANDOM_PORT
    @LocalServerPort
    private val port = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var bookmarkService: BookmarkService

    @Test
    fun createGroup() {
        // given
        val urlRequest = getUserGroupMock()
        val request: HttpEntity<Group> = HttpEntity(urlRequest)
        Mockito.`when`(bookmarkService!!.createGroup(urlRequest)).thenReturn(urlRequest);
        //when
        val url = BASE_URL + port + API_END_POINTS
        val response = restTemplate.postForEntity(url, request, Group::class.java)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        Assertions.assertThat(response.body.groupName).isEqualTo("atom")
    }

    @Test
    fun getAllGroups() {
        //when
        val url = BASE_URL + port + API_END_POINTS
        Mockito.`when`(bookmarkService!!.getAllGroup("http://localhost:$port")).thenReturn(listOf(getUserGroupMock()))
        val response = restTemplate.getForEntity(url, GroupDto::class.java)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body.details.size).isEqualTo(1)
    }

    @Test
    fun updateGroup() {
        // given
        val urlRequest = getUserGroupMock()
        val request: HttpEntity<Group> = HttpEntity(urlRequest)
        //when
        val url = BASE_URL + port + API_END_POINTS
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        //then
    }

    @Test
    fun deleteAGroup() {
        //when
        val url = "$BASE_URL$port$API_END_POINTS/1"
        val response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        //then
    }


    private fun getUserGroupMock(): Group {
        return Group(
                groupName = "atom",
                groupContext = GroupContext.USER,
                groupContextName = "test",
                users = listOf(groupUser())
        )
    }

    private fun groupUser(): GroupUser {
        return GroupUser(
                userId = 1,
                userName = "user1",
                email = "user1@gmail.com",
                roleName = "admin"
        )
    }


    companion object {
        private const val BASE_URL = "http://localhost:"
        private const val API_END_POINTS = "/api/v1/bookmarks/groups"
    }
}