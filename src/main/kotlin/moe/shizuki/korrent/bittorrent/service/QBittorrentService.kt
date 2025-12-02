package moe.shizuki.korrent.bittorrent.service

import moe.shizuki.korrent.bittorrent.model.QBittorrentBuildInfo
import moe.shizuki.korrent.bittorrent.model.QBittorrentCategory
import moe.shizuki.korrent.bittorrent.model.QBittorrentCookie
import moe.shizuki.korrent.bittorrent.model.QBittorrentLog
import moe.shizuki.korrent.bittorrent.model.QBittorrentPeerLog
import moe.shizuki.korrent.bittorrent.model.QBittorrentPlugin
import moe.shizuki.korrent.bittorrent.model.QBittorrentPreference
import moe.shizuki.korrent.bittorrent.model.QBittorrentRssRule
import moe.shizuki.korrent.bittorrent.model.QBittorrentSearchId
import moe.shizuki.korrent.bittorrent.model.QBittorrentSearchResult
import moe.shizuki.korrent.bittorrent.model.QBittorrentSearchStatus
import moe.shizuki.korrent.bittorrent.model.QBittorrentSyncMainData
import moe.shizuki.korrent.bittorrent.model.QBittorrentSyncPeersData
import moe.shizuki.korrent.bittorrent.model.QBittorrentTorrentContent
import moe.shizuki.korrent.bittorrent.model.QBittorrentTorrentInfo
import moe.shizuki.korrent.bittorrent.model.QBittorrentTorrentProperty
import moe.shizuki.korrent.bittorrent.model.QBittorrentTracker
import moe.shizuki.korrent.bittorrent.model.QBittorrentTransferInfo
import moe.shizuki.korrent.bittorrent.model.QBittorrentWebSeed
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface QBittorrentService {
    @FormUrlEncoded
    @POST("/api/v2/auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    )

    @POST("/api/v2/auth/logout")
    suspend fun logout()

    @GET("/api/v2/app/version")
    suspend fun getApplicationVersion(): String

    @GET("/api/v2/app/webapiVersion")
    suspend fun getApiVersion(): String

    @GET("/api/v2/app/buildInfo")
    suspend fun getBuildInfo(): QBittorrentBuildInfo

    @POST("/api/v2/app/shutdown")
    suspend fun shutdown()

    @GET("/api/v2/app/preferences")
    suspend fun getPreferences(): QBittorrentPreference

    @FormUrlEncoded
    @POST("/api/v2/app/setPreferences")
    suspend fun setPreferences(
        @Field("json") preferences: String,
    )

    @GET("/api/v2/app/defaultSavePath")
    suspend fun getDefaultSavePath(): String

    @GET("/api/v2/app/cookies")
    suspend fun getCookies(): Array<QBittorrentCookie>

    @FormUrlEncoded
    @POST("/api/v2/app/setCookies")
    suspend fun setCookies(
        @Field("cookies") cookies: String,
    )

    @GET("/api/v2/log/main")
    suspend fun getLogs(
        @Query("normal") normal: Boolean = true,
        @Query("info") info: Boolean = true,
        @Query("warning") warning: Boolean = true,
        @Query("critical") critical: Boolean = true,
        @Query("last_known_id") lastKnownId: Int = -1,
    ): Array<QBittorrentLog>

    @GET("/api/v2/log/peers")
    suspend fun getPeerLogs(
        @Query("last_known_id") lastKnownId: Int = -1,
    ): Array<QBittorrentPeerLog>

    @GET("/api/v2/sync/maindata")
    suspend fun getSyncMainData(
        @Query("rid") rid: Int = -1,
    ): QBittorrentSyncMainData

    @GET("/api/v2/sync/torrentPeers")
    suspend fun getSyncPeersData(
        @Query("hash") hash: String,
    ): QBittorrentSyncPeersData

    @GET("/api/v2/transfer/info")
    suspend fun getGlobalTransferInfo(): QBittorrentTransferInfo

    @GET("/api/v2/transfer/speedLimitsMode")
    suspend fun getAlternativeSpeedLimitsState(): Int

    @POST("/api/v2/transfer/toggleSpeedLimitsMode")
    suspend fun toggleAlternativeSpeedLimits()

    @GET("/api/v2/transfer/downloadLimit")
    suspend fun getGlobalDownloadLimit(): Int

    @FormUrlEncoded
    @POST("/api/v2/transfer/setDownloadLimit")
    suspend fun setGlobalDownloadLimit(
        @Field("limit") limit: Int,
    )

    @GET("/api/v2/transfer/uploadLimit")
    suspend fun getGlobalUploadLimit(): Int

    @FormUrlEncoded
    @POST("/api/v2/transfer/setUploadLimit")
    suspend fun setGlobalUploadLimit(
        @Field("limit") limit: Int,
    )

    @FormUrlEncoded
    @POST("/api/v2/transfer/banPeers")
    suspend fun banPeers(
        @Field("peers") peers: String,
    )

    @GET("/api/v2/torrents/info")
    suspend fun getTorrents(
        @Query("filter") filter: String? = null,
        @Query("category") category: String? = null,
        @Query("tag") tag: String? = null,
        @Query("sort") sort: String? = null,
        @Query("reverse") reverse: Boolean? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("hashes") hashes: String? = null,
    ): Array<QBittorrentTorrentInfo>

    @GET("/api/v2/torrents/properties")
    suspend fun getTorrentGenericProperty(
        @Query("hash") hash: String,
    ): QBittorrentTorrentProperty

    @GET("/api/v2/torrents/trackers")
    suspend fun getTorrentTrackers(
        @Query("hash") hash: String,
    ): Array<QBittorrentTracker>

    @GET("/api/v2/torrents/webseeds")
    suspend fun getTorrentWebSeeds(
        @Query("hash") hash: String,
    ): Array<QBittorrentWebSeed>

    @GET("/api/v2/torrents/files")
    suspend fun getTorrentContents(
        @Query("hash") hash: String,
        @Query("indexes") indexes: String? = null,
    ): Array<QBittorrentTorrentContent>

    @GET("/api/v2/torrents/pieceStates")
    suspend fun getTorrentPiecesStates(
        @Query("hash") hash: String,
    ): Array<Int>

    @GET("/api/v2/torrents/pieceHashes")
    suspend fun getTorrentPiecesHashes(
        @Query("hash") hash: String,
    ): Array<String>

    @FormUrlEncoded
    @POST("/api/v2/torrents/stop")
    suspend fun stopTorrents(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/start")
    suspend fun startTorrents(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/delete")
    suspend fun removeTorrents(
        @Field("hashes") hashes: String,
        @Field("deleteFiles") deleteFiles: Boolean,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/recheck")
    suspend fun recheckTorrents(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/reannounce")
    suspend fun reannounceTorrents(
        @Field("hashes") hashes: String,
    )

    @Multipart
    @POST("/api/v2/torrents/add")
    suspend fun addTorrentFromFile(
        @Part torrent: MultipartBody.Part,
        @Part("savepath") savepath: String? = null,
        @Part("category") category: String? = null,
        @Part("tags") tags: String? = null,
        @Part("skip_checking") skipChecking: Boolean? = null,
        @Part("paused") paused: Boolean? = null,
        @Part("root_folder") rootFolder: Boolean? = null,
        @Part("rename") rename: String? = null,
        @Part("upLimit") upLimit: Int? = null,
        @Part("dlLimit") dlLimit: Int? = null,
        @Part("ratioLimit") ratioLimit: Float? = null,
        @Part("seedingTimeLimit") seedingTimeLimit: Int? = null,
        @Part("autoTMM") autoTMM: Boolean? = null,
        @Part("sequentialDownload") sequentialDownload: Boolean? = null,
        @Part("firstLastPiecePrio") firstLastPiecePrio: Boolean? = null,
    )

    @Multipart
    @POST("/api/v2/torrents/add")
    suspend fun addTorrentFromUrl(
        @Part("urls") urls: String,
        @Part("savepath") savepath: String? = null,
        @Part("category") category: String? = null,
        @Part("tags") tags: String? = null,
        @Part("skip_checking") skipChecking: Boolean? = null,
        @Part("paused") paused: Boolean? = null,
        @Part("root_folder") rootFolder: Boolean? = null,
        @Part("rename") rename: String? = null,
        @Part("upLimit") upLimit: Int? = null,
        @Part("dlLimit") dlLimit: Int? = null,
        @Part("ratioLimit") ratioLimit: Float? = null,
        @Part("seedingTimeLimit") seedingTimeLimit: Int? = null,
        @Part("autoTMM") autoTMM: Boolean? = null,
        @Part("sequentialDownload") sequentialDownload: Boolean? = null,
        @Part("firstLastPiecePrio") firstLastPiecePrio: Boolean? = null,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/addTrackers")
    suspend fun addTorrentTrackers(
        @Field("hash") hash: String,
        @Field("urls") urls: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/editTracker")
    suspend fun editTorrentTracker(
        @Field("hash") hash: String,
        @Field("origUrl") origUrl: String,
        @Field("newUrl") newUrl: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeTrackers")
    suspend fun removeTorrentTrackers(
        @Field("hash") hash: String,
        @Field("urls") urls: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/addPeers")
    suspend fun addTorrentPeers(
        @Field("hashes") hashes: String,
        @Field("peers") peers: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/increasePrio")
    suspend fun increaseTorrentPriority(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/decreasePrio")
    suspend fun decreaseTorrentPriority(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/topPrio")
    suspend fun maximalTorrentPriority(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/bottomPrio")
    suspend fun minimalTorrentPriority(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/filePrio")
    suspend fun setTorrentFilePriority(
        @Field("hash") hash: String,
        @Field("id") id: Int,
        @Field("priority") priority: Int,
    )

    @GET("/api/v2/torrents/downloadLimit")
    suspend fun getTorrentDownloadLimit(
        @Query("hashes") hashes: String,
    ): Map<String, Int>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setDownloadLimit")
    suspend fun setTorrentDownloadLimit(
        @Field("hashes") hashes: String,
        @Field("limit") limit: Int,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/setShareLimits")
    suspend fun setTorrentShareLimit(
        @Field("hashes") hashes: String,
        @Field("ratioLimit") ratioLimit: Float,
        @Field("seedingTimeLimit") seedingTimeLimit: Int,
        @Field("inactiveSeedingTimeLimit") inactiveSeedingTimeLimit: Int,
    )

    @GET("/api/v2/torrents/uploadLimit")
    suspend fun getTorrentUploadLimit(
        @Query("hashes") hashes: String,
    ): Map<String, Int>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setUploadLimit")
    suspend fun setTorrentUploadLimit(
        @Field("hashes") hashes: String,
        @Field("limit") limit: Int,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/setLocation")
    suspend fun setTorrentLocation(
        @Field("hashes") hashes: String,
        @Field("location") location: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/rename")
    suspend fun renameTorrent(
        @Field("hash") hash: String,
        @Field("name") name: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/setCategory")
    suspend fun setTorrentCategory(
        @Field("hashes") hashes: String,
        @Field("category") category: String,
    )

    @GET("/api/v2/torrents/categories")
    suspend fun getCategories(): Map<String, QBittorrentCategory>

    @FormUrlEncoded
    @POST("/api/v2/torrents/createCategory")
    suspend fun createCategory(
        @Field("category") category: String,
        @Field("savePath") savePath: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/editCategory")
    suspend fun editCategory(
        @Field("category") category: String,
        @Field("savePath") savePath: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeCategories")
    suspend fun removeCategories(
        @Field("categories") categories: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/addTags")
    suspend fun addTorrentTag(
        @Field("hashes") hashes: String,
        @Field("tags") tags: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeTags")
    suspend fun removeTorrentTag(
        @Field("hashes") hashes: String,
        @Field("tags") tags: String,
    )

    @GET("/api/v2/torrents/tags")
    suspend fun getTags(): Array<String>

    @FormUrlEncoded
    @POST("/api/v2/torrents/createTags")
    suspend fun createTags(
        @Field("tags") tags: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/deleteTags")
    suspend fun deleteTags(
        @Field("tags") tags: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/setAutoManagement")
    suspend fun setAutomaticTorrentManagement(
        @Field("hashes") hashes: String,
        @Field("enable") enable: Boolean,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/toggleSequentialDownload")
    suspend fun toggleSequentialDownload(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/toggleFirstLastPiecePrio")
    suspend fun toggleFirstLastPiecePriority(
        @Field("hashes") hashes: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/setForceStart")
    suspend fun setForceStart(
        @Field("hashes") hashes: String,
        @Field("value") value: Boolean,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/setSuperSeeding")
    suspend fun setSuperSeeding(
        @Field("hashes") hashes: String,
        @Field("value") value: Boolean,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/renameFile")
    suspend fun renameTorrentFile(
        @Field("hash") hash: String,
        @Field("oldPath") oldPath: String,
        @Field("newPath") newPath: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/torrents/renameFolder")
    suspend fun renameTorrentFolder(
        @Field("hash") hash: String,
        @Field("oldPath") oldPath: String,
        @Field("newPath") newPath: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/rss/addFolder")
    suspend fun addRssFolder(
        @Field("path") path: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/rss/addFeed")
    suspend fun addRssFeed(
        @Field("url") url: String,
        @Field("path") path: String? = null,
    )

    @FormUrlEncoded
    @POST("/api/v2/rss/removeItem")
    suspend fun removeRssFeed(
        @Field("path") path: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/rss/moveItem")
    suspend fun moveRssFeed(
        @Field("itemPath") itemPath: String,
        @Field("destPath") destPath: String,
    )

    @GET("/api/v2/rss/items")
    suspend fun getRssFeeds(
        @Query("withData") withData: Boolean,
    ): String

    @FormUrlEncoded
    @POST("/api/v2/rss/markAsRead")
    suspend fun markRssAsRead(
        @Field("itemPath") itemPath: String,
        @Field("articleId") articleId: String? = null,
    )

    @FormUrlEncoded
    @POST("/api/v2/rss/refreshItem")
    suspend fun refreshRssFeed(
        @Field("itemPath") itemPath: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/rss/setRule")
    suspend fun setRssRule(
        @Field("ruleName") ruleName: String,
        @Field("ruleDef") ruleDef: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/rss/renameRule")
    suspend fun renameRssRule(
        @Field("ruleName") ruleName: String,
        @Field("newRuleName") newRuleName: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/rss/removeRule")
    suspend fun removeRssRule(
        @Field("ruleName") ruleName: String,
    )

    @GET("/api/v2/rss/rules")
    suspend fun getRssRules(): Map<String, QBittorrentRssRule>

    @GET("/api/v2/rss/matchingArticles")
    suspend fun matchingRssArticles(
        @Query("ruleName") ruleName: String,
    ): Map<String, Array<String>>

    @FormUrlEncoded
    @POST("/api/v2/search/start")
    suspend fun startSearch(
        @Field("pattern") pattern: String,
        @Field("plugins") plugins: String,
        @Field("category") category: String,
    ): QBittorrentSearchId

    @FormUrlEncoded
    @POST("/api/v2/search/stop")
    suspend fun stopSearch(
        @Field("id") id: Int,
    )

    @GET("/api/v2/search/status")
    suspend fun getSearchStatus(
        @Query("id") id: Int? = null,
    ): Array<QBittorrentSearchStatus>

    @GET("/api/v2/search/results")
    suspend fun getSearchResults(
        @Query("id") id: Int,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
    ): QBittorrentSearchResult

    @FormUrlEncoded
    @POST("/api/v2/search/delete")
    suspend fun deleteSearchResult(
        @Field("id") id: Int,
    )

    @GET("/api/v2/search/plugins")
    suspend fun getSearchPlugins(): Array<QBittorrentPlugin>

    @FormUrlEncoded
    @POST("/api/v2/search/installPlugin")
    suspend fun installSearchPlugin(
        @Field("sources") sources: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/search/uninstallPlugin")
    suspend fun uninstallSearchPlugin(
        @Field("names") names: String,
    )

    @FormUrlEncoded
    @POST("/api/v2/search/enablePlugin")
    suspend fun enableSearchPlugin(
        @Field("names") names: String,
        @Field("enable") enable: Boolean,
    )

    @POST("/api/v2/search/updatePlugins")
    suspend fun updateSearchPlugins()
}
