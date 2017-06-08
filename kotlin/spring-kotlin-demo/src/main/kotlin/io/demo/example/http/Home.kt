package io.demo.example.http

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Home {

    @GetMapping("")
    fun index(): String {
        return "index"
    }

    @GetMapping("/index")
    fun index_(): String {
        return index()
    }

    @GetMapping("/home")
    fun home(): String {
        return index()
    }
}