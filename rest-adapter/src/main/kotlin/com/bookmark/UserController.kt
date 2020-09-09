package com.bookmark

import com.bookmark.exceptions.ExceptionResponse
import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.model.User
import com.bookmark.model.UserRequest
import com.bookmark.port.BookmarkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Manage all users")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class UserController(private val bookmarkService: BookmarkService) {

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Add user", description = "add user information")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successfully created user", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = User::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun createUser(@RequestBody user: UserRequest): ResponseEntity<User> {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.createUser(user))
    }

    @GetMapping(value=["/by-credentials"],produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "get user by credentials", description = "get user information")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully get a user", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = User::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun getUserByCredentials(@RequestParam(name = "email") email: String, @RequestParam(name = "password") password: String): User {
        val userRequest= UserRequest(email = email, password = password);
        return bookmarkService.getUserByCredentials(userRequest)
    }

}