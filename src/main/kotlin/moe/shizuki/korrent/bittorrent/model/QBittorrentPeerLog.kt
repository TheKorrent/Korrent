package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentPeerLog(
    @field:JsonProperty("blocked") val blocked: Boolean,
    @field:JsonProperty("id") val id: Int,
    @field:JsonProperty("ip") val ip: String,
    @field:JsonProperty("reason") val reason: String,
    @field:JsonProperty("timestamp") val timestamp: Long,
)
