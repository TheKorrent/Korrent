package moe.shizuki.korrent.web.service

import moe.shizuki.korrent.bittorrent.client.BitTorrentClientManager
import moe.shizuki.korrent.bittorrent.config.BitTorrentConfig
import moe.shizuki.korrent.bittorrent.config.BitTorrentConfigManager
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

    fun addClient(client: String) {
        if (clientManager.get() != null) {
            throw ClientAlreadyExistsException("Client already exists")
        }

        configManager.add(client)
        configManager.load()
    }

    fun getClient(): BitTorrentConfig = clientManager.get()?.config ?: throw ClientNotFoundException("Client not found")

    fun updateClient(client: String) {
        if (clientManager.get() == null) {
            throw ClientNotFoundException("Client not found")
        }

        clientManager.remove()
        configManager.remove()
        configManager.add(client)
        configManager.load()
    }

    fun removeClient() {
        if (clientManager.get() == null) {
            throw ClientNotFoundException("Client not found")
        }

        clientManager.remove()
        configManager.remove()
    }
}
