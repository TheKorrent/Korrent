package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.plugin.config.PluginConfigManager
import moe.shizuki.korrent.plugin.data.PluginDataManager
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientInfo

abstract class BitTorrentClient {
    abstract val clientInfo: BitTorrentClientInfo

    val pluginConfigManager: PluginConfigManager = PluginConfigManager()
    val pluginDataManager = PluginDataManager()
}
