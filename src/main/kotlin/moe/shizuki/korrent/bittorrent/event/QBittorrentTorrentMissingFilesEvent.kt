package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient

class QBittorrentTorrentMissingFilesEvent(
    val client: QBittorrentClient,
    val torrent: String
)
