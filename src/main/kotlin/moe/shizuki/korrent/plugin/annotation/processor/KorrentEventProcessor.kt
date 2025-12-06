package moe.shizuki.korrent.plugin.annotation.processor

import io.github.classgraph.ClassGraph
import moe.shizuki.korrent.eventbus
import moe.shizuki.korrent.plugin.annotation.KorrentEvent

class KorrentEventProcessor {
    companion object {
        private val cache = mutableMapOf<String, Array<Any>>()

        private fun getListeners(basePackage: String, classLoader: ClassLoader): Array<Any> {
            val result = ClassGraph().apply {
                enableClassInfo()
                enableAnnotationInfo()
                acceptPackages(basePackage)
                addClassLoader(classLoader)
            }.scan()

            val listeners = result.getClassesWithAnnotation(KorrentEvent::class.java)

            val instances = listeners.mapNotNull { listener ->
                listener.loadClass(true).getDeclaredConstructor().newInstance()
            }

            return instances.toTypedArray()
        }

        fun register(basePackage: String, classLoader: ClassLoader) {
            val listeners = getListeners(basePackage, classLoader)

            cache[basePackage] = listeners

            for (listener in listeners) {
                eventbus.register(listener)
            }
        }

        fun unregister(basePackage: String) {
            val listeners = cache[basePackage] ?: return

            for (listener in listeners) {
                eventbus.unregister(listener)
            }
        }
    }
}
