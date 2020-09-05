package com.bookmark

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@SpringBootApplication
open class BookmarkApplication{
    @RequestMapping("/")
    @ResponseBody
    fun home(): String {
        return "Hello World!";
    }
}

fun main(args: Array<String>) {
    println("application started")
    runApplication<BookmarkApplication>(*args)
}