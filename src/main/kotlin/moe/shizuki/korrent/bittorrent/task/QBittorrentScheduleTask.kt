package moe.shizuki.korrent.bittorrent.task

import com.google.common.eventbus.EventBus
import io.github.oshai.kotlinlogging.KotlinLogging
import moe.shizuki.korrent.bittorrent.client.QBittorrentClient
import moe.shizuki.korrent.bittorrent.event.QBittorrentEventPublisher
import moe.shizuki.korrent.clientConfigFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import java.util.concurrent.ScheduledFuture

private val logger = KotlinLogging.logger {}

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
            CronTrigger(client.config.common.polling.cron)
        )

        logger.info { "Client [${client.config.common.name}] registered [${client.config.common.polling.cron}]" }
    }

    fun remove(clientName: String) {
        tasks[clientName]?.cancel(false)
        tasks.remove(clientName)

        logger.info { "Client [${clientName}] unregistered" }
    }
}
