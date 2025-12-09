package moe.shizuki.korrent.plugin

import moe.shizuki.korrent.plugin.annotation.processor.KorrentConfigProcessor
import moe.shizuki.korrent.plugin.annotation.processor.KorrentEventProcessor
import moe.shizuki.korrent.plugin.annotation.processor.KorrentScheduleEventProcessor
import moe.shizuki.korrent.plugin.config.PluginConfigManager
import moe.shizuki.korrent.plugin.data.PluginDataManager
import org.pf4j.Plugin
import org.pf4j.PluginWrapper

abstract class KorrentPlugin(wrapper: PluginWrapper): Plugin(wrapper) {
    val pluginConfigManager = PluginConfigManager(wrapper.pluginId)
    val pluginDataManager = PluginDataManager(wrapper.pluginId)

    override fun start() {
        super.start()

        KorrentEventProcessor.register(wrapper.pluginId, wrapper.pluginClassLoader)
        KorrentConfigProcessor.register(wrapper.pluginId, wrapper.pluginClassLoader, pluginConfigManager)
        KorrentScheduleEventProcessor.register(wrapper.pluginId, wrapper.pluginClassLoader)
    }

    override fun stop() {
        super.stop()

        KorrentEventProcessor.unregister(wrapper.pluginId)
        KorrentScheduleEventProcessor.unregister(wrapper.pluginId)
    }

    override fun delete() {
        super.delete()

        KorrentEventProcessor.unregister(wrapper.pluginId)
        KorrentScheduleEventProcessor.unregister(wrapper.pluginId)
    }
}
