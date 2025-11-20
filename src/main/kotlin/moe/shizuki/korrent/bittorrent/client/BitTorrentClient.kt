package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.bittorrent.config.PluginConfigManager
import moe.shizuki.korrent.bittorrent.data.PluginDataManager
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientInfo
import java.io.File

abstract class BitTorrentClient {
    private val pluginConfigManager = PluginConfigManager(this)
    private val pluginDataManager = PluginDataManager(this)

    abstract fun getClientInfo(): BitTorrentClientInfo

    fun getPluginConfigManager(): PluginConfigManager {
        return pluginConfigManager
    }

    fun getPluginDataFolder(): File {
        return pluginDataManager.folder
    }
}
