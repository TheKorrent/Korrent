package moe.shizuki.korrent.bittorrent.task

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.client.QBittorrentClient
import moe.shizuki.korrent.bittorrent.event.QBittorrentEventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import java.util.concurrent.ScheduledFuture

@Component
class QBittorrentScheduleTask {
    @Autowired
    private lateinit var taskScheduler: TaskScheduler

    @Autowired
    private lateinit var eventbus: EventBus

    private val tasks: HashMap<String,  ScheduledFuture<*>?> = HashMap()

    fun add(client: QBittorrentClient) {
        val publisher = QBittorrentEventPublisher(eventbus)

        tasks["client"] = taskScheduler.schedule(
            {
                publisher.polling(client)
            },
            CronTrigger(client.clientConfig.common.polling.schedule)
        )
    }

    fun remove(clientName: String) {
        tasks[clientName]?.cancel(false)
        tasks.remove(clientName)
    }
}