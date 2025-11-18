package moe.shizuki.korrent.web.controller

import moe.shizuki.korrent.bittorrent.model.BitTorrentClientInfo
import moe.shizuki.korrent.web.model.ResponseData
import moe.shizuki.korrent.web.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clients")
class ClientController {
    @Autowired
    private lateinit var service: ClientService

    @PostMapping
    fun addClient(@RequestBody client: String): ResponseData<Void> {
        service.addClient(client)

        return ResponseData(HttpStatus.CREATED.value(), "Add client successful")
    }

    @GetMapping
    fun getClients(): ResponseData<List<BitTorrentClientInfo>> {
        return ResponseData(HttpStatus.OK.value(), "Get clients successful", service.getClients())
    }

    @GetMapping("/{clientName}")
    fun getClient(@PathVariable clientName: String): ResponseData<BitTorrentClientInfo> {
        return ResponseData(HttpStatus.OK.value(), "Get client successful", service.getClient(clientName))
    }

    @PutMapping("/{clientName}")
    fun updateClient(@PathVariable clientName: String, @RequestBody client: String): ResponseData<Void> {
        service.updateClient(clientName, client)

        return ResponseData(HttpStatus.NO_CONTENT.value(), "Update client successful")
    }

    @DeleteMapping("/{clientName}")
    fun removeClient(@PathVariable clientName: String): ResponseData<Void> {
        service.removeClient(clientName)

        return ResponseData(HttpStatus.NO_CONTENT.value(), "Remove client successful")
    }
}