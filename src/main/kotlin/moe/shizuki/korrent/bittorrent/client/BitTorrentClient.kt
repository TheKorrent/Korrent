package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.bittorrent.config.PluginConfigManager
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientInfo

interface BitTorrentClient {
    fun getClientInfo(): BitTorrentClientInfo
    fun getPluginConfigManager(): PluginConfigManager
}
