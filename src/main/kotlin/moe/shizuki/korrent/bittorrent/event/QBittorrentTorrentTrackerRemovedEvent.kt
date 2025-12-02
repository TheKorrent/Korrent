package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient

class QBittorrentTorrentTrackerRemovedEvent(
    val client: QBittorrentClient,
    val torrent: String,
    val tracker: String,
)
