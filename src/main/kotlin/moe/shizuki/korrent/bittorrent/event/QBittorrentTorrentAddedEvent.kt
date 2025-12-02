package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient

class QBittorrentTorrentAddedEvent(
    val client: QBittorrentClient,
    val torrent: String,
)
