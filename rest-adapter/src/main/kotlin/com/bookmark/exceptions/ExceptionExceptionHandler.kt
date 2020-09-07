package com.bookmark.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
open class ExceptionExceptionHandler {
    @ExceptionHandler(value = [BadRequestException::class])
    fun handlerBadRequest(request: WebRequest): ResponseEntity<ExceptionResponse> {
        return ResponseEntity(
                ExceptionResponse("Bad request", (request as ServletWebRequest).request.requestURI, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST)
    }
}