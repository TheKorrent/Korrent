package moe.shizuki.korrent

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.config.BitTorrentConfigManager
import moe.shizuki.korrent.bittorrent.config.QBittorrentConfig
import moe.shizuki.korrent.plugin.KorrentPlugin
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

    @Autowired
    private lateinit var eventbus: EventBus

    fun init() {
        initPlugins()
        initBitTorrents()
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

        val plugins = pluginManager.getExtensions(KorrentPlugin::class.java)

        for (plugin in plugins) {
            plugin.initialize(eventbus)
        }
    }

    fun initExampleConfigs() {
        val clientConfig = File(exampleConfigFolder, "qbittorrent-client.json")

        if (!clientConfig.exists()) {
            clientConfig.createNewFile()
            clientConfig.writeText(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(QBittorrentConfig()))
        }
    }
}
