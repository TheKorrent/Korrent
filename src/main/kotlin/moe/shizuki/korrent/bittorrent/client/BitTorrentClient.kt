package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.bittorrent.config.BitTorrentConfig
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientType
import moe.shizuki.korrent.plugin.config.PluginConfigManager
import moe.shizuki.korrent.plugin.data.PluginDataManager

abstract class BitTorrentClient {
    abstract val clientType: BitTorrentClientType
    abstract val clientConfig: BitTorrentConfig

    val pluginConfigManager: PluginConfigManager = PluginConfigManager()
    val pluginDataManager = PluginDataManager()
}
