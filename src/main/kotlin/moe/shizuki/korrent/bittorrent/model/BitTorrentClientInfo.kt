package moe.shizuki.korrent.bittorrent.model

data class BitTorrentClientInfo(
    val client: BitTorrentClientType,
    val name: String,
    val baseUrl: String
)
