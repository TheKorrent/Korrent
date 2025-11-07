package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient

class QBittorrentCategoryRemovedEvent(
    val client: QBittorrentClient,
    val category: String
)
