package moe.shizuki.korrent.bittorrent.client

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.event.PluginInitializeEvent
import moe.shizuki.korrent.bittorrent.task.QBittorrentScheduleTask
import moe.shizuki.korrent.scheduleEventManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component

@Component
class BitTorrentClientManager {
    @Autowired
    private lateinit var eventbus: EventBus

    @Autowired
    private lateinit var taskScheduler: TaskScheduler

    @Autowired
    private lateinit var qBittorrentScheduleTask: QBittorrentScheduleTask

    private val clients: HashMap<String, BitTorrentClient> = HashMap()

    fun add(client: BitTorrentClient) {
        clients["client"] = client

        scheduleEventManager.init(client, taskScheduler, eventbus)
        eventbus.post(PluginInitializeEvent(client))

        if (client is QBittorrentClient) {
            qBittorrentScheduleTask.add(client)
        }
    }

    fun remove() {
        val client = clients.remove("client") ?: return
        scheduleEventManager.cleanup()

        if (client is QBittorrentClient) {
            qBittorrentScheduleTask.remove("client")
        }
    }

    fun get(): BitTorrentClient? {
        return clients["client"]
    }
}
