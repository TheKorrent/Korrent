package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentSearchId(
    @field:JsonProperty("id") val id: Int
)
