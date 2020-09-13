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
            (Content(mediaType = "application/json", schema = Schema(implementation = Url::class)))]),
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
    fun getAllGroup(): GroupDto {
        val baseUrl = getHostName()
        return GroupDto(details = bookmarkService.getAllGroup(baseUrl))
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
    fun deleteShortUrl(@PathVariable id: Long) {
        bookmarkService.deleteGroup(id)
    }

    private fun getHostName(): String {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}