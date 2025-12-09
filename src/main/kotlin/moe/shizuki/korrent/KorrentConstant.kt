package moe.shizuki.korrent

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.event.ScheduleEventManager
import java.io.File

val objectMapper = jacksonObjectMapper().apply {
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

val eventbus = EventBus()
val scheduleEventManager = ScheduleEventManager()

val pluginFolder = File("plugins/")
val dataFolder = File("data/")
val cacheFolder = File("cache/")
val configFolder = File("config/")
val exampleConfigFolder = File("example/")

val pluginDataFolder = File(dataFolder, "plugins/")
val pluginConfigFolder = File(configFolder, "plugins/")
val korrentConfigFile = File(configFolder, "korrent.json")
val clientConfigFile = File(configFolder, "client.json")
