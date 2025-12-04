package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentTagRemovedEvent(
    val client: QBittorrentClient,
    val tag: String
)
