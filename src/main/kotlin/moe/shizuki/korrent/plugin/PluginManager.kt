package moe.shizuki.korrent.plugin

import org.pf4j.DefaultPluginManager
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class PluginManager {
    private val pluginManager = DefaultPluginManager()

    fun loadPlugins() {
        pluginManager.loadPlugins()
    }

    fun loadPlugin(pluginPath: Path) {
        pluginManager.loadPlugin(pluginPath)
    }

    fun unloadPlugins() {
        pluginManager.unloadPlugins()
    }

    fun unloadPlugin(pluginId: String) {
        pluginManager.unloadPlugin(pluginId)
    }

    fun enablePlugin(pluginId: String) {
        pluginManager.enablePlugin(pluginId)
    }

    fun disablePlugin(pluginId: String) {
        pluginManager.disablePlugin(pluginId)
    }
}
