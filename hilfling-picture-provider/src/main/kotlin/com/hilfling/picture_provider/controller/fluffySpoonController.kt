package com.hilfling.picture_provider

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class FluffySpoonController {

    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello, Spoon!"
    }
}
