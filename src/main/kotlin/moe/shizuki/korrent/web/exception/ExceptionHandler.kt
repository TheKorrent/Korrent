package moe.shizuki.korrent.web.exception

import moe.shizuki.korrent.web.model.ResponseData
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler
    fun handleInvalidUsernamePasswordException(exception: InvalidUsernamePasswordException): ResponseData<Void> {
        return ResponseData(HttpStatus.UNAUTHORIZED.value(), exception.message)
    }

    @ExceptionHandler
    fun handleInvalidTokenException(exception: InvalidTokenException): ResponseData<Void> {
        return ResponseData(HttpStatus.UNAUTHORIZED.value(), exception.message)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(): ResponseData<Void> {
        return ResponseData(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method not allowed")
    }

    @ExceptionHandler
    fun handleClientNotFoundException(exception: ClientNotFoundException): ResponseData<Void> {
        return ResponseData(HttpStatus.NOT_FOUND.value(), exception.message)
    }

    @ExceptionHandler
    fun handleClientAlreadyExistsException(exception: ClientAlreadyExistsException): ResponseData<Void> {
        return ResponseData(HttpStatus.CONFLICT.value(), exception.message)
    }

    @ExceptionHandler
    fun handlePluginNotFoundException(exception: PluginNotFoundException): ResponseData<Void> {
        return ResponseData(HttpStatus.NOT_FOUND.value(), exception.message)
    }

    @ExceptionHandler
    fun handlePluginAlreadyExistsException(exception: PluginAlreadyExistsException): ResponseData<Void> {
        return ResponseData(HttpStatus.CONFLICT.value(), exception.message)
    }

    @ExceptionHandler
    fun handleInvalidPluginException(exception: InvalidPluginException): ResponseData<Void> {
        return ResponseData(HttpStatus.BAD_REQUEST.value(), exception.message)
    }
}
