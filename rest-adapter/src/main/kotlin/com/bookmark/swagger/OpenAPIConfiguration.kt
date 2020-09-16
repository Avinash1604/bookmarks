package com.bookmark.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info


@OpenAPIDefinition(info = Info(title = "Create the short urls and bookmarks and grouping it",
        description = "User should generate short URLs which will expire " +
                "after a standard default timespan " +
                "and able to group cards in terms of tribes, feature teams, platforms or application",
        contact = Contact(name = "Avinasha NV", url = "https://avinash1604.github.io/bookmarks",
                email = "avinashnv2@gmail.com"), version = "1.0.0"))
class OpenAPIConfiguration