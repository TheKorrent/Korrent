package moe.shizuki.korrent.bittorrent.config

import moe.shizuki.korrent.objectMapper
import java.io.File

class PluginConfigManager(
) {
    fun register(name: String, config: PluginConfig) {
        val file = File("config/plugins/${name}.json")

        file.parentFile.mkdirs()

        if (file.exists()) {
            return
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, config)
    }

    fun load(name: String, clazz: Class<*>): Any? {
        return objectMapper.readValue(File("config/plugins/${name}.json").readText(), clazz)
    }
}
