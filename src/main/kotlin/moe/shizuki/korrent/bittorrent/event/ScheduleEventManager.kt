package moe.shizuki.korrent.bittorrent.event

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.client.BitTorrentClient
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import java.util.concurrent.ScheduledFuture

class ScheduleEventManager {
    private val tasks: HashMap<String, ScheduledFuture<*>?> = HashMap()

    private var client: BitTorrentClient? = null
    private var taskScheduler: TaskScheduler? = null
    private var eventbus: EventBus? = null

    internal fun init(
        client: BitTorrentClient,
        taskScheduler: TaskScheduler,
        eventbus: EventBus,
    ) {
        this.client = client
        this.taskScheduler = taskScheduler
        this.eventbus = eventbus
    }

    internal fun cleanup() {
        tasks.values.forEach { it?.cancel(false) }
        tasks.clear()
    }

    fun register(
        cron: String,
        event: ScheduleEvent,
    ) {
        if (client == null || taskScheduler == null || eventbus == null) {
            return
        }

        event.init(client!!)

        tasks[event.javaClass.name] =
            taskScheduler!!.schedule(
                {
                    eventbus!!.post(event)
                },
                CronTrigger(cron),
            )
    }

    fun unregister(event: ScheduleEvent) {
        tasks[event.javaClass.name]?.cancel(false)
        tasks.remove(event.javaClass.name)
    }
}
