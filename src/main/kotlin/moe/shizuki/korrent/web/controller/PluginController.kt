package moe.shizuki.korrent.web.controller

import moe.shizuki.korrent.web.model.ResponseData
import moe.shizuki.korrent.web.service.PluginService
import org.pf4j.PluginDescriptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/plugins")
class PluginController {
    @Autowired
    private lateinit var service: PluginService

    @GetMapping
    fun getPlugins(): ResponseData<List<PluginDescriptor>> {
        return ResponseData(HttpStatus.OK.value(), "Get plugins successful", service.getPlugins())
    }

    @GetMapping("/{id}")
    fun getPlugin(@PathVariable("id") id: String): ResponseData<PluginDescriptor> {
        return ResponseData(HttpStatus.OK.value(), "Get plugin successful", service.getPlugin(id))
    }

    @PostMapping
    fun addPlugin(@RequestParam("file") file: MultipartFile): ResponseData<Void> {
        service.addPlugin(file)

        return ResponseData(HttpStatus.CREATED.value(), "Add plugin successful")
    }

    @DeleteMapping("/{id}")
    fun deletePlugin(@PathVariable("id") id: String): ResponseData<Void> {
        service.removePlugin(id)

        return ResponseData(HttpStatus.NO_CONTENT.value(), "Delete plugin successful")
    }

    @PutMapping("/{id}")
    fun updatePlugin(@PathVariable("id") id: String, @RequestBody file: MultipartFile): ResponseData<Void> {
        service.updatePlugin(id, file)

        return ResponseData(HttpStatus.OK.value(), "Update plugin successful")
    }

    @PatchMapping("/{id}/enable")
    fun enablePlugin(@PathVariable("id") id: String): ResponseData<Void> {
        service.enablePlugin(id)

        return ResponseData(HttpStatus.NO_CONTENT.value(), "Enable plugin successful")
    }

    @PatchMapping("/{id}/disable")
    fun disablePlugin(@PathVariable("id") id: String): ResponseData<Void> {
        service.disablePlugin(id)

        return ResponseData(HttpStatus.NO_CONTENT.value(), "Disable plugin successful")
    }
}
