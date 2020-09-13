package com.bookmark

import com.bookmark.model.*
import com.bookmark.repository.GroupRepository
import com.bookmark.repository.UrlRepository
import com.bookmark.repository.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
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

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var groupRepository: GroupRepository

    @BeforeEach
    fun init(){
        userRepository.deleteAll()
        urlRepository.deleteAll()
        groupRepository.deleteAll()
    }

    @Test
    fun `create short url and store details on database`(){
        val urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30"))
        val response = bookmarkJpa.createShortUrl(urlRequest);
        Assertions.assertThat(response.longUrl).isEqualTo(urlRepository.findAll()[0].longUrl)
    }

    @Test
    fun `list all the bookmarked short urls`(){
        val urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30") , title = "test", description = "test",bookmarked = true)
        bookmarkJpa.createShortUrl(urlRequest);
        Assertions.assertThat(bookmarkJpa.getAllUrls().size).isEqualTo(1)
    }

    @Test
    fun `Create a user`(){
        val response = bookmarkJpa.createUser(getUserRequestMock());
        Assertions.assertThat(response.userId).isEqualTo(userRepository.findAll()[0].userId)
    }

    @Test
    fun `Create a user by email and password`(){
        val responseUser = bookmarkJpa.createUser(getUserRequestMock());
        val response = bookmarkJpa.getUserByCredentials(getUserRequestMock());
        Assertions.assertThat(response.userId).isEqualTo(userRepository.findAll()[0].userId)
    }

    @Test
    fun `update short url and store details on database`(){
        var urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30"), description = "test",title = "test", bookmarked = true)
        val response = bookmarkJpa.createShortUrl(urlRequest);
        urlRequest.description = "updated"
        urlRequest.id = response.id
        bookmarkJpa.updateBookmarkUrl(urlRequest)
        Assertions.assertThat("updated").isEqualTo(urlRepository.findAll()[0].description)
    }

    @Test
    fun `delete a short url from database`(){
        var urlRequest = UrlRequest(longUrl = "https://mkyong.com/spring-boot/test/spring/boot", expiryDate = LocalDate.parse("2020-09-30"), description = "test",title = "test", bookmarked = true)
        val response = bookmarkJpa.createShortUrl(urlRequest);
        bookmarkJpa.deleteBookmarkUrl(response.id)
        Assertions.assertThat(0).isEqualTo(urlRepository.findAll().size)
    }

    @Test
    fun `create a group and store details on database`(){
        val response = bookmarkJpa.createGroup(getUserGroupMock());
        Assertions.assertThat(response.groupName).isEqualTo("atom")
    }


    private fun getUserGroupMock(): Group{
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

    private fun getUserRequestMock(): UserRequest {
        return UserRequest(userName = "user 1",email = "user1@gmaill.com",password = "pass")
    }

}