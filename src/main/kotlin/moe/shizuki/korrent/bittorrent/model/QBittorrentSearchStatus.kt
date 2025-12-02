package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentSearchStatus(
    @field:JsonProperty("id") val id: Int,
    @field:JsonProperty("status") val status: String,
    @field:JsonProperty("total") val total: Int
)
