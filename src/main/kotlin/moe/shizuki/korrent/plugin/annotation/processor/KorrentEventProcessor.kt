package moe.shizuki.korrent.plugin.annotation.processor

import io.github.classgraph.ClassGraph
import io.github.oshai.kotlinlogging.KotlinLogging
import moe.shizuki.korrent.eventbus
import moe.shizuki.korrent.plugin.annotation.KorrentEvent

val logger = KotlinLogging.logger {}

class KorrentEventProcessor {
    companion object {
        private val cache = mutableMapOf<String, Array<Any>>()

        private fun getListeners(basePackage: String, classLoader: ClassLoader): Array<Any> {
            ClassGraph().apply {
                enableClassInfo()
                enableAnnotationInfo()
                acceptPackages(basePackage)
                addClassLoader(classLoader)
            }.scan().use { result ->
                val listeners = result.getClassesWithAnnotation(KorrentEvent::class.java)

                val instances = listeners.mapNotNull { listener ->
                    listener.loadClass(true).getDeclaredConstructor().newInstance()
                }

                return instances.toTypedArray()
            }
        }

        fun register(basePackage: String, classLoader: ClassLoader) {
            val listeners = getListeners(basePackage, classLoader)

            cache[basePackage] = listeners

            for (listener in listeners) {
                eventbus.register(listener)

                logger.debug { "Listener registered: [${listener::class.java.name}]" }
            }
        }

        fun unregister(basePackage: String) {
            val listeners = cache[basePackage] ?: return

            for (listener in listeners) {
                eventbus.unregister(listener)

                logger.debug { "Listener unregistered: [${listener::class.java.name}]" }
            }

            cache.remove(basePackage)
        }
    }
}
