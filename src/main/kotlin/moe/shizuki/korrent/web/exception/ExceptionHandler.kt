package moe.shizuki.korrent.web.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import moe.shizuki.korrent.web.model.ResponseData
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler
    fun handleInvalidUsernamePasswordException(exception: InvalidUsernamePasswordException): ResponseData<Void> {
        logger.error { exception }

        return ResponseData(HttpStatus.UNAUTHORIZED.value(), exception.message)
    }

    @ExceptionHandler
    fun handleInvalidTokenException(exception: InvalidTokenException): ResponseData<Void> {
        logger.error { exception }

        return ResponseData(HttpStatus.UNAUTHORIZED.value(), exception.message)
    }

    @ExceptionHandler
    fun handleHttpRequestMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ResponseData<Void> {
        logger.error { exception }

        return ResponseData(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method not allowed")
    }

    @ExceptionHandler
    fun handleClientNotFoundException(exception: ClientNotFoundException): ResponseData<Void> {
        logger.error { exception }

        return ResponseData(HttpStatus.NOT_FOUND.value(), exception.message)
    }

    @ExceptionHandler
    fun handleClientAlreadyExistsException(exception: ClientAlreadyExistsException): ResponseData<Void> {
        logger.error { exception }

        return ResponseData(HttpStatus.CONFLICT.value(), exception.message)
    }

    @ExceptionHandler
    fun handlePluginNotFoundException(exception: PluginNotFoundException): ResponseData<Void> {
        logger.error { exception }

        return ResponseData(HttpStatus.NOT_FOUND.value(), exception.message)
    }

    @ExceptionHandler
    fun handlePluginAlreadyExistsException(exception: PluginAlreadyExistsException): ResponseData<Void> {
        logger.error { exception }

        return ResponseData(HttpStatus.CONFLICT.value(), exception.message)
    }

    @ExceptionHandler
    fun handleInvalidPluginException(exception: InvalidPluginException): ResponseData<Void> {
        logger.error { exception }

        return ResponseData(HttpStatus.BAD_REQUEST.value(), exception.message)
    }
}
