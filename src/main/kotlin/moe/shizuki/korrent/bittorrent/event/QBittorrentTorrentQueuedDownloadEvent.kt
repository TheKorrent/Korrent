package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentTorrentQueuedDownloadEvent(
    val client: QBittorrentClient,
    val torrent: String
)
