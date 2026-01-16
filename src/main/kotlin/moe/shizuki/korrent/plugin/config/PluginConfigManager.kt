package moe.shizuki.korrent.plugin.config

import io.github.oshai.kotlinlogging.KotlinLogging
import moe.shizuki.korrent.objectMapper
import moe.shizuki.korrent.pluginConfigFolder
import java.io.File

private val logger = KotlinLogging.logger {}

class PluginConfigManager(
    val id: String
) {
    fun register(config: PluginConfig) {
        val file = File(pluginConfigFolder, "${id}.json")

        file.parentFile.mkdirs()

        logger.debug { "Config registered: [$id]" }

        if (file.exists()) {
            return
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, config)
    }

    inline fun <reified T : Any> load(): T {
        return objectMapper.readValue(File(pluginConfigFolder, "${id}.json").readText(), T::class.java)
    }

    fun <T> load(clazz: Class<T>): T {
        return objectMapper.readValue(File(pluginConfigFolder, "${id}.json").readText(), clazz)
    }
}
