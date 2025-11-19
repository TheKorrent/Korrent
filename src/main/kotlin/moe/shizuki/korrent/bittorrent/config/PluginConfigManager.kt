package moe.shizuki.korrent.bittorrent.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import moe.shizuki.korrent.bittorrent.client.BitTorrentClient
import java.io.File

class PluginConfigManager(
    private val client: BitTorrentClient
) {
    fun register(name: String, config: PluginConfig) {
        val file = File("config/${client.getClientInfo().name}/plugins/${name}.json")

        file.parentFile.mkdirs()

        jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValue(file, config)
    }

    fun load(name: String, clazz: Class<*>): Any? {
        return jacksonObjectMapper().readValue(File("config/${client.getClientInfo().name}/plugins/${name}.json").readText(), clazz)
    }
}
