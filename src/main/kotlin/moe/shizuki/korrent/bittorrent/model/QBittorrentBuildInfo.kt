package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentBuildInfo(
    @field:JsonProperty("bitness") val bitness: Int,
    @field:JsonProperty("boost") val boost: String,
    @field:JsonProperty("libtorrent") val libtorrent: String,
    @field:JsonProperty("openssl") val openssl: String,
    @field:JsonProperty("platform") val platform: String,
    @field:JsonProperty("qt") val qt: String,
    @field:JsonProperty("zlib") val zlib: String
)
