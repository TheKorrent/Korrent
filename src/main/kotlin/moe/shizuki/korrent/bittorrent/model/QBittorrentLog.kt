package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentLog(
    @field:JsonProperty("id") val id: Int,
    @field:JsonProperty("message") val message: String,
    @field:JsonProperty("timestamp") val timestamp: Long,
    @field:JsonProperty("type") val type: Int
)
