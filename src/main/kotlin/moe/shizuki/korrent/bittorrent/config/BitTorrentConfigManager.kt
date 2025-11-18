package moe.shizuki.korrent.bittorrent.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import moe.shizuki.korrent.bittorrent.client.BitTorrentClientManager
import moe.shizuki.korrent.bittorrent.client.QBittorrentClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File

@Component
class BitTorrentConfigManager {
    private val objectMapper = jacksonObjectMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    @Autowired
    private lateinit var clientManager: BitTorrentClientManager

    fun add(json: String) {
        val name = objectMapper.readTree(json).get("common").get("name").asText()

        val file = File("config/$name/config.json")

        if(file.exists()) {
            return
        }

        file.parentFile.mkdirs()
        file.createNewFile()
        file.writeText(json)
    }

    fun add(name: String, config: Any) {
        val file = File("config/$name/config.json")

        if(file.exists()) {
            return
        }

        file.parentFile.mkdirs()
        file.createNewFile()

        objectMapper.writeValue(file, config)
    }

    fun remove(name: String) {
        val file = File("config/$name/config.json")

        file.delete()
        file.parentFile.delete()
    }

    fun get(name: String, clazz: Class<*>): Any? {
        return objectMapper.readValue(File("config/$name/config.json"), clazz)
    }

    fun load(name: String) {
        val jsonTree = jacksonObjectMapper().readTree(File("config/$name/config.json"))

        if (jsonTree.has("qbittorrent")) {
            val config = get(name, QBittorrentConfig::class.java)

            if (config is QBittorrentConfig) {
                val client = QBittorrentClient(config.common.name, config.common.baseUrl)

                client.login(config.qbittorrent.username, config.qbittorrent.password).execute()
                clientManager.add(client)
            }
        }
    }
}
