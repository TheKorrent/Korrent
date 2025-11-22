package moe.shizuki.korrent

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import moe.shizuki.korrent.bittorrent.event.ScheduleEventManager
import moe.shizuki.korrent.plugin.config.PluginConfigManager
import java.io.File

val objectMapper = jacksonObjectMapper().apply {
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

val pluginConfigManager = PluginConfigManager()
val scheduleEventManager = ScheduleEventManager()

val pluginFolder = File("plugins/")
val dataFolder = File("data/")
val cacheFolder = File("cache/")
val configFolder = File("config/")

val pluginDataFolder = File(dataFolder, "plugins/")
val pluginConfigFolder = File(configFolder, "plugins/")
val korrentConfigFile = File(configFolder, "korrent.json")
val clientConfigFile = File(configFolder, "client.json")

