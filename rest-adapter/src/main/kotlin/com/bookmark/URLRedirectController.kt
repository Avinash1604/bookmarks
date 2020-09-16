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
import java.net.URI

@RestController
@Hidden
@Tag(name = "Url Redirect", description = "redirect to original url")
@RequestMapping("/")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class URLRedirectController(private val bookmarkService: BookmarkService) {
    @Operation(summary = "Get short url and redirect to original url", description = "when user hit a short url on browser and should application redirects to original url")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully redirected", content = [
            (Content( schema = Schema(implementation = Void::class)))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = ExceptionResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content(schema = Schema(implementation = ExceptionResponse::class))])]
    )
    @GetMapping(value = ["{shortUrl}"])
    fun getUrlAndRedirect(@PathVariable shortUrl: String): ResponseEntity<Void> {
        val url = bookmarkService.getOriginalUrlByUrl(shortUrl)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).location(URI(url)).build()
    }

}