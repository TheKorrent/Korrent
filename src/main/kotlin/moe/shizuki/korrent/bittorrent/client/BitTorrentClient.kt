package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.bittorrent.config.PluginConfigManager
import moe.shizuki.korrent.bittorrent.data.PluginDataManager
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientInfo

abstract class BitTorrentClient {
    abstract val clientInfo: BitTorrentClientInfo

    val pluginConfigManager: PluginConfigManager = PluginConfigManager()

    val pluginDataManager = PluginDataManager()
}
