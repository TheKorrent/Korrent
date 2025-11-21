package moe.shizuki.korrent

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

val objectMapper = jacksonObjectMapper().apply {
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

val pluginFolder = File("plugins/")
val pluginDataFolder = File("data/")
val cacheFolder = File("cache/")
val configFolder = File("config/")

val korrentConfigFile = File(configFolder, "korrent.json")
val clientConfigFile = File(configFolder, "client.json")
val pluginConfigFolder = File(configFolder, "plugins/")
