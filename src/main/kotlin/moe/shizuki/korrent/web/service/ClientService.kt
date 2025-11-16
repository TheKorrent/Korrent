package moe.shizuki.korrent.web.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import moe.shizuki.korrent.bittorrent.client.BitTorrentClientManager
import moe.shizuki.korrent.bittorrent.config.BitTorrentConfigManager
import moe.shizuki.korrent.bittorrent.model.BitTorrentClientInfo
import moe.shizuki.korrent.web.exception.ClientAlreadyExistsException
import moe.shizuki.korrent.web.exception.ClientNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ClientService {
    @Autowired
    private lateinit var clientManager: BitTorrentClientManager

    @Autowired
    private lateinit var configManager: BitTorrentConfigManager

    fun getClients(): List<BitTorrentClientInfo> {
        return clientManager.list().map { client ->
            client.getClientInfo()
        }
    }

    fun getClient(clientName: String): BitTorrentClientInfo {
        return clientManager.get(clientName)?.getClientInfo() ?: throw ClientNotFoundException("Client not found")
    }

    fun addClient(client: String) {
        val name = jacksonObjectMapper().readTree(client).get("common").get("name").asText()

        if (clientManager.get(name) != null) {
            throw ClientAlreadyExistsException("Client already exists")
        }

        configManager.add(client)
        configManager.load(name)
    }

    fun removeClient(clientName: String) {
        if (clientManager.get(clientName) == null) {
            throw ClientNotFoundException("Client not found")
        }

        clientManager.remove(clientName)
        configManager.remove(clientName)
    }

    fun updateClient(name: String, client: String) {
        if (clientManager.get(name) == null) {
            throw ClientNotFoundException("Client not found")
        }

        configManager.remove(name)
        configManager.add(client)
        configManager.load(jacksonObjectMapper().readTree(client).get("common").get("name").asText())

        clientManager.remove(name)
    }
}
