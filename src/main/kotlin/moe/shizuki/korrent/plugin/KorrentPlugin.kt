package moe.shizuki.korrent.plugin

import com.google.common.eventbus.EventBus
import org.pf4j.ExtensionPoint

interface KorrentPlugin : ExtensionPoint {
    fun initialize(eventBus: EventBus)
}
