package moe.shizuki.korrent.plugin.annotation.processor

import io.github.classgraph.ClassGraph
import moe.shizuki.korrent.bittorrent.event.ScheduleEvent
import moe.shizuki.korrent.objectMapper
import moe.shizuki.korrent.plugin.annotation.KorrentScheduleEvent
import moe.shizuki.korrent.pluginConfigFolder
import moe.shizuki.korrent.scheduleEventManager
import java.io.File

class KorrentScheduleEventProcessor {
    companion object {
        private val cache = mutableMapOf<String, Array<ScheduleEvent>>()

        private fun getEvents(basePackage: String, classLoader: ClassLoader): List<Triple<ScheduleEvent, String, String>> {
            ClassGraph().apply {
                enableClassInfo()
                enableAnnotationInfo()
                acceptPackages(basePackage)
                addClassLoader(classLoader)
            }.scan().use { result ->
                val events = result.getClassesWithAnnotation(KorrentScheduleEvent::class.java)

                val triples = events.mapNotNull { scheduleEvent ->
                    val instance = scheduleEvent.loadClass(true)
                    val event = instance.getDeclaredConstructor().newInstance() as ScheduleEvent
                    val cron = scheduleEvent.annotationInfo[KorrentScheduleEvent::class.java.name].parameterValues.getValue("cron") as String
                    val config = scheduleEvent.annotationInfo[KorrentScheduleEvent::class.java.name].parameterValues.getValue("config") as String

                    Triple(event, cron, config)
                }

                return triples
            }
        }

        fun register(basePackage: String, classLoader: ClassLoader) {
            val triples = getEvents(basePackage, classLoader)
            val registeredEvents = mutableListOf<ScheduleEvent>()

            for ((event, cron, config) in triples) {
                var targetCron = cron
                val targetConfig = if (config.startsWith("/")) config.replace(".", "/") else "/" + config.replace(".", "/")

                if (config != "") {
                    targetCron = objectMapper.readTree(File(pluginConfigFolder, "$basePackage.json")).at(targetConfig).asText()

                    if (targetCron == "") {
                        targetCron = cron
                    }
                }

                registeredEvents.add(event)

                scheduleEventManager.register(targetCron, event)
            }

            cache[basePackage] = registeredEvents.toTypedArray()
        }

        fun unregister(basePackage: String) {
            val events = cache[basePackage] ?: return

            for (event in events) {
                scheduleEventManager.unregister(event)
            }

            cache.remove(basePackage)
        }
    }
}
