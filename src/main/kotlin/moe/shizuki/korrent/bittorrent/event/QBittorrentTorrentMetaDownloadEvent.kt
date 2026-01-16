package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentTorrentMetaDownloadEvent(
    val client: QBittorrentClient,
    val torrent: String
)
