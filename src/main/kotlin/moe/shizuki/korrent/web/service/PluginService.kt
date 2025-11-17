package moe.shizuki.korrent.web.service

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

    fun getPlugins(): List<PluginDescriptor> {
        return pluginManager.plugins.toList().map { plugin ->
            plugin.descriptor
        }
    }

    fun getPlugin(id: String): PluginDescriptor {
        return (pluginManager.getPlugin(id) ?: throw PluginNotFoundException("Plugin not found")).descriptor
    }

    fun addPlugin(file: MultipartFile) {
        val temp = File("data/cache/${file.originalFilename}.temp")

        temp.parentFile.mkdirs()
        temp.createNewFile()

        file.transferTo(File(temp.absolutePath))

        val jar = JarFile(temp)
        val id = jar.manifest.mainAttributes.getValue("Plugin-Id")

        jar.close()

        val plugin = File("plugins/${id}.jar")

        if (plugin.exists()) {
            throw PluginAlreadyExistsException("Plugin already exists")
        }

        temp.copyTo(plugin)

        pluginManager.loadPlugin(plugin.toPath())
    }

    fun removePlugin(id: String) {
        pluginManager.getPlugin(id) ?: throw PluginNotFoundException("Plugin not found")
        pluginManager.deletePlugin(id)
    }

    fun updatePlugin(id: String, file: MultipartFile) {
        removePlugin(id)
        addPlugin(file)
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
