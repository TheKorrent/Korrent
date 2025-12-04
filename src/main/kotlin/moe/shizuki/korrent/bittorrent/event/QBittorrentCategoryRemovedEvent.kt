package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentCategoryRemovedEvent(
    val client: QBittorrentClient,
    val category: String
)
