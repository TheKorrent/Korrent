package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentTracker(
    @field:JsonProperty("msg") val msg: String,
    @field:JsonProperty("num_downloaded") val numDownloaded: Int,
    @field:JsonProperty("num_leeches") val numLeeches: Int,
    @field:JsonProperty("num_peers") val numPeers: Int,
    @field:JsonProperty("num_seeds") val numSeeds: Int,
    @field:JsonProperty("status") val status: Int,
    @field:JsonProperty("tier") val tier: Int,
    @field:JsonProperty("url") val url: String
)
