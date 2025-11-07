package moe.shizuki.korrent.bittorrent.config

data class QBittorrentConfig(
    val common: BitTorrentCommonConfig,
    val qbittorrent: Config
) {
    data class Config(
        val username: String,
        val password: String
    )
}
