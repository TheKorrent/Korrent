package moe.shizuki.korrent.bittorrent.config

class QBittorrentConfig(
    override val common: BitTorrentCommonConfig,
    val qbittorrent: QBittorrent
): BitTorrentConfig(common) {
    data class QBittorrent(
        val username: String,
        val password: String
    )
}
