package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentCookie(
    @field:JsonProperty("name") val name: String,
    @field:JsonProperty("domain") val domain: String,
    @field:JsonProperty("path") val path: String,
    @field:JsonProperty("value") val value: String,
    @field:JsonProperty("expirationDate") val expirationDate: Long,
)
