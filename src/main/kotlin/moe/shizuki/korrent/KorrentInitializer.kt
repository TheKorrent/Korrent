package moe.shizuki.korrent

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.client.BitTorrentClientManager
import moe.shizuki.korrent.bittorrent.config.BitTorrentConfigManager
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
    private lateinit var clientManager: BitTorrentClientManager

    @Autowired
    private lateinit var pluginManager: DefaultPluginManager

    @Autowired
    private lateinit var eventbus: EventBus

    fun init() {
        initPlugins()
        initBitTorrents()
    }

    fun initBitTorrents() {
        val configRoot = File("config/")

        if (!configRoot.exists()) {
            configRoot.mkdirs()
        }

        val configFolders = configRoot.listFiles() ?: return

        for (configFolder in configFolders) {
            if (!configFolder.isDirectory) {
                continue
            }

            val configFile = configFolder.path + "/config.json"

            if (!File(configFile).exists()) {
                continue
            }

            configManager.load(configFolder.name)
        }
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
}
