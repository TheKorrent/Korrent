package moe.shizuki.korrent.plugin.data

import moe.shizuki.korrent.cacheFolder
import java.io.File

class PluginDataManager(
    private val id: String
) {
    val pluginDataFolder = File(moe.shizuki.korrent.pluginDataFolder, id)
    val pluginCacheFolder = File(cacheFolder, "plugins${File.separator}$id")
}
