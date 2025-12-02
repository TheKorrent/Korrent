package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class QBittorrentSearchResult(
    @field:JsonProperty("results") val results: List<Result>,
    @field:JsonProperty("status") val status: String,
    @field:JsonProperty("total") val total: Int,
) {
    data class Result(
        @field:JsonProperty("descrLink") val descrLink: String,
        @field:JsonProperty("engineName") val engineName: String,
        @field:JsonProperty("fileName") val fileName: String,
        @field:JsonProperty("fileSize") val fileSize: Long,
        @field:JsonProperty("fileUrl") val fileUrl: String,
        @field:JsonProperty("nbLeechers") val nbLeechers: Int,
        @field:JsonProperty("nbSeeders") val nbSeeders: Int,
        @field:JsonProperty("pubDate") val pubDate: Long,
        @field:JsonProperty("siteUrl") val siteUrl: String,
    )
}
