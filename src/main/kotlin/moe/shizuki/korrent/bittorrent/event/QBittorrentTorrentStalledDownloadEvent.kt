package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentTorrentStalledDownloadEvent(
    val client: QBittorrentClient,
    val torrent: String
)
