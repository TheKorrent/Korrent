package moe.shizuki.korrent.config

data class KorrentConfig(
    val web: Web = Web()
) {
    data class Web(
        val username: String = "admin",
        val password: String = "admin12345"
    )
}
