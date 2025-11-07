package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient

class QBittorrentTrackerRemovedEvent(
    val client: QBittorrentClient,
    val tracker: String
)
