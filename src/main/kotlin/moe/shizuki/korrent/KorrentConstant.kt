package moe.shizuki.korrent

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val objectMapper = jacksonObjectMapper().apply {
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}
