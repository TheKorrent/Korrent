package moe.shizuki.korrent.bittorrent.config

class QBittorrentConfig(
    val qbittorrent: QBittorrent = QBittorrent(),
) : BitTorrentConfig() {
    data class QBittorrent(
        val username: String = "admin",
        val password: String = "admin12345",
    )
}
