package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentTrackerAddedEvent(
    val client: QBittorrentClient,
    val tracker: String
)
