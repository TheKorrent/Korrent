package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.bittorrent.config.QBittorrentConfig
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientType
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
import moe.shizuki.korrent.bittorrent.service.QBittorrentService
import moe.shizuki.korrent.objectMapper
import okhttp3.JavaNetCookieJar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.InetSocketAddress
import java.net.Proxy

class QBittorrentClient(
    override val config: QBittorrentConfig,
) : BitTorrentClient() {
    private val service: QBittorrentService

    override val clientType = BitTorrentClientType.QBITTORRENT

    init {
        val cookieManager =
            CookieManager().apply {
                setCookiePolicy(CookiePolicy.ACCEPT_ALL)
            }

        val clientBuilder =
            OkHttpClient.Builder().apply {
                cookieJar(JavaNetCookieJar(cookieManager))

                if (config.common.proxy.enabled) {
                    proxy(Proxy(config.common.proxy.type, InetSocketAddress(config.common.proxy.host, config.common.proxy.port)))
                }
            }

        val retrofit =
            Retrofit
                .Builder()
                .baseUrl(config.common.baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(clientBuilder.build())
                .build()

        this.service = retrofit.create(QBittorrentService::class.java)
    }

    suspend fun login(
        username: String,
        password: String,
    ) = service.login(username, password)

    suspend fun logout() = service.logout()

    suspend fun getApplicationVersion(): String = service.getApplicationVersion()

    suspend fun getApiVersion(): String = service.getApiVersion()

    suspend fun getBuildInfo(): QBittorrentBuildInfo = service.getBuildInfo()

    suspend fun shutdown() = service.shutdown()

    suspend fun getPreferences(): QBittorrentPreference = service.getPreferences()

    suspend fun setPreferences(preferences: QBittorrentPreference) = service.setPreferences(objectMapper.writeValueAsString(preferences))

    suspend fun getDefaultSavePath(): String = service.getDefaultSavePath()

    suspend fun getCookies(): Array<QBittorrentCookie> = service.getCookies()

    suspend fun setCookies(vararg cookies: QBittorrentCookie) = service.setCookies(objectMapper.writeValueAsString(cookies))

    suspend fun getLogs(
        normal: Boolean = true,
        info: Boolean = true,
        warning: Boolean = true,
        critical: Boolean = true,
        lastKnownId: Int = -1,
    ): Array<QBittorrentLog> = service.getLogs(normal, info, warning, critical, lastKnownId)

    suspend fun getPeerLogs(lastKnownId: Int = -1): Array<QBittorrentPeerLog> = service.getPeerLogs(lastKnownId)

    suspend fun getSyncMainData(rid: Int = -1): QBittorrentSyncMainData = service.getSyncMainData(rid)

    suspend fun getSyncPeersData(hash: String): QBittorrentSyncPeersData = service.getSyncPeersData(hash)

    suspend fun getGlobalTransferInfo(): QBittorrentTransferInfo = service.getGlobalTransferInfo()

    suspend fun getAlternativeSpeedLimitsState(): Int = service.getAlternativeSpeedLimitsState()

    suspend fun toggleAlternativeSpeedLimits() = service.toggleAlternativeSpeedLimits()

    suspend fun getGlobalDownloadLimit(): Int = service.getGlobalDownloadLimit()

    suspend fun setGlobalDownloadLimit(limit: Int) = service.setGlobalDownloadLimit(limit)

    suspend fun getGlobalUploadLimit(): Int = service.getGlobalUploadLimit()

    suspend fun setGlobalUploadLimit(limit: Int) = service.setGlobalUploadLimit(limit)

    suspend fun banPeers(vararg peers: String) = service.banPeers(peers.joinToString(separator = "|"))

    suspend fun getTorrents(
        filter: String? = null,
        category: String? = null,
        tag: String? = null,
        sort: String? = null,
        reverse: Boolean? = null,
        limit: Int? = null,
        offset: Int? = null,
        hashes: String? = null,
    ): Array<QBittorrentTorrentInfo> = service.getTorrents(filter, category, tag, sort, reverse, limit, offset, hashes)

    suspend fun getTorrentGenericProperty(hash: String): QBittorrentTorrentProperty = service.getTorrentGenericProperty(hash)

    suspend fun getTorrentTrackers(hash: String): Array<QBittorrentTracker> = service.getTorrentTrackers(hash)

    suspend fun getTorrentWebSeeds(hash: String): Array<QBittorrentWebSeed> = service.getTorrentWebSeeds(hash)

    suspend fun getTorrentContents(
        hash: String,
        indexes: String? = null,
    ): Array<QBittorrentTorrentContent> = service.getTorrentContents(hash, indexes)

    suspend fun getTorrentPiecesStates(hash: String): Array<Int> = service.getTorrentPiecesStates(hash)

    suspend fun getTorrentPiecesHashes(hash: String): Array<String> = service.getTorrentPiecesHashes(hash)

    suspend fun stopTorrents(vararg hashes: String) = service.stopTorrents(hashes.joinToString(separator = "|"))

    suspend fun startTorrents(vararg hashes: String) = service.startTorrents(hashes.joinToString(separator = "|"))

    suspend fun removeTorrents(
        vararg hashes: String,
        deleteFiles: Boolean,
    ) = service.removeTorrents(hashes.joinToString(separator = "|"), deleteFiles)

    suspend fun recheckTorrents(vararg hashes: String) = service.recheckTorrents(hashes.joinToString(separator = "|"))

    suspend fun reannounceTorrents(vararg hashes: String) = service.reannounceTorrents(hashes.joinToString(separator = "|"))

    suspend fun addTorrent(
        torrent: File,
        savepath: String? = null,
        category: String? = null,
        tags: String? = null,
        skipChecking: Boolean? = null,
        paused: Boolean? = null,
        rootFolder: Boolean? = null,
        rename: String? = null,
        upLimit: Int? = null,
        dlLimit: Int? = null,
        ratioLimit: Float? = null,
        seedingTimeLimit: Int? = null,
        autoTMM: Boolean? = null,
        sequentialDownload: Boolean? = null,
        firstLastPiecePrio: Boolean? = null,
    ) = service.addTorrentFromFile(
        MultipartBody.Part.createFormData(
            "file",
            torrent.name,
            torrent.asRequestBody("multipart/form-data".toMediaTypeOrNull()),
        ),
        savepath,
        category,
        tags,
        skipChecking,
        paused,
        rootFolder,
        rename,
        upLimit,
        dlLimit,
        ratioLimit,
        seedingTimeLimit,
        autoTMM,
        sequentialDownload,
        firstLastPiecePrio,
    )

    suspend fun addTorrent(
        urls: String,
        savepath: String? = null,
        category: String? = null,
        tags: String? = null,
        skipChecking: Boolean? = null,
        paused: Boolean? = null,
        rootFolder: Boolean? = null,
        rename: String? = null,
        upLimit: Int? = null,
        dlLimit: Int? = null,
        ratioLimit: Float? = null,
        seedingTimeLimit: Int? = null,
        autoTMM: Boolean? = null,
        sequentialDownload: Boolean? = null,
        firstLastPiecePrio: Boolean? = null,
    ) = service.addTorrentFromUrl(
        urls,
        savepath,
        category,
        tags,
        skipChecking,
        paused,
        rootFolder,
        rename,
        upLimit,
        dlLimit,
        ratioLimit,
        seedingTimeLimit,
        autoTMM,
        sequentialDownload,
        firstLastPiecePrio,
    )

    suspend fun addTorrentTracker(
        hash: String,
        url: String,
    ) = service.addTorrentTrackers(hash, url)

    suspend fun editTorrentTracker(
        hash: String,
        origUrl: String,
        newUrl: String,
    ) = service.editTorrentTracker(hash, origUrl, newUrl)

    suspend fun removeTorrentTracker(
        hash: String,
        url: String,
    ) = service.removeTorrentTrackers(hash, url)

    suspend fun addTorrentPeers(
        hash: String,
        vararg peers: String,
    ) = service.addTorrentPeers(hash, peers.joinToString(separator = "|"))

    suspend fun increaseTorrentPriority(vararg hashes: String) = service.increaseTorrentPriority(hashes.joinToString(separator = "|"))

    suspend fun decreaseTorrentPriority(vararg hashes: String) = service.decreaseTorrentPriority(hashes.joinToString(separator = "|"))

    suspend fun maximalTorrentPriority(vararg hashes: String) = service.maximalTorrentPriority(hashes.joinToString(separator = "|"))

    suspend fun minimalTorrentPriority(vararg hashes: String) = service.minimalTorrentPriority(hashes.joinToString(separator = "|"))

    suspend fun setTorrentFilePriority(
        hash: String,
        id: Int,
        priority: Int,
    ) = service.setTorrentFilePriority(hash, id, priority)

    suspend fun getTorrentDownloadLimit(vararg hashes: String): Map<String, Int> =
        service.getTorrentDownloadLimit(hashes.joinToString(separator = "|"))

    suspend fun setTorrentDownloadLimit(
        vararg hashes: String,
        limit: Int,
    ) = service.setTorrentDownloadLimit(hashes.joinToString(separator = "|"), limit)

    suspend fun setTorrentShareLimit(
        vararg hashes: String,
        ratioLimit: Float,
        seedingTimeLimit: Int,
        inactiveSeedingTimeLimit: Int,
    ) = service.setTorrentShareLimit(hashes.joinToString(separator = "|"), ratioLimit, seedingTimeLimit, inactiveSeedingTimeLimit)

    suspend fun getTorrentUploadLimit(vararg hashes: String): Map<String, Int> =
        service.getTorrentUploadLimit(hashes.joinToString(separator = "|"))

    suspend fun setTorrentUploadLimit(
        vararg hashes: String,
        limit: Int,
    ) = service.setTorrentUploadLimit(hashes.joinToString(separator = "|"), limit)

    suspend fun setTorrentLocation(
        vararg hashes: String,
        location: String,
    ) = service.setTorrentLocation(hashes.joinToString(separator = "|"), location)

    suspend fun renameTorrent(
        hash: String,
        name: String,
    ) = service.renameTorrent(hash, name)

    suspend fun setTorrentCategory(
        vararg hashes: String,
        category: String,
    ) = service.setTorrentCategory(hashes.joinToString(separator = "|"), category)

    suspend fun getCategories(): Map<String, QBittorrentCategory> = service.getCategories()

    suspend fun createCategory(
        category: String,
        savePath: String,
    ) = service.createCategory(category, savePath)

    suspend fun editCategory(
        category: String,
        savePath: String,
    ) = service.editCategory(category, savePath)

    suspend fun deleteCategories(vararg categories: String) = service.removeCategories(categories.joinToString(separator = "\n"))

    suspend fun addTorrentTag(
        vararg hashes: String,
        tag: String,
    ) = service.addTorrentTag(hashes.joinToString(separator = "|"), tag)

    suspend fun removeTorrentTag(
        vararg hashes: String,
        tag: String,
    ) = service.removeTorrentTag(hashes.joinToString(separator = "|"), tag)

    suspend fun getTags(): Array<String> = service.getTags()

    suspend fun createTags(vararg tags: String) = service.createTags(tags.joinToString(separator = ","))

    suspend fun deleteTags(vararg tags: String) = service.deleteTags(tags.joinToString(separator = ","))

    suspend fun setAutomaticTorrentManagement(
        vararg hashes: String,
        enable: Boolean,
    ) = service.setAutomaticTorrentManagement(hashes.joinToString(separator = "|"), enable)

    suspend fun toggleSequentialDownload(vararg hashes: String) = service.toggleSequentialDownload(hashes.joinToString(separator = "|"))

    suspend fun toggleFirstLastPiecePriority(vararg hashes: String) =
        service.toggleFirstLastPiecePriority(hashes.joinToString(separator = "|"))

    suspend fun setForceStart(
        vararg hashes: String,
        enable: Boolean,
    ) = service.setForceStart(hashes.joinToString(separator = "|"), enable)

    suspend fun setSuperSeeding(
        vararg hashes: String,
        enable: Boolean,
    ) = service.setSuperSeeding(hashes.joinToString(separator = "|"), enable)

    suspend fun renameTorrentFile(
        hash: String,
        oldPath: String,
        newPath: String,
    ) = service.renameTorrentFile(hash, oldPath, newPath)

    suspend fun renameTorrentFolder(
        hash: String,
        oldPath: String,
        newPath: String,
    ) = service.renameTorrentFolder(hash, oldPath, newPath)

    suspend fun addRssFolder(path: String) = service.addRssFolder(path)

    suspend fun addRssFeed(
        url: String,
        path: String? = null,
    ) = service.addRssFeed(url, path)

    suspend fun removeRssFeed(path: String) = service.removeRssFeed(path)

    suspend fun moveRssFeed(
        itemPath: String,
        destPath: String,
    ) = service.moveRssFeed(itemPath, destPath)

    suspend fun getRssFeeds(withData: Boolean): String = service.getRssFeeds(withData)

    suspend fun markRssAsRead(
        itemPath: String,
        articleId: String? = null,
    ) = service.markRssAsRead(itemPath, articleId)

    suspend fun refreshRssFeed(itemPath: String) = service.refreshRssFeed(itemPath)

    suspend fun setRssRule(
        ruleName: String,
        ruleDef: QBittorrentRssRule,
    ) = service.setRssRule(ruleName, objectMapper.writeValueAsString(ruleDef))

    suspend fun renameRssRule(
        ruleName: String,
        newRuleName: String,
    ) = service.renameRssRule(ruleName, newRuleName)

    suspend fun removeRssRule(ruleName: String) = service.removeRssRule(ruleName)

    suspend fun getRssRules(): Map<String, QBittorrentRssRule> = service.getRssRules()

    suspend fun matchingRssArticles(ruleName: String): Map<String, Array<String>> = service.matchingRssArticles(ruleName)

    suspend fun startSearch(
        pattern: String,
        plugins: String,
        category: String,
    ): QBittorrentSearchId = service.startSearch(pattern, plugins, category)

    suspend fun stopSearch(id: Int) = service.stopSearch(id)

    suspend fun getSearchStatus(id: Int? = null): Array<QBittorrentSearchStatus> = service.getSearchStatus(id)

    suspend fun getSearchResults(
        id: Int,
        limit: Int? = null,
        offset: Int? = null,
    ): QBittorrentSearchResult = service.getSearchResults(id, limit, offset)

    suspend fun deleteSearchResult(id: Int) = service.deleteSearchResult(id)

    suspend fun getSearchPlugins(): Array<QBittorrentPlugin> = service.getSearchPlugins()

    suspend fun installSearchPlugin(sources: String) = service.installSearchPlugin(sources)

    suspend fun uninstallSearchPlugin(names: String) = service.uninstallSearchPlugin(names)

    suspend fun enableSearchPlugin(
        names: String,
        enable: Boolean,
    ) = service.enableSearchPlugin(names, enable)

    suspend fun updateSearchPlugins() = service.updateSearchPlugins()
}
