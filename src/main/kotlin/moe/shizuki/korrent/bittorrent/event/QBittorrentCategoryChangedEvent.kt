package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient
import moe.shizuki.korrent.bittorrent.model.QBittorrentCategory

class QBittorrentCategoryChangedEvent(
    val client: QBittorrentClient,
    val category: QBittorrentCategory
)
