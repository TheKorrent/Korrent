package moe.shizuki.korrent.bittorrent.config

import com.fasterxml.jackson.databind.ObjectMapper
import moe.shizuki.korrent.bittorrent.client.BitTorrentClientManager
import moe.shizuki.korrent.bittorrent.client.QBittorrentClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File

@Component
class BitTorrentConfigManager {
    @Autowired
    private lateinit var clientManager: BitTorrentClientManager

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    fun add(json: String) {
        val file = File("config/client.json")

        if(file.exists()) {
            return
        }

        file.parentFile.mkdirs()
        file.createNewFile()
        file.writeText(json)
    }

    fun remove() {
        val file = File("config/client.json")

        file.delete()
        file.parentFile.delete()
    }

    fun get(clazz: Class<*>): Any? {
        return objectMapper.readValue(File("config/client.json"), clazz)
    }

    fun load() {
        val jsonTree = objectMapper.readTree(File("config/client.json"))

        if (jsonTree.has("qbittorrent")) {
            val config = get(QBittorrentConfig::class.java)

            if (config is QBittorrentConfig) {
                val client = QBittorrentClient(config.common.name, config.common.baseUrl)

                client.login(config.qbittorrent.username, config.qbittorrent.password).execute()
                clientManager.add(client)
            }
        }
    }
}
