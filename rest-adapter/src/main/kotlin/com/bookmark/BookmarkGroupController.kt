package com.bookmark

import com.bookmark.exceptions.ExceptionResponse
import com.bookmark.model.*
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1/bookmarks/groups")
@Tag(name = "BookmarkGroupUrl", description = "This allows users to create and manage the groups and bookmarked urls")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class BookmarkGroupController(private val bookmarkService: BookmarkService) {
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Create a group", description = "Create a group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successfully created a group", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = Group::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun createGroup(@RequestBody group: Group): ResponseEntity<Group> {
        val baseUrl = getHostName()
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.createGroup(group))
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get list of all bookmarked group", description = "Get list of all bookmarked group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully get all results", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = GroupDto::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun getAllGroup(@RequestParam(required = false) groupId: Long?): GroupDto {
        val baseUrl = getHostName()
        return GroupDto(details = bookmarkService.getAllGroup(baseUrl, groupId))
    }


    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Update a group", description = "Update a group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully updated a group", content = [
            (Content(schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun updateGroup(@RequestBody group: Group) {
        bookmarkService.updateGroup(group)
    }

    @DeleteMapping(value = ["/{id}"])
    @Operation(summary = "delete a group", description = "delete a group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully deleted a group", content = [
            (Content(schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteGroup(@PathVariable id: Long) {
        bookmarkService.deleteGroup(id)
    }

    @PostMapping(value = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Add users to a group", description = "Add users` to a group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successfully user added to a group", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @ResponseStatus(value = HttpStatus.OK)
    fun addUsersToGroup(@RequestBody group: Group) {
        bookmarkService.addUsersToGroup(group)
    }

    @PutMapping(value = ["/users/roles"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Update users role", description = "Update users role")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully updated user role", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun updateUsersRoleToGroup(@RequestBody group: Group) {
        bookmarkService.updateUsersRoleToGroup(group)
    }

    @DeleteMapping(value = ["/{groupId}/users/{userId}"])
    @Operation(summary = "delete a user for a group", description = "delete a user for a group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully deleted a user for a group", content = [
            (Content(schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteUserForGroup(@PathVariable groupId: Long, @PathVariable userId: Long) {
        bookmarkService.deleteUserForGroup(groupId, userId)
    }

    @PostMapping(value = ["/urls"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Add urls to a group", description = "Add urls to a group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successfully urls added to a group", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @ResponseStatus(value = HttpStatus.OK)
    fun addUrlsToGroup(@RequestBody group: Group) {
        bookmarkService.addUrlsToGroup(group)
    }

    @PutMapping(value = ["/urls"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Update urls to a group", description = "Update urls to a group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully updated a url", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun updateUrlToGroup(@RequestBody group: Group) {
        bookmarkService.updateUrlToGroup(group)
    }

    @DeleteMapping(value = ["/{groupId}/urls/{urlId}"])
    @Operation(summary = "delete a url for a group", description = "delete a url for a group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully deleted a url for a group", content = [
            (Content(schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteUrlForGroup(@PathVariable groupId: Long, @PathVariable urlId: Long) {
        bookmarkService.deleteUrlForGroup(groupId, urlId)
    }

    private fun getHostName(): String {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}