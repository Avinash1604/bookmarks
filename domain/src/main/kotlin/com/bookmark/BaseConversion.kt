package com.bookmark

object BaseConversion {
    private const val allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val allowedCharacters = allowedString.toCharArray()
    private val base = allowedCharacters.size
    fun encode(input: Long): String {
        var input = input
        val encodedString = StringBuilder()
        if (input == 0L) {
            return allowedCharacters[0].toString()
        }
        while (input > 0) {
            encodedString.append(allowedCharacters[(input % base).toInt()])
            input /= base
        }
        return encodedString.reverse().toString()
    }

    fun decode(input: String): Long {
        val characters = input.toCharArray()
        val length = characters.size
        var decoded: Double = 1.0
        var counter = 1
        for (i in 0 until length) {
            decoded += allowedString.indexOf(characters.get(i)) * Math.pow(base.toDouble(), length - counter.toDouble())
            counter++
        }
        return decoded.toLong()
    }
}
