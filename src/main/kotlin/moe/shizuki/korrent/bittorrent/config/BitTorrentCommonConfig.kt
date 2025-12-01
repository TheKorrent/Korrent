package moe.shizuki.korrent.bittorrent.config

data class BitTorrentCommonConfig(
    val name: String = "Korrent",
    val baseUrl: String = "https://example.com/",
    val polling: Polling = Polling()
) {
    class Polling(
        val cron: String = "*/5 * * * * *"
    )
}
