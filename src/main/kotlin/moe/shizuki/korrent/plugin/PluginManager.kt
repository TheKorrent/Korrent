package moe.shizuki.korrent.plugin

import org.pf4j.DefaultPluginManager
import org.pf4j.PluginState
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class PluginManager {
    private val pluginManager = DefaultPluginManager()

    fun loadPlugins() {
        pluginManager.loadPlugins()
    }

    fun loadPlugin(pluginPath: Path): String? {
        return pluginManager.loadPlugin(pluginPath)
    }

    fun unloadPlugins() {
        pluginManager.unloadPlugins()
    }

    fun unloadPlugin(pluginId: String): Boolean {
        return pluginManager.unloadPlugin(pluginId)
    }

    fun enablePlugin(pluginId: String): Boolean {
        return pluginManager.enablePlugin(pluginId)
    }

    fun disablePlugin(pluginId: String): Boolean {
        return pluginManager.disablePlugin(pluginId)
    }

    fun startPlugins() {
        pluginManager.startPlugins()
    }

    fun startPlugin(pluginId: String): PluginState? {
        return pluginManager.startPlugin(pluginId)
    }

    fun stopPlugins() {
        pluginManager.stopPlugins()
    }

    fun stopPlugin(pluginId: String): PluginState? {
        return pluginManager.stopPlugin(pluginId)
    }
}
