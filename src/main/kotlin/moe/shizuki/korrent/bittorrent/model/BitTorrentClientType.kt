package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonValue

enum class BitTorrentClientType(val client: String) {
    QBITTORRENT("qBittorrent");

    @JsonValue
    fun getValue(): String {
        return client
    }
}
