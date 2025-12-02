package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient

class QBittorrentTagCreatedEvent(
    val client: QBittorrentClient,
    val tag: String
)
