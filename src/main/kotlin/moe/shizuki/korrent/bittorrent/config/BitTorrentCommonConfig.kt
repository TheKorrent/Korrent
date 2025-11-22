package moe.shizuki.korrent.bittorrent.config

data class BitTorrentCommonConfig(
    val name: String,
    val baseUrl: String,
    val polling: Polling
) {
    class Polling(
        val schedule: String
    )
}