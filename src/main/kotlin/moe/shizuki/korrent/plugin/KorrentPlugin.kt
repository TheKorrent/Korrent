package moe.shizuki.korrent.plugin

import io.github.oshai.kotlinlogging.KLogger
import moe.shizuki.korrent.plugin.annotation.processor.KorrentConfigProcessor
import moe.shizuki.korrent.plugin.annotation.processor.KorrentEventProcessor
import moe.shizuki.korrent.plugin.annotation.processor.KorrentScheduleEventProcessor
import org.pf4j.Plugin
import org.pf4j.PluginWrapper

abstract class KorrentPlugin(wrapper: PluginWrapper): Plugin(wrapper) {
    abstract val logger: KLogger
    abstract val id: String

    override fun start() {
        super.start()

        KorrentEventProcessor.register(id, wrapper.pluginClassLoader)
        KorrentConfigProcessor.register(id, wrapper.pluginClassLoader)
        KorrentScheduleEventProcessor.register(id, wrapper.pluginClassLoader)
    }

    override fun stop() {
        super.stop()

        KorrentEventProcessor.unregister(id)
        KorrentScheduleEventProcessor.unregister(id)
    }

    override fun delete() {
        super.delete()

        KorrentEventProcessor.unregister(id)
        KorrentScheduleEventProcessor.unregister(id)
    }
}
