package moe.shizuki.korrent.bittorrent.data

import moe.shizuki.korrent.bittorrent.client.BitTorrentClient
import java.io.File

class PluginDataManager(
    private val client: BitTorrentClient
) {
    val folder: File by lazy {
        File("data/plugins/${client.getClientInfo().name}/")
    }
}
