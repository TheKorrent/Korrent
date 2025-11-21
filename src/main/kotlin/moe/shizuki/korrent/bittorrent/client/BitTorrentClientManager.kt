package moe.shizuki.korrent.bittorrent.client

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.event.PluginInitializeEvent
import moe.shizuki.korrent.bittorrent.task.QBittorrentScheduleTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BitTorrentClientManager {
    @Autowired
    private lateinit var eventbus: EventBus

    @Autowired
    private lateinit var qBittorrentScheduleTask: QBittorrentScheduleTask

    private val clients: HashMap<String, BitTorrentClient> = HashMap()

    fun add(client: BitTorrentClient) {
        clients[client.clientInfo.name] = client

        eventbus.post(PluginInitializeEvent(client))

        if (client is QBittorrentClient) {
            qBittorrentScheduleTask.add(client)
        }
    }

    fun remove(clientName: String) {
        val client = clients.remove(clientName) ?: return

        if (client is QBittorrentClient) {
            qBittorrentScheduleTask.remove(clientName)
        }
    }

    fun remove(client: BitTorrentClient) {
        remove(client.clientInfo.name)
    }

    fun get(clientName: String): BitTorrentClient? {
        return clients[clientName]
    }

    fun list(): List<BitTorrentClient> {
        return clients.values.toList()
    }
}
