package moe.shizuki.korrent.bittorrent.client

import moe.shizuki.korrent.bittorrent.model.*
import moe.shizuki.korrent.bittorrent.service.QBittorrentService
import moe.shizuki.korrent.objectMapper
import okhttp3.JavaNetCookieJar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.net.CookieManager
import java.net.CookiePolicy

class QBittorrentClient(
    val name: String,
    val baseUrl: String,

): BitTorrentClient() {
    private val service: QBittorrentService

    override val clientInfo = BitTorrentClientInfo(BitTorrentClientType.QBITTORRENT, name, baseUrl)

    init {
        val cookieManager = CookieManager().apply {
            setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        }

        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieManager))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .client(client)
            .build()

        this.service = retrofit.create(QBittorrentService::class.java)
    }

    fun login(username: String, password: String): Call<Void> {
        return service.login(username, password)
    }

    fun logout(): Call<Void> {
        return service.logout()
    }

    fun getApplicationVersion(): Call<String> {
        return service.getApplicationVersion()
    }

    fun getApiVersion(): Call<String> {
        return service.getApiVersion()
    }

    fun getBuildInfo(): Call<QBittorrentBuildInfo> {
        return service.getBuildInfo()
    }

    fun shutdown(): Call<Void> {
        return service.shutdown()
    }

    fun getPreferences(): Call<QBittorrentPreference> {
        return service.getPreferences()
    }

    fun setPreferences(preferences: QBittorrentPreference): Call<Void> {
        return service.setPreferences(objectMapper.writeValueAsString(preferences))
    }

    fun getDefaultSavePath(): Call<String> {
        return service.getDefaultSavePath()
    }

    fun getCookies(): Call<Array<QBittorrentCookie>> {
        return service.getCookies()
    }

    fun setCookies(vararg cookies: QBittorrentCookie): Call<Void> {
        return service.setCookies(objectMapper.writeValueAsString(cookies))
    }

    fun getLogs(normal: Boolean = true, info: Boolean = true, warning: Boolean = true, critical: Boolean = true, lastKnownId: Int = -1): Call<Array<QBittorrentLog>> {
        return service.getLogs(normal, info, warning, critical, lastKnownId)
    }

    fun getPeerLogs(lastKnownId: Int = -1): Call<Array<QBittorrentPeerLog>> {
        return service.getPeerLogs(lastKnownId)
    }

    fun getSyncMainData(rid: Int = -1): Call<QBittorrentSyncMainData> {
        return service.getSyncMainData(rid)
    }

    fun getSyncPeersData(hash: String): Call<QBittorrentSyncPeersData> {
        return service.getSyncPeersData(hash)
    }

    fun getGlobalTransferInfo(): Call<QBittorrentTransferInfo> {
        return service.getGlobalTransferInfo()
    }

    fun getAlternativeSpeedLimitsState(): Call<Int> {
        return service.getAlternativeSpeedLimitsState()
    }

    fun toggleAlternativeSpeedLimits(): Call<Void> {
        return service.toggleAlternativeSpeedLimits()
    }

    fun getGlobalDownloadLimit(): Call<Int> {
        return service.getGlobalDownloadLimit()
    }

    fun setGlobalDownloadLimit(limit: Int): Call<Void> {
        return service.setGlobalDownloadLimit(limit)
    }

    fun getGlobalUploadLimit(): Call<Int> {
        return service.getGlobalUploadLimit()
    }

    fun setGlobalUploadLimit(limit: Int): Call<Void> {
        return service.setGlobalUploadLimit(limit)
    }

    fun banPeers(vararg peers: String): Call<Void> {
        return service.banPeers(peers.joinToString(separator = "|"))
    }

    fun getTorrents(
        filter: String? = null,
        category: String? = null,
        tag: String? = null,
        sort: String? = null,
        reverse: Boolean? = null,
        limit: Int? = null,
        offset: Int? = null,
        hashes: String? = null
    ): Call<Array<QBittorrentTorrentInfo>> {
        return service.getTorrents(filter, category, tag, sort, reverse, limit, offset, hashes)
    }

    fun getTorrentGenericProperty(hash: String): Call<QBittorrentTorrentProperty> {
        return service.getTorrentGenericProperty(hash)
    }

    fun getTorrentTrackers(hash: String): Call<Array<QBittorrentTracker>> {
        return service.getTorrentTrackers(hash)
    }

    fun getTorrentWebSeeds(hash: String): Call<Array<QBittorrentWebSeed>> {
        return service.getTorrentWebSeeds(hash)
    }

    fun getTorrentContents(hash: String, indexes: String? = null): Call<Array<QBittorrentTorrentContent>> {
        return service.getTorrentContents(hash, indexes)
    }

    fun getTorrentPiecesStates(hash: String): Call<Array<Int>> {
        return service.getTorrentPiecesStates(hash)
    }

    fun getTorrentPiecesHashes(hash: String): Call<Array<String>> {
        return service.getTorrentPiecesHashes(hash)
    }

    fun stopTorrents(vararg hashes: String): Call<Void> {
        return service.stopTorrents(hashes.joinToString(separator = "|"))
    }

    fun startTorrents(vararg hashes: String): Call<Void> {
        return service.startTorrents(hashes.joinToString(separator = "|"))
    }

    fun removeTorrents(vararg hashes: String, deleteFiles: Boolean): Call<Void> {
        return service.removeTorrents(hashes.joinToString(separator = "|"), deleteFiles)
    }

    fun recheckTorrents(vararg hashes: String): Call<Void> {
        return service.recheckTorrents(hashes.joinToString(separator = "|"))
    }

    fun reannounceTorrents(vararg hashes: String): Call<Void> {
        return service.reannounceTorrents(hashes.joinToString(separator = "|"))
    }

    fun addTorrent(
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
    ): Call<Void> {
        return service.addTorrentFromFile(
            MultipartBody.Part.createFormData("file", torrent.name,
                torrent.asRequestBody("multipart/form-data".toMediaTypeOrNull())
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
            firstLastPiecePrio
        )
    }

    fun addTorrent(
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
    ): Call<Void> {
        return service.addTorrentFromUrl(urls, savepath, category, tags, skipChecking, paused, rootFolder, rename, upLimit, dlLimit, ratioLimit, seedingTimeLimit, autoTMM, sequentialDownload, firstLastPiecePrio)
    }

    fun addTorrentTracker(hash: String, url: String): Call<Void> {
        return service.addTorrentTrackers(hash, url)
    }

    fun editTorrentTracker(hash: String, origUrl: String, newUrl: String): Call<Void> {
        return service.editTorrentTracker(hash, origUrl, newUrl)
    }

    fun removeTorrentTracker(hash: String, url: String): Call<Void> {
        return service.removeTorrentTrackers(hash, url)
    }

    fun addTorrentPeers(hash: String, vararg peers: String): Call<Void> {
        return service.addTorrentPeers(hash, peers.joinToString(separator = "|"))
    }

    fun increaseTorrentPriority(vararg hashes: String): Call<Void> {
        return service.increaseTorrentPriority(hashes.joinToString(separator = "|"))
    }

    fun decreaseTorrentPriority(vararg hashes: String): Call<Void> {
        return service.decreaseTorrentPriority(hashes.joinToString(separator = "|"))
    }

    fun maximalTorrentPriority(vararg hashes: String): Call<Void> {
        return service.maximalTorrentPriority(hashes.joinToString(separator = "|"))
    }

    fun minimalTorrentPriority(vararg hashes: String): Call<Void> {
        return service.minimalTorrentPriority(hashes.joinToString(separator = "|"))
    }

    fun setTorrentFilePriority(hash: String, id: Int, priority: Int): Call<Void> {
        return service.setTorrentFilePriority(hash, id, priority)
    }

    fun getTorrentDownloadLimit(vararg hashes: String): Call<Map<String, Int>> {
        return service.getTorrentDownloadLimit(hashes.joinToString(separator = "|"))
    }

    fun setTorrentDownloadLimit(vararg hashes: String, limit: Int): Call<Void> {
        return service.setTorrentDownloadLimit(hashes.joinToString(separator = "|"), limit)
    }

    fun setTorrentShareLimit(
        vararg hashes: String,
        ratioLimit: Float,
        seedingTimeLimit: Int,
        inactiveSeedingTimeLimit: Int
    ): Call<Void> {
        return service.setTorrentShareLimit(hashes.joinToString(separator = "|"), ratioLimit, seedingTimeLimit, inactiveSeedingTimeLimit)
    }

    fun getTorrentUploadLimit(vararg hashes: String): Call<Map<String, Int>> {
        return service.getTorrentUploadLimit(hashes.joinToString(separator = "|"))
    }

    fun setTorrentUploadLimit(vararg hashes: String, limit: Int): Call<Void> {
        return service.setTorrentUploadLimit(hashes.joinToString(separator = "|"), limit)
    }

    fun setTorrentLocation(vararg hashes: String, location: String): Call<Void> {
        return service.setTorrentLocation(hashes.joinToString(separator = "|"), location)
    }

    fun renameTorrent(hash: String, name: String): Call<Void> {
        return service.renameTorrent(hash, name)
    }

    fun setTorrentCategory(vararg hashes: String, category: String): Call<Void> {
        return service.setTorrentCategory(hashes.joinToString(separator = "|"), category)
    }

    fun getCategories(): Call<Map<String, QBittorrentCategory>> {
        return service.getCategories()
    }

    fun createCategory(category: String, savePath: String): Call<Void> {
        return service.createCategory(category, savePath)
    }

    fun editCategory(category: String, savePath: String): Call<Void> {
        return service.editCategory(category, savePath)
    }

    fun deleteCategories(vararg categories: String): Call<Void> {
        return service.removeCategories(categories.joinToString(separator = "\n"))
    }

    fun addTorrentTag(vararg hashes: String, tag: String): Call<Void> {
        return service.addTorrentTag(hashes.joinToString(separator = "|"), tag)
    }

    fun removeTorrentTag(vararg hashes: String, tag: String): Call<Void> {
        return service.removeTorrentTag(hashes.joinToString(separator = "|"), tag)
    }

    fun getTags(): Call<Array<String>> {
        return service.getTags()
    }

    fun createTags(vararg tags: String): Call<Void> {
        return service.createTags(tags.joinToString(separator = ","))
    }

    fun deleteTags(vararg tags: String): Call<Void> {
        return service.deleteTags(tags.joinToString(separator = ","))
    }

    fun setAutomaticTorrentManagement(vararg hashes: String, enable: Boolean): Call<Void> {
        return service.setAutomaticTorrentManagement(hashes.joinToString(separator = "|"), enable)
    }

    fun toggleSequentialDownload(vararg hashes: String): Call<Void> {
        return service.toggleSequentialDownload(hashes.joinToString(separator = "|"))
    }

    fun toggleFirstLastPiecePriority(vararg hashes: String): Call<Void> {
        return service.toggleFirstLastPiecePriority(hashes.joinToString(separator = "|"))
    }

    fun setForceStart(vararg hashes: String, enable: Boolean): Call<Void> {
        return service.setForceStart(hashes.joinToString(separator = "|"), enable)
    }

    fun setSuperSeeding(vararg hashes: String, enable: Boolean): Call<Void> {
        return service.setSuperSeeding(hashes.joinToString(separator = "|"), enable)
    }

    fun renameTorrentFile(hash: String, oldPath: String, newPath: String): Call<Void> {
        return service.renameTorrentFile(hash, oldPath, newPath)
    }

    fun renameTorrentFolder(hash: String, oldPath: String, newPath: String): Call<Void> {
        return service.renameTorrentFolder(hash, oldPath, newPath)
    }

    fun addRssFolder(path: String): Call<Void> {
        return service.addRssFolder(path)
    }

    fun addRssFeed(url: String, path: String? = null): Call<Void> {
        return service.addRssFeed(url, path)
    }

    fun removeRssFeed(path: String): Call<Void> {
        return service.removeRssFeed(path)
    }

    fun moveRssFeed(itemPath: String, destPath: String): Call<Void> {
        return service.moveRssFeed(itemPath, destPath)
    }

    fun getRssFeeds(withData: Boolean): Call<String> {
        return service.getRssFeeds(withData)
    }

    fun markRssAsRead(itemPath: String, articleId: String? = null): Call<Void> {
        return service.markRssAsRead(itemPath, articleId)
    }

    fun refreshRssFeed(itemPath: String): Call<Void> {
        return service.refreshRssFeed(itemPath)
    }

    fun setRssRule(ruleName: String, ruleDef: QBittorrentRssRule): Call<Void> {
        return service.setRssRule(ruleName, objectMapper.writeValueAsString(ruleDef))
    }

    fun renameRssRule(ruleName: String, newRuleName: String): Call<Void> {
        return service.renameRssRule(ruleName, newRuleName)
    }

    fun removeRssRule(ruleName: String): Call<Void> {
        return service.removeRssRule(ruleName)
    }

    fun getRssRules(): Call<Map<String, QBittorrentRssRule>> {
        return service.getRssRules()
    }

    fun matchingRssArticles(ruleName: String): Call<Map<String, Array<String>>> {
        return service.matchingRssArticles(ruleName)
    }

    fun startSearch(pattern: String, plugins: String, category: String): Call<QBittorrentSearchId> {
        return service.startSearch(pattern, plugins, category)
    }

    fun stopSearch(id: Int): Call<Void> {
        return service.stopSearch(id)
    }

    fun getSearchStatus(id: Int? = null): Call<Array<QBittorrentSearchStatus>> {
        return service.getSearchStatus(id)
    }

    fun getSearchResults(id: Int, limit: Int? = null, offset: Int? = null): Call<QBittorrentSearchResult> {
        return service.getSearchResults(id, limit, offset)
    }

    fun deleteSearchResult(id: Int): Call<Void> {
        return service.deleteSearchResult(id)
    }

    fun getSearchPlugins(): Call<Array<QBittorrentPlugin>> {
        return service.getSearchPlugins()
    }

    fun installSearchPlugin(sources: String): Call<Void> {
        return service.installSearchPlugin(sources)
    }

    fun uninstallSearchPlugin(names: String): Call<Void> {
        return service.uninstallSearchPlugin(names)
    }

    fun enableSearchPlugin(names: String, enable: Boolean): Call<Void> {
        return service.enableSearchPlugin(names, enable)
    }

    fun updateSearchPlugins(): Call<Void> {
        return service.updateSearchPlugins()
    }
}
