package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentTorrentAllocatingEvent(
    val client: QBittorrentClient,
    val torrent: String
)
