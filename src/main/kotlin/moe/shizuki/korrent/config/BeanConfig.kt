package moe.shizuki.korrent.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.eventbus.EventBus
import org.pf4j.DefaultPluginManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class BeanConfig {
    @Bean
    fun eventbus(): EventBus{
        return EventBus()
    }

    @Bean
    fun pluginManager(): DefaultPluginManager {
        return DefaultPluginManager()
    }

    @Bean
    fun korrentConfig(): KorrentConfig {
        val configFile = File("config/korrent.json")

        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()

            jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValue(configFile, KorrentConfig("Korrent"))
        }

        return jacksonObjectMapper().readValue(configFile.readText(), KorrentConfig::class.java)
    }
}
