package com.bookmark

import com.bookmark.exceptions.ExceptionResponse
import com.bookmark.model.Url
import com.bookmark.model.UrlDto
import com.bookmark.model.UrlRequest
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
@RequestMapping("/api/v1/bookmark")
@Tag(name = "Bookmark", description = "This allows user to dealing with big urls problems by shorting it")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class BookmarkController(private val bookmarkService: BookmarkService) {
    @PostMapping(value = ["/urls/shorts"],produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Create a short url", description = "User will request for the short url by sending the destination url and expiry date")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successfully created short url", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = Url::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun createShortUrl(@RequestBody urlRequest: UrlRequest): ResponseEntity<Url> {
        val baseUrl = getHostName()
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.createShortUrl(urlRequest, baseUrl))
    }

    @GetMapping(value = ["/urls/shorts"],produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get list of all bookmarked url", description = "Get list of all bookmarked url")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully get all results", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = UrlDto::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun getAllShortUrl(): UrlDto {
        val baseUrl = getHostName()
        return UrlDto(details = bookmarkService.getShortUrls(baseUrl))
    }


    @PutMapping(value = ["/urls/shorts"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Update a short url", description = "Update the bookmark link")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully updated a short url", content = [
            (Content( schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun updateShortUrl(@RequestBody urlRequest: UrlRequest): ResponseEntity.BodyBuilder {
        bookmarkService.updateBookmarkUrl(urlRequest)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping(value = ["/urls/shorts/{id}"])
    @Operation(summary = "delete a short url", description = "delete the bookmark link")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully deleted a short url", content = [
            (Content( schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun deleteShortUrl(@PathVariable id: Long): ResponseEntity.BodyBuilder {
        bookmarkService.deleteBookmarkUrl(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
    }

    private fun getHostName(): String {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}