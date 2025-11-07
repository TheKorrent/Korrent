package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentTransferInfo(
    @field:JsonProperty("connection_status") val connectionStatus: String,
    @field:JsonProperty("dht_nodes") val dhtNodes: Int,
    @field:JsonProperty("dl_info_data") val dlInfoData: Long,
    @field:JsonProperty("dl_info_speed") val dlInfoSpeed: Int,
    @field:JsonProperty("dl_rate_limit") val dlRateLimit: Int,
    @field:JsonProperty("last_external_address_v4") val lastExternalAddressV4: String,
    @field:JsonProperty("last_external_address_v6") val lastExternalAddressV6: String,
    @field:JsonProperty("up_info_data") val upInfoData: Long,
    @field:JsonProperty("up_info_speed") val upInfoSpeed: Int,
    @field:JsonProperty("up_rate_limit") val upRateLimit: Int
)
