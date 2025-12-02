package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentCategory(
    @field:JsonProperty("name") val name: String,
    @field:JsonProperty("savePath") val savePath: String,
)
