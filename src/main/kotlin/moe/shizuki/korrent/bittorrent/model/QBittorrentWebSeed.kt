package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentWebSeed(
    @field:JsonProperty("url") val url: String
)
