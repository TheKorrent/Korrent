package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentSyncPeersData(
    @field:JsonProperty("full_update") val fullUpdate: Boolean,
    @field:JsonProperty("peers") val peers: Map<String, Peer>,
    @field:JsonProperty("rid") val rid: Int,
    @field:JsonProperty("show_flags") val showFlags: Boolean,
) {
    data class Peer(
        @field:JsonProperty("client") val client: String,
        @field:JsonProperty("connection") val connection: String,
        @field:JsonProperty("country") val country: String,
        @field:JsonProperty("country_code") val countryCode: String,
        @field:JsonProperty("dl_speed") val dlSpeed: Long,
        @field:JsonProperty("downloaded") val downloaded: Long,
        @field:JsonProperty("files") val files: String,
        @field:JsonProperty("flags") val flags: String,
        @field:JsonProperty("flags_desc") val flagsDesc: String,
        @field:JsonProperty("ip") val ip: String,
        @field:JsonProperty("peer_id_client") val peerIdClient: String,
        @field:JsonProperty("port") val port: Int,
        @field:JsonProperty("progress") val progress: Double,
        @field:JsonProperty("relevance") val relevance: Int,
        @field:JsonProperty("up_speed") val upSpeed: Long,
        @field:JsonProperty("uploaded") val uploaded: Long,
    )
}
