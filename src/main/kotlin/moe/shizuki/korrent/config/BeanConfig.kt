package moe.shizuki.korrent.config

import com.google.common.eventbus.EventBus
import org.pf4j.DefaultPluginManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
}
