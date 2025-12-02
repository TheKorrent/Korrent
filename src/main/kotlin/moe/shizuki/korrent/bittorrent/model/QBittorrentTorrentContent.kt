package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentTorrentContent(
    @field:JsonProperty("availability") val availability: Double,
    @field:JsonProperty("index") val index: Int,
    @field:JsonProperty("is_seed") val isSeed: Boolean,
    @field:JsonProperty("name") val name: String,
    @field:JsonProperty("piece_range") val pieceRange: List<Int>,
    @field:JsonProperty("priority") val priority: Int,
    @field:JsonProperty("progress") val progress: Double,
    @field:JsonProperty("size") val size: Long,
)
