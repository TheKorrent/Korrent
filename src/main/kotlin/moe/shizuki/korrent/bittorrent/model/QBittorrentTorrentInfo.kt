package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.reflect.full.primaryConstructor

data class QBittorrentTorrentInfo(
    @field:JsonProperty("added_on") val addedOn: Long?,
    @field:JsonProperty("amount_left") val amountLeft: Long?,
    @field:JsonProperty("auto_tmm") val autoTmm: Boolean?,
    @field:JsonProperty("availability") val availability: Double?,
    @field:JsonProperty("category") val category: String?,
    @field:JsonProperty("comment") val comment: String?,
    @field:JsonProperty("completed") val completed: Long?,
    @field:JsonProperty("completion_on") val completionOn: Long?,
    @field:JsonProperty("content_path") val contentPath: String?,
    @field:JsonProperty("dl_limit") val dlLimit: Long?,
    @field:JsonProperty("dlspeed") val dlspeed: Long?,
    @field:JsonProperty("download_path") val downloadPath: String?,
    @field:JsonProperty("downloaded") val downloaded: Long?,
    @field:JsonProperty("downloaded_session") val downloadedSession: Long?,
    @field:JsonProperty("eta") val eta: Int?,
    @field:JsonProperty("f_l_piece_prio") val fLPiecePrio: Boolean?,
    @field:JsonProperty("force_start") val forceStart: Boolean?,
    @field:JsonProperty("has_metadata") val hasMetadata: Boolean?,
    @field:JsonProperty("hash") val hash: String?,
    @field:JsonProperty("inactive_seeding_time_limit") val inactiveSeedingTimeLimit: Int?,
    @field:JsonProperty("infohash_v1") val infohashV1: String?,
    @field:JsonProperty("infohash_v2") val infohashV2: String?,
    @field:JsonProperty("last_activity") val lastActivity: Long?,
    @field:JsonProperty("magnet_uri") val magnetUri: String?,
    @field:JsonProperty("max_inactive_seeding_time") val maxInactiveSeedingTime: Int?,
    @field:JsonProperty("max_ratio") val maxRatio: Double?,
    @field:JsonProperty("max_seeding_time") val maxSeedingTime: Int?,
    @field:JsonProperty("name") val name: String?,
    @field:JsonProperty("num_complete") val numComplete: Int?,
    @field:JsonProperty("num_incomplete") val numIncomplete: Int?,
    @field:JsonProperty("num_leechs") val numLeechs: Int?,
    @field:JsonProperty("num_seeds") val numSeeds: Int?,
    @field:JsonProperty("popularity") val popularity: Double?,
    @field:JsonProperty("priority") val priority: Int?,
    @field:JsonProperty("private") val `private`: Boolean?,
    @field:JsonProperty("progress") val progress: Double?,
    @field:JsonProperty("ratio") val ratio: Double?,
    @field:JsonProperty("ratio_limit") val ratioLimit: Double?,
    @field:JsonProperty("reannounce") val reannounce: Int?,
    @field:JsonProperty("root_path") val rootPath: String?,
    @field:JsonProperty("save_path") val savePath: String?,
    @field:JsonProperty("seeding_time") val seedingTime: Long?,
    @field:JsonProperty("seeding_time_limit") val seedingTimeLimit: Long?,
    @field:JsonProperty("seen_complete") val seenComplete: Int?,
    @field:JsonProperty("seq_dl") val seqDl: Boolean?,
    @field:JsonProperty("size") val size: Long?,
    @field:JsonProperty("state") val state: QBittorrentState?,
    @field:JsonProperty("super_seeding") val superSeeding: Boolean?,
    @field:JsonProperty("tags") val tags: String?,
    @field:JsonProperty("time_active") val timeActive: Int?,
    @field:JsonProperty("total_size") val totalSize: Long?,
    @field:JsonProperty("tracker") val tracker: String?,
    @field:JsonProperty("trackers_count") val trackersCount: Int?,
    @field:JsonProperty("up_limit") val upLimit: Long?,
    @field:JsonProperty("uploaded") val uploaded: Long?,
    @field:JsonProperty("uploaded_session") val uploadedSession: Long?,
    @field:JsonProperty("upspeed") val upspeed: Long?
) {
    fun mergeWith(other: QBittorrentTorrentInfo): QBittorrentTorrentInfo {
        val kClass = this::class
        val props = kClass.members.filterIsInstance<kotlin.reflect.KProperty1<QBittorrentTorrentInfo, Any?>>()

        val values = props.associate { prop ->
            val newValue = prop.get(other)
            val oldValue = prop.get(this)
            prop.name to (newValue ?: oldValue)
        }

        return kClass.primaryConstructor!!.callBy(
            kClass.primaryConstructor!!.parameters.associateWith { values[it.name] }
        )
    }
}
