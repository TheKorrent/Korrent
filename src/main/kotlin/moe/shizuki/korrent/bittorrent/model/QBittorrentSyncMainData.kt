package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentSyncMainData(
    @field:JsonProperty("categories") val categories: Map<String, QBittorrentCategory>?,
    @field:JsonProperty("categories_removed") val categoriesRemoved: List<String>?,
    @field:JsonProperty("full_update") val fullUpdate: Boolean?,
    @field:JsonProperty("rid") val rid: Int?,
    @field:JsonProperty("server_state") val serverState: ServerState?,
    @field:JsonProperty("tags") val tags: List<String>?,
    @field:JsonProperty("tags_removed") val tagsRemoved: List<String>?,
    @field:JsonProperty("torrents") val torrents: Map<String, QBittorrentTorrentInfo>?,
    @field:JsonProperty("torrents_removed") val torrentsRemoved: List<String>?,
    @field:JsonProperty("trackers") val trackers: Map<String, Array<String>>?,
    @field:JsonProperty("trackers_removed") val trackersRemoved: List<String>?
) {
    data class ServerState(
        @field:JsonProperty("alltime_dl") val alltimeDl: Long?,
        @field:JsonProperty("alltime_ul") val alltimeUl: Long?,
        @field:JsonProperty("average_time_queue") val averageTimeQueue: Int?,
        @field:JsonProperty("connection_status") val connectionStatus: String?,
        @field:JsonProperty("dht_nodes") val dhtNodes: Int?,
        @field:JsonProperty("dl_info_data") val dlInfoData: Long?,
        @field:JsonProperty("dl_info_speed") val dlInfoSpeed: Int?,
        @field:JsonProperty("dl_rate_limit") val dlRateLimit: Int?,
        @field:JsonProperty("free_space_on_disk") val freeSpaceOnDisk: Long?,
        @field:JsonProperty("global_ratio") val globalRatio: String?,
        @field:JsonProperty("last_external_address_v4") val lastExternalAddressV4: String?,
        @field:JsonProperty("last_external_address_v6") val lastExternalAddressV6: String?,
        @field:JsonProperty("queued_io_jobs") val queuedIoJobs: Int?,
        @field:JsonProperty("queueing") val queueing: Boolean?,
        @field:JsonProperty("read_cache_hits") val readCacheHits: String?,
        @field:JsonProperty("read_cache_overload") val readCacheOverload: String?,
        @field:JsonProperty("refresh_interval") val refreshInterval: Int?,
        @field:JsonProperty("total_buffers_size") val totalBuffersSize: Int?,
        @field:JsonProperty("total_peer_connections") val totalPeerConnections: Int?,
        @field:JsonProperty("total_queued_size") val totalQueuedSize: Int?,
        @field:JsonProperty("total_wasted_session") val totalWastedSession: Int?,
        @field:JsonProperty("up_info_data") val upInfoData: Long?,
        @field:JsonProperty("up_info_speed") val upInfoSpeed: Int?,
        @field:JsonProperty("up_rate_limit") val upRateLimit: Int?,
        @field:JsonProperty("use_alt_speed_limits") val useAltSpeedLimits: Boolean?,
        @field:JsonProperty("use_subcategories") val useSubcategories: Boolean?,
        @field:JsonProperty("write_cache_overload") val writeCacheOverload: String?,
        @field:JsonProperty("piece_range") val pieceRange: List<Int>?
    )
}
