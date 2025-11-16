package moe.shizuki.korrent.web.controller

import moe.shizuki.korrent.web.model.ResponseData
import moe.shizuki.korrent.web.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {
    @Autowired
    private lateinit var service: AuthService

    @PostMapping("/login")
    fun login(@RequestParam("username") username: String, @RequestParam("password") password: String): ResponseData<Void> {
        service.login(username, password)

        return ResponseData(HttpStatus.OK.value(), "Login successful")
    }

    @PostMapping("/logout")
    fun logout(): ResponseData<Void> {
        service.logout()

        return ResponseData(HttpStatus.OK.value(), "Logout successful")
    }
}