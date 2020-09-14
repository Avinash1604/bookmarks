package com.bookmark

import com.bookmark.model.*
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
     //then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun deleteAGroup() {
        //when
        val url = "$BASE_URL$port$API_END_POINTS/1"
        val response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun addUsersToGroup() {
        // given
        val urlRequest = getUserGroupMock()
        val request: HttpEntity<Group> = HttpEntity(urlRequest)
        //when
        val url = "$BASE_URL$port$API_END_POINTS/users"
        val response = restTemplate.exchange(url, HttpMethod.POST, request, Void::class.java)
        //then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun updateUserRolesToGroup() {
        // given
        val urlRequest = getUserGroupMock()
        val request: HttpEntity<Group> = HttpEntity(urlRequest)
        //when
        val url = "$BASE_URL$port$API_END_POINTS/users/roles"
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }


    @Test
    fun deleteUserForGroup() {
        //when
        val url = "$BASE_URL$port$API_END_POINTS/1/users/1"
        val response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void::class.java)
      //then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun addUrlToGroup() {
        // given
        val urlRequest = getUrlGroupMock()
        val request: HttpEntity<Group> = HttpEntity(urlRequest)
        //when
        val url = "$BASE_URL$port$API_END_POINTS/urls"
        val response = restTemplate.exchange(url, HttpMethod.POST, request, Void::class.java)
        //then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }


    @Test
    fun updateUrlToGroup() {
        // given
        val urlRequest = getUrlGroupMock()
        val request: HttpEntity<Group> = HttpEntity(urlRequest)
        //when
        val url = "$BASE_URL$port$API_END_POINTS/urls"
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java)
        //then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun deleteUrlGroup() {
        //when
        val url = "$BASE_URL$port$API_END_POINTS/1/urls/1"
        val response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    private fun getUserGroupMock(): Group {
        return Group(
                groupName = "atom",
                groupContext = GroupContext.USER,
                groupContextName = "test",
                users = listOf(groupUser())
        )
    }

    private fun getUrlGroupMock(): Group {
        return Group(
                groupName = "atom",
                groupContext = GroupContext.USER,
                groupContextName = "test",
                urls = listOf(groupUrl())
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

    private fun groupUrl(): GroupUrl {
        return GroupUrl(
                title = "url1",
                description = "desc",
                longUrl = "http://localhost:36366"
        )
    }


    companion object {
        private const val BASE_URL = "http://localhost:"
        private const val API_END_POINTS = "/api/v1/bookmarks/groups"
    }
}