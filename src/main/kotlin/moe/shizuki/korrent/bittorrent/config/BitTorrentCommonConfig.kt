package moe.shizuki.korrent.bittorrent.config

data class BitTorrentCommonConfig(
    val name: String = "Korrent",
    val baseUrl: String = "https://example.com/",
    val polling: Polling = Polling(),
    val proxy: Proxy = Proxy()
) {
    class Polling(
        val schedule: String = "*/5 * * * * *"
    )

    class Proxy(
        val host: String = "127.0.0.1",
        val port: Int = 1080,
        val type: java.net.Proxy.Type = java.net.Proxy.Type.HTTP,
        val enabled: Boolean = false
    )
}
