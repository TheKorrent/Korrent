package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient
import moe.shizuki.korrent.bittorrent.model.QBittorrentState

class QBittorrentTorrentStateChangedEvent(
    val client: QBittorrentClient,
    val torrent: String,
    val before: QBittorrentState,
    val after: QBittorrentState
)
