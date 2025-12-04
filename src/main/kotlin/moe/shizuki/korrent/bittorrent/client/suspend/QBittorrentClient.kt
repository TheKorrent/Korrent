package moe.shizuki.korrent.bittorrent.client.suspend

import moe.shizuki.korrent.bittorrent.client.BitTorrentClient
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
import moe.shizuki.korrent.bittorrent.service.suspend.QBittorrentService
import moe.shizuki.korrent.objectMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class QBittorrentClient(
    override val config: QBittorrentConfig,
    private val service: QBittorrentService
): BitTorrentClient() {
    override val clientType = BitTorrentClientType.QBITTORRENT

    suspend fun login(username: String, password: String) {
        return service.login(username, password)
    }

    suspend fun logout() {
        return service.logout()
    }

    suspend fun getApplicationVersion(): String {
        return service.getApplicationVersion()
    }

    suspend fun getApiVersion(): String {
        return service.getApiVersion()
    }

    suspend fun getBuildInfo(): QBittorrentBuildInfo {
        return service.getBuildInfo()
    }

    suspend fun shutdown() {
        return service.shutdown()
    }

    suspend fun getPreferences(): QBittorrentPreference {
        return service.getPreferences()
    }

    suspend fun setPreferences(preferences: QBittorrentPreference) {
        return service.setPreferences(objectMapper.writeValueAsString(preferences))
    }

    suspend fun getDefaultSavePath(): String {
        return service.getDefaultSavePath()
    }

    suspend fun getCookies(): Array<QBittorrentCookie> {
        return service.getCookies()
    }

    suspend fun setCookies(vararg cookies: QBittorrentCookie) {
        return service.setCookies(objectMapper.writeValueAsString(cookies))
    }

    suspend fun getLogs(normal: Boolean = true, info: Boolean = true, warning: Boolean = true, critical: Boolean = true, lastKnownId: Int = -1): Array<QBittorrentLog> {
        return service.getLogs(normal, info, warning, critical, lastKnownId)
    }

    suspend fun getPeerLogs(lastKnownId: Int = -1): Array<QBittorrentPeerLog> {
        return service.getPeerLogs(lastKnownId)
    }

    suspend fun getSyncMainData(rid: Int = -1): QBittorrentSyncMainData {
        return service.getSyncMainData(rid)
    }

    suspend fun getSyncPeersData(hash: String): QBittorrentSyncPeersData {
        return service.getSyncPeersData(hash)
    }

    suspend fun getGlobalTransferInfo(): QBittorrentTransferInfo {
        return service.getGlobalTransferInfo()
    }

    suspend fun getAlternativeSpeedLimitsState(): Int {
        return service.getAlternativeSpeedLimitsState()
    }

    suspend fun toggleAlternativeSpeedLimits() {
        return service.toggleAlternativeSpeedLimits()
    }

    suspend fun getGlobalDownloadLimit(): Int {
        return service.getGlobalDownloadLimit()
    }

    suspend fun setGlobalDownloadLimit(limit: Int) {
        return service.setGlobalDownloadLimit(limit)
    }

    suspend fun getGlobalUploadLimit(): Int {
        return service.getGlobalUploadLimit()
    }

    suspend fun setGlobalUploadLimit(limit: Int) {
        return service.setGlobalUploadLimit(limit)
    }

    suspend fun banPeers(vararg peers: String) {
        return service.banPeers(peers.joinToString(separator = "|"))
    }

    suspend fun getTorrents(
        filter: String? = null,
        category: String? = null,
        tag: String? = null,
        sort: String? = null,
        reverse: Boolean? = null,
        limit: Int? = null,
        offset: Int? = null,
        hashes: String? = null
    ): Array<QBittorrentTorrentInfo> {
        return service.getTorrents(filter, category, tag, sort, reverse, limit, offset, hashes)
    }

    suspend fun getTorrentGenericProperty(hash: String): QBittorrentTorrentProperty {
        return service.getTorrentGenericProperty(hash)
    }

    suspend fun getTorrentTrackers(hash: String): Array<QBittorrentTracker> {
        return service.getTorrentTrackers(hash)
    }

    suspend fun getTorrentWebSeeds(hash: String): Array<QBittorrentWebSeed> {
        return service.getTorrentWebSeeds(hash)
    }

    suspend fun getTorrentContents(hash: String, indexes: String? = null): Array<QBittorrentTorrentContent> {
        return service.getTorrentContents(hash, indexes)
    }

    suspend fun getTorrentPiecesStates(hash: String): Array<Int> {
        return service.getTorrentPiecesStates(hash)
    }

    suspend fun getTorrentPiecesHashes(hash: String): Array<String> {
        return service.getTorrentPiecesHashes(hash)
    }

    suspend fun stopTorrents(vararg hashes: String) {
        return service.stopTorrents(hashes.joinToString(separator = "|"))
    }

    suspend fun startTorrents(vararg hashes: String) {
        return service.startTorrents(hashes.joinToString(separator = "|"))
    }

    suspend fun removeTorrents(vararg hashes: String, deleteFiles: Boolean) {
        return service.removeTorrents(hashes.joinToString(separator = "|"), deleteFiles)
    }

    suspend fun recheckTorrents(vararg hashes: String) {
        return service.recheckTorrents(hashes.joinToString(separator = "|"))
    }

    suspend fun reannounceTorrents(vararg hashes: String) {
        return service.reannounceTorrents(hashes.joinToString(separator = "|"))
    }

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
    ) {
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
    ) {
        return service.addTorrentFromUrl(urls, savepath, category, tags, skipChecking, paused, rootFolder, rename, upLimit, dlLimit, ratioLimit, seedingTimeLimit, autoTMM, sequentialDownload, firstLastPiecePrio)
    }

    suspend fun addTorrentTracker(hash: String, url: String) {
        return service.addTorrentTrackers(hash, url)
    }

    suspend fun editTorrentTracker(hash: String, origUrl: String, newUrl: String) {
        return service.editTorrentTracker(hash, origUrl, newUrl)
    }

    suspend fun removeTorrentTracker(hash: String, url: String) {
        return service.removeTorrentTrackers(hash, url)
    }

    suspend fun addTorrentPeers(hash: String, vararg peers: String) {
        return service.addTorrentPeers(hash, peers.joinToString(separator = "|"))
    }

    suspend fun increaseTorrentPriority(vararg hashes: String) {
        return service.increaseTorrentPriority(hashes.joinToString(separator = "|"))
    }

    suspend fun decreaseTorrentPriority(vararg hashes: String) {
        return service.decreaseTorrentPriority(hashes.joinToString(separator = "|"))
    }

    suspend fun maximalTorrentPriority(vararg hashes: String) {
        return service.maximalTorrentPriority(hashes.joinToString(separator = "|"))
    }

    suspend fun minimalTorrentPriority(vararg hashes: String) {
        return service.minimalTorrentPriority(hashes.joinToString(separator = "|"))
    }

    suspend fun setTorrentFilePriority(hash: String, id: Int, priority: Int) {
        return service.setTorrentFilePriority(hash, id, priority)
    }

    suspend fun getTorrentDownloadLimit(vararg hashes: String): Map<String, Int> {
        return service.getTorrentDownloadLimit(hashes.joinToString(separator = "|"))
    }

    suspend fun setTorrentDownloadLimit(vararg hashes: String, limit: Int) {
        return service.setTorrentDownloadLimit(hashes.joinToString(separator = "|"), limit)
    }

    suspend fun setTorrentShareLimit(
        vararg hashes: String,
        ratioLimit: Float,
        seedingTimeLimit: Int,
        inactiveSeedingTimeLimit: Int
    ) {
        return service.setTorrentShareLimit(hashes.joinToString(separator = "|"), ratioLimit, seedingTimeLimit, inactiveSeedingTimeLimit)
    }

    suspend fun getTorrentUploadLimit(vararg hashes: String): Map<String, Int> {
        return service.getTorrentUploadLimit(hashes.joinToString(separator = "|"))
    }

    suspend fun setTorrentUploadLimit(vararg hashes: String, limit: Int) {
        return service.setTorrentUploadLimit(hashes.joinToString(separator = "|"), limit)
    }

    suspend fun setTorrentLocation(vararg hashes: String, location: String) {
        return service.setTorrentLocation(hashes.joinToString(separator = "|"), location)
    }

    suspend fun renameTorrent(hash: String, name: String) {
        return service.renameTorrent(hash, name)
    }

    suspend fun setTorrentCategory(vararg hashes: String, category: String) {
        return service.setTorrentCategory(hashes.joinToString(separator = "|"), category)
    }

    suspend fun getCategories(): Map<String, QBittorrentCategory> {
        return service.getCategories()
    }

    suspend fun createCategory(category: String, savePath: String) {
        return service.createCategory(category, savePath)
    }

    suspend fun editCategory(category: String, savePath: String) {
        return service.editCategory(category, savePath)
    }

    suspend fun deleteCategories(vararg categories: String) {
        return service.removeCategories(categories.joinToString(separator = "\n"))
    }

    suspend fun addTorrentTag(vararg hashes: String, tag: String) {
        return service.addTorrentTag(hashes.joinToString(separator = "|"), tag)
    }

    suspend fun removeTorrentTag(vararg hashes: String, tag: String) {
        return service.removeTorrentTag(hashes.joinToString(separator = "|"), tag)
    }

    suspend fun getTags(): Array<String> {
        return service.getTags()
    }

    suspend fun createTags(vararg tags: String) {
        return service.createTags(tags.joinToString(separator = ","))
    }

    suspend fun deleteTags(vararg tags: String) {
        return service.deleteTags(tags.joinToString(separator = ","))
    }

    suspend fun setAutomaticTorrentManagement(vararg hashes: String, enable: Boolean) {
        return service.setAutomaticTorrentManagement(hashes.joinToString(separator = "|"), enable)
    }

    suspend fun toggleSequentialDownload(vararg hashes: String) {
        return service.toggleSequentialDownload(hashes.joinToString(separator = "|"))
    }

    suspend fun toggleFirstLastPiecePriority(vararg hashes: String) {
        return service.toggleFirstLastPiecePriority(hashes.joinToString(separator = "|"))
    }

    suspend fun setForceStart(vararg hashes: String, enable: Boolean) {
        return service.setForceStart(hashes.joinToString(separator = "|"), enable)
    }

    suspend fun setSuperSeeding(vararg hashes: String, enable: Boolean) {
        return service.setSuperSeeding(hashes.joinToString(separator = "|"), enable)
    }

    suspend fun renameTorrentFile(hash: String, oldPath: String, newPath: String) {
        return service.renameTorrentFile(hash, oldPath, newPath)
    }

    suspend fun renameTorrentFolder(hash: String, oldPath: String, newPath: String) {
        return service.renameTorrentFolder(hash, oldPath, newPath)
    }

    suspend fun addRssFolder(path: String) {
        return service.addRssFolder(path)
    }

    suspend fun addRssFeed(url: String, path: String? = null) {
        return service.addRssFeed(url, path)
    }

    suspend fun removeRssFeed(path: String) {
        return service.removeRssFeed(path)
    }

    suspend fun moveRssFeed(itemPath: String, destPath: String) {
        return service.moveRssFeed(itemPath, destPath)
    }

    suspend fun getRssFeeds(withData: Boolean): String {
        return service.getRssFeeds(withData)
    }

    suspend fun markRssAsRead(itemPath: String, articleId: String? = null) {
        return service.markRssAsRead(itemPath, articleId)
    }

    suspend fun refreshRssFeed(itemPath: String) {
        return service.refreshRssFeed(itemPath)
    }

    suspend fun setRssRule(ruleName: String, ruleDef: QBittorrentRssRule) {
        return service.setRssRule(ruleName, objectMapper.writeValueAsString(ruleDef))
    }

    suspend fun renameRssRule(ruleName: String, newRuleName: String) {
        return service.renameRssRule(ruleName, newRuleName)
    }

    suspend fun removeRssRule(ruleName: String) {
        return service.removeRssRule(ruleName)
    }

    suspend fun getRssRules(): Map<String, QBittorrentRssRule> {
        return service.getRssRules()
    }

    suspend fun matchingRssArticles(ruleName: String): Map<String, Array<String>> {
        return service.matchingRssArticles(ruleName)
    }

    suspend fun startSearch(pattern: String, plugins: String, category: String): QBittorrentSearchId {
        return service.startSearch(pattern, plugins, category)
    }

    suspend fun stopSearch(id: Int) {
        return service.stopSearch(id)
    }

    suspend fun getSearchStatus(id: Int? = null): Array<QBittorrentSearchStatus> {
        return service.getSearchStatus(id)
    }

    suspend fun getSearchResults(id: Int, limit: Int? = null, offset: Int? = null): QBittorrentSearchResult {
        return service.getSearchResults(id, limit, offset)
    }

    suspend fun deleteSearchResult(id: Int) {
        return service.deleteSearchResult(id)
    }

    suspend fun getSearchPlugins(): Array<QBittorrentPlugin> {
        return service.getSearchPlugins()
    }

    suspend fun installSearchPlugin(sources: String) {
        return service.installSearchPlugin(sources)
    }

    suspend fun uninstallSearchPlugin(names: String) {
        return service.uninstallSearchPlugin(names)
    }

    suspend fun enableSearchPlugin(names: String, enable: Boolean) {
        return service.enableSearchPlugin(names, enable)
    }

    suspend fun updateSearchPlugins() {
        return service.updateSearchPlugins()
    }
}
