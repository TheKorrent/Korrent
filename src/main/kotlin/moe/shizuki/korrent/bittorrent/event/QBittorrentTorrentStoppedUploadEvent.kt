package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient

class QBittorrentTorrentStoppedUploadEvent(
    client: QBittorrentClient,
    torrent: String
)
