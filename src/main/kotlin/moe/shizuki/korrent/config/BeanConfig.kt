package moe.shizuki.korrent.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.korrentConfigFile
import moe.shizuki.korrent.model.KorrentConfig
import moe.shizuki.korrent.objectMapper
import org.pf4j.DefaultPluginManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {
    @Bean
    fun eventbus(): EventBus = EventBus()

    @Bean
    fun pluginManager(): DefaultPluginManager = DefaultPluginManager()

    @Bean
    fun objectMapper(): ObjectMapper = objectMapper

    @Bean
    fun korrentConfig(): KorrentConfig {
        if (!korrentConfigFile.exists()) {
            korrentConfigFile.parentFile.mkdirs()

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(korrentConfigFile, KorrentConfig())
        }

        return objectMapper.readValue(korrentConfigFile.readText(), KorrentConfig::class.java)
    }
}
