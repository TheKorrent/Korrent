package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentPlugin(
    @field:JsonProperty("enabled") val enabled: Boolean,
    @field:JsonProperty("fullName") val fullName: String,
    @field:JsonProperty("name") val name: String,
    @field:JsonProperty("supportedCategories") val supportedCategories: List<SupportedCategory>,
    @field:JsonProperty("url") val url: String,
    @field:JsonProperty("version") val version: String,
) {
    data class SupportedCategory(
        @field:JsonProperty("id") val id: String,
        @field:JsonProperty("name") val name: String,
    )
}
