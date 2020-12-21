package com.sixth.cartographer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CartographerApplication

fun main(args: Array<String>) {
    runApplication<CartographerApplication>(*args)
}
