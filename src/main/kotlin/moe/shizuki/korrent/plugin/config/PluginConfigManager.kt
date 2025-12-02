package moe.shizuki.korrent.plugin.config

import moe.shizuki.korrent.objectMapper
import moe.shizuki.korrent.pluginConfigFolder
import java.io.File

class PluginConfigManager {
    fun register(
        name: String,
        config: PluginConfig,
    ) {
        val file = File(pluginConfigFolder, "$name.json")

        file.parentFile.mkdirs()

        if (file.exists()) {
            return
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, config)
    }

    fun load(
        name: String,
        clazz: Class<*>,
    ): Any? = objectMapper.readValue(File(pluginConfigFolder, "$name.json").readText(), clazz)
}
