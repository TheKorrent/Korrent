package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient

class QBittorrentTorrentRemovedEvent(
    val client: QBittorrentClient,
    val torrent: String,
)
