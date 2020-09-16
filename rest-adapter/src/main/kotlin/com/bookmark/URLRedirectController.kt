package com.bookmark

import com.bookmark.exceptions.ExceptionResponse
import com.bookmark.port.BookmarkService
import io.swagger.v3.oas.annotations.Hidden
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
import java.net.URI

@RestController
@Hidden
@RequestMapping("/v1/")
@CrossOrigin(origins = ["*"], allowedHeaders = ["GET"])
class URLRedirectController(private val bookmarkService: BookmarkService) {

    @GetMapping(value = ["{groupContextName}/{shortUrlCode}"])
    fun getGroupUrlAndRedirect(@PathVariable("groupContextName") contextName: String, @PathVariable(name = "shortUrlCode") shortUrl: String): ResponseEntity<Void> {
        val url = bookmarkService.getGroupUrlAndRedirect(contextName, shortUrl)
        return ResponseEntity.status(HttpStatus.FOUND).location(URI(url)).build()
    }

    @GetMapping(value = ["{shortUrl}"])
    fun getUrlAndRedirect(@PathVariable shortUrl: String): ResponseEntity<Void> {
        val url = bookmarkService.getOriginalUrlByShortUrl(shortUrl)
        return ResponseEntity.status(HttpStatus.FOUND).location(URI(url)).build()
    }

    private fun getHostName(): String {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}