package moe.shizuki.korrent.config

import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.stp.StpUtil
import moe.shizuki.korrent.web.exception.InvalidTokenException
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SaTokenConfig: WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SaInterceptor {
            if (!StpUtil.isLogin()) {
                throw InvalidTokenException("Invalid token") }
            }
        ).apply {
            addPathPatterns("/**")
            excludePathPatterns("/api/v0/auth/login")
            excludePathPatterns("/api/v0/auth/verify")
            excludePathPatterns("/error")
        }
    }
}
