package moe.shizuki.korrent.bittorrent.service.call

import moe.shizuki.korrent.bittorrent.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface QBittorrentService {
    @FormUrlEncoded
    @POST("/api/v2/auth/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<Void>

    @POST("/api/v2/auth/logout")
    fun logout(): Call<Void>

    @GET("/api/v2/app/version")
    fun getApplicationVersion(): Call<String>

    @GET("/api/v2/app/webapiVersion")
    fun getApiVersion(): Call<String>

    @GET("/api/v2/app/buildInfo")
    fun getBuildInfo(): Call<QBittorrentBuildInfo>

    @POST("/api/v2/app/shutdown")
    fun shutdown(): Call<Void>

    @GET("/api/v2/app/preferences")
    fun getPreferences(): Call<QBittorrentPreference>

    @FormUrlEncoded
    @POST("/api/v2/app/setPreferences")
    fun setPreferences(@Field("json") preferences: String): Call<Void>

    @GET("/api/v2/app/defaultSavePath")
    fun getDefaultSavePath(): Call<String>

    @GET("/api/v2/app/cookies")
    fun getCookies(): Call<Array<QBittorrentCookie>>

    @FormUrlEncoded
    @POST("/api/v2/app/setCookies")
    fun setCookies(@Field("cookies") cookies: String): Call<Void>

    @GET("/api/v2/log/main")
    fun getLogs(
        @Query("normal") normal: Boolean = true,
        @Query("info") info: Boolean = true,
        @Query("warning") warning: Boolean = true,
        @Query("critical") critical: Boolean = true,
        @Query("last_known_id") lastKnownId: Int = -1
    ): Call<Array<QBittorrentLog>>

    @GET("/api/v2/log/peers")
    fun getPeerLogs(@Query("last_known_id") lastKnownId: Int = -1): Call<Array<QBittorrentPeerLog>>

    @GET("/api/v2/sync/maindata")
    fun getSyncMainData(@Query("rid") rid: Int = -1): Call<QBittorrentSyncMainData>

    @GET("/api/v2/sync/torrentPeers")
    fun getSyncPeersData(@Query("hash") hash: String): Call<QBittorrentSyncPeersData>

    @GET("/api/v2/transfer/info")
    fun getGlobalTransferInfo(): Call<QBittorrentTransferInfo>

    @GET("/api/v2/transfer/speedLimitsMode")
    fun getAlternativeSpeedLimitsState(): Call<Int>

    @POST("/api/v2/transfer/toggleSpeedLimitsMode")
    fun toggleAlternativeSpeedLimits(): Call<Void>

    @GET("/api/v2/transfer/downloadLimit")
    fun getGlobalDownloadLimit(): Call<Int>

    @FormUrlEncoded
    @POST("/api/v2/transfer/setDownloadLimit")
    fun setGlobalDownloadLimit(@Field("limit") limit: Int): Call<Void>

    @GET("/api/v2/transfer/uploadLimit")
    fun getGlobalUploadLimit(): Call<Int>

    @FormUrlEncoded
    @POST("/api/v2/transfer/setUploadLimit")
    fun setGlobalUploadLimit(@Field("limit") limit: Int): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/transfer/banPeers")
    fun banPeers(@Field("peers") peers: String): Call<Void>

    @GET("/api/v2/torrents/info")
    fun getTorrents(
        @Query("filter") filter: String? = null,
        @Query("category") category: String? = null,
        @Query("tag") tag: String? = null,
        @Query("sort") sort: String? = null,
        @Query("reverse") reverse: Boolean? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("hashes") hashes: String? = null
    ): Call<Array<QBittorrentTorrentInfo>>

    @GET("/api/v2/torrents/properties")
    fun getTorrentGenericProperty(@Query("hash") hash: String): Call<QBittorrentTorrentProperty>

    @GET("/api/v2/torrents/trackers")
    fun getTorrentTrackers(@Query("hash") hash: String): Call<Array<QBittorrentTracker>>

    @GET("/api/v2/torrents/webseeds")
    fun getTorrentWebSeeds(@Query("hash") hash: String): Call<Array<QBittorrentWebSeed>>

    @GET("/api/v2/torrents/files")
    fun getTorrentContents(@Query("hash") hash: String, @Query("indexes") indexes: String? = null): Call<Array<QBittorrentTorrentContent>>

    @GET("/api/v2/torrents/pieceStates")
    fun getTorrentPiecesStates(@Query("hash") hash: String): Call<Array<Int>>

    @GET("/api/v2/torrents/pieceHashes")
    fun getTorrentPiecesHashes(@Query("hash") hash: String): Call<Array<String>>

    @FormUrlEncoded
    @POST("/api/v2/torrents/stop")
    fun stopTorrents(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/start")
    fun startTorrents(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/delete")
    fun removeTorrents(@Field("hashes") hashes: String, @Field("deleteFiles") deleteFiles: Boolean): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/recheck")
    fun recheckTorrents(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/reannounce")
    fun reannounceTorrents(@Field("hashes") hashes: String): Call<Void>

    @Multipart
    @POST("/api/v2/torrents/add")
    fun addTorrentFromFile(
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
    ): Call<Void>

    @Multipart
    @POST("/api/v2/torrents/add")
    fun addTorrentFromUrl(
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
    ): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/addTrackers")
    fun addTorrentTrackers(@Field("hash") hash: String, @Field("urls") urls: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/editTracker")
    fun editTorrentTracker(@Field("hash") hash: String, @Field("origUrl") origUrl: String, @Field("newUrl") newUrl: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeTrackers")
    fun removeTorrentTrackers(@Field("hash") hash: String, @Field("urls") urls: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/addPeers")
    fun addTorrentPeers(@Field("hashes") hashes: String, @Field("peers") peers: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/increasePrio")
    fun increaseTorrentPriority(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/decreasePrio")
    fun decreaseTorrentPriority(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/topPrio")
    fun maximalTorrentPriority(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/bottomPrio")
    fun minimalTorrentPriority(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/filePrio")
    fun setTorrentFilePriority(@Field("hash") hash: String, @Field("id") id: Int, @Field("priority") priority: Int): Call<Void>

    @GET("/api/v2/torrents/downloadLimit")
    fun getTorrentDownloadLimit(@Query("hashes") hashes: String): Call<Map<String, Int>>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setDownloadLimit")
    fun setTorrentDownloadLimit(@Field("hashes") hashes: String, @Field("limit") limit: Int): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setShareLimits")
    fun setTorrentShareLimit(
        @Field("hashes") hashes: String,
        @Field("ratioLimit") ratioLimit: Float,
        @Field("seedingTimeLimit") seedingTimeLimit: Int,
        @Field("inactiveSeedingTimeLimit") inactiveSeedingTimeLimit: Int
    ): Call<Void>

    @GET("/api/v2/torrents/uploadLimit")
    fun getTorrentUploadLimit(@Query("hashes") hashes: String): Call<Map<String, Int>>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setUploadLimit")
    fun setTorrentUploadLimit(@Field("hashes") hashes: String, @Field("limit") limit: Int): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setLocation")
    fun setTorrentLocation(@Field("hashes") hashes: String, @Field("location") location: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/rename")
    fun renameTorrent(@Field("hash") hash: String, @Field("name") name: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setCategory")
    fun setTorrentCategory(@Field("hashes") hashes: String, @Field("category") category: String): Call<Void>

    @GET("/api/v2/torrents/categories")
    fun getCategories(): Call<Map<String, QBittorrentCategory>>

    @FormUrlEncoded
    @POST("/api/v2/torrents/createCategory")
    fun createCategory(@Field("category") category: String, @Field("savePath") savePath: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/editCategory")
    fun editCategory(@Field("category") category: String, @Field("savePath") savePath: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeCategories")
    fun removeCategories(@Field("categories") categories: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/addTags")
    fun addTorrentTag(@Field("hashes") hashes: String, @Field("tags") tags: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeTags")
    fun removeTorrentTag(@Field("hashes") hashes: String, @Field("tags") tags: String): Call<Void>

    @GET("/api/v2/torrents/tags")
    fun getTags(): Call<Array<String>>

    @FormUrlEncoded
    @POST("/api/v2/torrents/createTags")
    fun createTags(@Field("tags") tags: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/deleteTags")
    fun deleteTags(@Field("tags") tags: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setAutoManagement")
    fun setAutomaticTorrentManagement(@Field("hashes") hashes: String, @Field("enable") enable: Boolean): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/toggleSequentialDownload")
    fun toggleSequentialDownload(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/toggleFirstLastPiecePrio")
    fun toggleFirstLastPiecePriority(@Field("hashes") hashes: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setForceStart")
    fun setForceStart(@Field("hashes") hashes: String, @Field("value") value: Boolean): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setSuperSeeding")
    fun setSuperSeeding(@Field("hashes") hashes: String, @Field("value") value: Boolean): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/renameFile")
    fun renameTorrentFile(@Field("hash") hash: String, @Field("oldPath") oldPath: String, @Field("newPath") newPath: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/renameFolder")
    fun renameTorrentFolder(@Field("hash") hash: String, @Field("oldPath") oldPath: String, @Field("newPath") newPath: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/rss/addFolder")
    fun addRssFolder(@Field("path") path: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/rss/addFeed")
    fun addRssFeed(@Field("url") url: String, @Field("path") path: String? = null): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/rss/removeItem")
    fun removeRssFeed(@Field("path") path: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/rss/moveItem")
    fun moveRssFeed(@Field("itemPath") itemPath: String, @Field("destPath") destPath: String): Call<Void>

    @GET("/api/v2/rss/items")
    fun getRssFeeds(@Query("withData") withData: Boolean): Call<String>

    @FormUrlEncoded
    @POST("/api/v2/rss/markAsRead")
    fun markRssAsRead(@Field("itemPath") itemPath: String, @Field("articleId") articleId: String? = null): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/rss/refreshItem")
    fun refreshRssFeed(@Field("itemPath") itemPath: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/rss/setRule")
    fun setRssRule(@Field("ruleName") ruleName: String, @Field("ruleDef") ruleDef: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/rss/renameRule")
    fun renameRssRule(@Field("ruleName") ruleName: String, @Field("newRuleName") newRuleName: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/rss/removeRule")
    fun removeRssRule(@Field("ruleName") ruleName: String): Call<Void>

    @GET("/api/v2/rss/rules")
    fun getRssRules(): Call<Map<String, QBittorrentRssRule>>

    @GET("/api/v2/rss/matchingArticles")
    fun matchingRssArticles(@Query("ruleName") ruleName: String): Call<Map<String, Array<String>>>

    @FormUrlEncoded
    @POST("/api/v2/search/start")
    fun startSearch(@Field("pattern") pattern: String, @Field("plugins") plugins: String, @Field("category") category: String): Call<QBittorrentSearchId>

    @FormUrlEncoded
    @POST("/api/v2/search/stop")
    fun stopSearch(@Field("id") id: Int): Call<Void>

    @GET("/api/v2/search/status")
    fun getSearchStatus(@Query("id") id: Int? = null): Call<Array<QBittorrentSearchStatus>>

    @GET("/api/v2/search/results")
    fun getSearchResults(@Query("id") id: Int, @Query("limit") limit: Int? = null, @Query("offset") offset: Int? = null): Call<QBittorrentSearchResult>

    @FormUrlEncoded
    @POST("/api/v2/search/delete")
    fun deleteSearchResult(@Field("id") id: Int): Call<Void>

    @GET("/api/v2/search/plugins")
    fun getSearchPlugins(): Call<Array<QBittorrentPlugin>>

    @FormUrlEncoded
    @POST("/api/v2/search/installPlugin")
    fun installSearchPlugin(@Field("sources") sources: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/search/uninstallPlugin")
    fun uninstallSearchPlugin(@Field("names") names: String): Call<Void>

    @FormUrlEncoded
    @POST("/api/v2/search/enablePlugin")
    fun enableSearchPlugin(@Field("names") names: String, @Field("enable") enable: Boolean): Call<Void>

    @POST("/api/v2/search/updatePlugins")
    fun updateSearchPlugins(): Call<Void>
}
