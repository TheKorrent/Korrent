package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentRssRule(
    @field:JsonProperty("addPaused") val addPaused: Boolean? = null,
    @field:JsonProperty("affectedFeeds") val affectedFeeds: List<Any?>? = null,
    @field:JsonProperty("assignedCategory") val assignedCategory: String? = null,
    @field:JsonProperty("enabled") val enabled: Boolean? = null,
    @field:JsonProperty("episodeFilter") val episodeFilter: String? = null,
    @field:JsonProperty("ignoreDays") val ignoreDays: Int? = null,
    @field:JsonProperty("lastMatch") val lastMatch: String? = null,
    @field:JsonProperty("mustContain") val mustContain: String? = null,
    @field:JsonProperty("mustNotContain") val mustNotContain: String? = null,
    @field:JsonProperty("previouslyMatchedEpisodes") val previouslyMatchedEpisodes: List<Any?>? = null,
    @field:JsonProperty("priority") val priority: Int? = null,
    @field:JsonProperty("savePath") val savePath: String? = null,
    @field:JsonProperty("smartFilter") val smartFilter: Boolean? = null,
    @field:JsonProperty("torrentContentLayout") val torrentContentLayout: Any? = null,
    @field:JsonProperty("torrentParams") val torrentParams: TorrentParams? = null,
    @field:JsonProperty("useRegex") val useRegex: Boolean? = null
) {
    data class TorrentParams(
        @field:JsonProperty("category") val category: String? = null,
        @field:JsonProperty("download_limit") val downloadLimit: Int? = null,
        @field:JsonProperty("download_path") val downloadPath: String? = null,
        @field:JsonProperty("inactive_seeding_time_limit") val inactiveSeedingTimeLimit: Int? = null,
        @field:JsonProperty("operating_mode") val operatingMode: String? = null,
        @field:JsonProperty("ratio_limit") val ratioLimit: Int? = null,
        @field:JsonProperty("save_path") val savePath: String? = null,
        @field:JsonProperty("seeding_time_limit") val seedingTimeLimit: Int? = null,
        @field:JsonProperty("share_limit_action") val shareLimitAction: String? = null,
        @field:JsonProperty("skip_checking") val skipChecking: Boolean? = null,
        @field:JsonProperty("ssl_certificate") val sslCertificate: String? = null,
        @field:JsonProperty("ssl_dh_params") val sslDhParams: String? = null,
        @field:JsonProperty("ssl_private_key") val sslPrivateKey: String? = null,
        @field:JsonProperty("tags") val tags: List<String>? = null,
        @field:JsonProperty("upload_limit") val uploadLimit: Int? = null
    )
}
