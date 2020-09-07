package com.bookmark.exceptions

import org.springframework.http.HttpStatus

data class ExceptionResponse(
        val messages: String? = null,
        val path: String? = null,
        val httpStatus: HttpStatus? = null
)