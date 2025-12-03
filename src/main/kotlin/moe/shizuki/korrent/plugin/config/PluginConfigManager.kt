package moe.shizuki.korrent.plugin.config

import io.github.oshai.kotlinlogging.KotlinLogging
import moe.shizuki.korrent.objectMapper
import moe.shizuki.korrent.pluginConfigFolder
import java.io.File

private val logger = KotlinLogging.logger {}

class PluginConfigManager(
) {
    fun register(name: String, config: PluginConfig) {
        val file = File(pluginConfigFolder, "${name}.json")

        file.parentFile.mkdirs()

        if (file.exists()) {
            return
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, config)

        logger.info { "Plugin config [$name] registered" }
    }

    fun load(name: String, clazz: Class<*>): Any? {
        return objectMapper.readValue(File(pluginConfigFolder, "${name}.json").readText(), clazz)
    }
}
