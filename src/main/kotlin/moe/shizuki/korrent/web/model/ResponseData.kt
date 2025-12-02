package moe.shizuki.korrent.web.model

data class ResponseData<T>(
    val code: Int,
    val message: String,
    val `data`: T? = null
)
