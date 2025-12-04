package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentTorrentStoppedDownloadEvent(
    val client: QBittorrentClient,
    val torrent: String
)
