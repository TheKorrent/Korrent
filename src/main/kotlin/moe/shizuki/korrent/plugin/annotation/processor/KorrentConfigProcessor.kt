package moe.shizuki.korrent.plugin.annotation.processor

import io.github.classgraph.ClassGraph
import moe.shizuki.korrent.plugin.annotation.KorrentConfig
import moe.shizuki.korrent.plugin.config.PluginConfig
import moe.shizuki.korrent.plugin.config.PluginConfigManager

class KorrentConfigProcessor {
    companion object {
        private fun getConfigs(basePackage: String, classLoader: ClassLoader): Array<Any> {
            ClassGraph().apply {
                enableClassInfo()
                enableAnnotationInfo()
                acceptPackages(basePackage)
                addClassLoader(classLoader)
            }.scan().use { result ->
                val configs = result.getClassesWithAnnotation(KorrentConfig::class.java)

                val instances = configs.mapNotNull { config ->
                    config.loadClass(true).getDeclaredConstructor().newInstance()
                }

                return instances.toTypedArray()
            }
        }

        fun register(basePackage: String, classLoader: ClassLoader, pluginConfigManager: PluginConfigManager) {
            val configs = getConfigs(basePackage, classLoader)

            for (config in configs) {
                pluginConfigManager.register(config as PluginConfig)
            }
        }
    }
}
