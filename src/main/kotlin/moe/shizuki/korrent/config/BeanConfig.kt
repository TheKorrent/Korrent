package moe.shizuki.korrent.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.objectMapper
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
    fun objectMapper(): ObjectMapper {
        return objectMapper
    }

    @Bean
    fun korrentConfig(): KorrentConfig {
        val configFile = File("config/korrent.json")

        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(configFile, KorrentConfig())
        }

        return objectMapper.readValue(configFile.readText(), KorrentConfig::class.java)
    }
}
