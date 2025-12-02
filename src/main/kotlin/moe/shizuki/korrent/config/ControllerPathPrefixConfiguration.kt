package moe.shizuki.korrent.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ControllerPathPrefixConfiguration: WebMvcConfigurer {
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.addPathPrefix("/api/v0") { clazz ->
            clazz.isAnnotationPresent(RestController::class.java)
        }
    }
}