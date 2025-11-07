package moe.shizuki.korrent.bittorrent.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component
import java.io.File

@Component
class BitTorrentConfigManager {
    private val objectMapper = jacksonObjectMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    fun add(name: String, config: Any) {
        val file = File("config/$name/config.json")

        if(file.exists()) {
            return
        }

        file.parentFile.mkdirs()
        file.createNewFile()

        objectMapper.writeValue(file, config)
    }

    fun remove(name: String) {
        File("config/$name/config.json").delete()
    }

    fun load(name: String, clazz: Class<Any>): Any? {
        return objectMapper.readValue(File("config/$name/config.json"), clazz)
    }
}
