package moe.shizuki.korrent.web.service

import moe.shizuki.korrent.web.exception.InvalidPluginException
import moe.shizuki.korrent.web.exception.PluginAlreadyExistsException
import moe.shizuki.korrent.web.exception.PluginNotFoundException
import org.pf4j.DefaultPluginManager
import org.pf4j.PluginDescriptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.jar.JarFile

@Service
class PluginService {
    @Autowired
    lateinit var pluginManager: DefaultPluginManager

    fun addPlugin(file: MultipartFile) {
        val temp = File("data/cache/${file.originalFilename}.temp")

        temp.parentFile.mkdirs()
        file.transferTo(File(temp.absolutePath))

        val id = runCatching {
            JarFile(temp).use { jar ->
                jar.manifest.mainAttributes.getValue("Plugin-Id")
            }
        }.getOrElse {
            throw InvalidPluginException("Plugin cannot be parsed")
        }

        val plugin = File("plugins/${id}.jar")

        if (plugin.exists()) {
            throw PluginAlreadyExistsException("Plugin already exists")
        }

        temp.copyTo(plugin)
        pluginManager.loadPlugin(plugin.toPath())
    }

    fun getPlugins(): List<PluginDescriptor> {
        return pluginManager.plugins.toList().map { plugin ->
            plugin.descriptor
        }
    }

    fun getPlugin(id: String): PluginDescriptor {
        return (pluginManager.getPlugin(id) ?: throw PluginNotFoundException("Plugin not found")).descriptor
    }

    fun updatePlugin(id: String, file: MultipartFile) {
        removePlugin(id)
        addPlugin(file)
    }

    fun removePlugin(id: String) {
        pluginManager.getPlugin(id) ?: throw PluginNotFoundException("Plugin not found")
        pluginManager.deletePlugin(id)
    }

    fun enablePlugin(id: String) {
        if (pluginManager.getPlugin(id) == null) {
            throw PluginNotFoundException("Plugin not found")
        }

        pluginManager.enablePlugin(id)
    }

    fun disablePlugin(id: String) {
        if (pluginManager.getPlugin(id) == null) {
            throw PluginNotFoundException("Plugin not found")
        }

        pluginManager.disablePlugin(id)
    }
}
