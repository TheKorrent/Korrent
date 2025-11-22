package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.bittorrent.config.BitTorrentConfig
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientType

abstract class BitTorrentClient {
    abstract val clientType: BitTorrentClientType
    abstract val clientConfig: BitTorrentConfig
}
