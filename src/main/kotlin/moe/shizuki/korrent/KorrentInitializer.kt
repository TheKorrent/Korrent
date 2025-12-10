package moe.shizuki.korrent

import moe.shizuki.korrent.bittorrent.config.BitTorrentConfigManager
import moe.shizuki.korrent.bittorrent.config.QBittorrentConfig
import org.pf4j.DefaultPluginManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File

@Component
class KorrentInitializer {
    @Autowired
    private lateinit var configManager: BitTorrentConfigManager

    @Autowired
    private lateinit var pluginManager: DefaultPluginManager

    fun init() {
        initExampleConfigs()
        initBitTorrents()
        initPlugins()
    }

    fun initBitTorrents() {
        configManager.load()
    }

    fun initPlugins() {
        val pluginsFolder = File("plugins/")

        if (!pluginsFolder.exists()) {
            pluginsFolder.mkdirs()
        }

        pluginManager.loadPlugins()
        pluginManager.startPlugins()
    }

    fun initExampleConfigs() {
        val clientConfig = File(exampleConfigFolder, "qbittorrent-client.json")

        if (!clientConfig.exists()) {
            clientConfig.parentFile.mkdirs()
            clientConfig.createNewFile()
            clientConfig.writeText(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(QBittorrentConfig()))
        }
    }
}
