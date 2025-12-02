package moe.shizuki.korrent.bittorrent.config

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.runBlocking
import moe.shizuki.korrent.bittorrent.client.BitTorrentClientManager
import moe.shizuki.korrent.bittorrent.client.QBittorrentClient
import moe.shizuki.korrent.clientConfigFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BitTorrentConfigManager {
    @Autowired
    private lateinit var clientManager: BitTorrentClientManager

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    fun add(json: String) {
        if(clientConfigFile.exists()) {
            return
        }

        clientConfigFile.parentFile.mkdirs()
        clientConfigFile.createNewFile()
        clientConfigFile.writeText(json)
    }

    fun remove() {
        clientConfigFile.delete()
    }

    fun get(clazz: Class<*>): Any? {
        return objectMapper.readValue(clientConfigFile, clazz)
    }

    fun load() {
        runBlocking {
            val jsonTree = objectMapper.readTree(clientConfigFile)

            if (jsonTree.has("qbittorrent")) {
                val config = get(QBittorrentConfig::class.java)

                if (config is QBittorrentConfig) {
                    val client = QBittorrentClient(config)

                    client.login(config.qbittorrent.username, config.qbittorrent.password)
                    clientManager.add(client)
                }
            }
        }
    }
}
