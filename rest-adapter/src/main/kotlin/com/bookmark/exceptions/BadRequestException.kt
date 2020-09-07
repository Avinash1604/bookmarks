package com.bookmark.exceptions

import java.lang.RuntimeException

class BadRequestException(reason: String): RuntimeException(reason)