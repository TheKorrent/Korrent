package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.bittorrent.config.PluginConfigManager
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientInfo

abstract class BitTorrentClient {
    private val pluginConfigManager = PluginConfigManager(this)

    abstract fun getClientInfo(): BitTorrentClientInfo

    fun getPluginConfigManager(): PluginConfigManager {
        return pluginConfigManager
    }
}
