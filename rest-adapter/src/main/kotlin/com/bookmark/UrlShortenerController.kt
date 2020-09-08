package com.bookmark

import com.bookmark.exceptions.ExceptionResponse
import com.bookmark.model.Url
import com.bookmark.model.UrlRequest
import com.bookmark.port.BookmarkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@RestController
@RequestMapping("/api/v1/urls/shorts")
@Tag(name = "URL", description = "This allows user to dealing with big urls problems by shorting it")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class UrlShortenerController(private val bookmarkService: BookmarkService) {
    @PostMapping()
    @Operation(summary = "Create a short url", description = "User will request for the short url by sending the destination url and expiry date")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully created short url", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = Url::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    fun createShortUrl(@RequestBody urlRequest: UrlRequest): ResponseEntity<Url> {
        val baseUrl = getHostName()
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.createShortUrl(urlRequest, baseUrl))
    }

    private fun getHostName(): String {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }


}