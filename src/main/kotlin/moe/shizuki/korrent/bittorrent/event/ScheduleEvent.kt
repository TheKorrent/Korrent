package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.BitTorrentClient

open class ScheduleEvent {
    private var _client: BitTorrentClient? = null

    val client: BitTorrentClient
        get() = _client ?: throw IllegalStateException("Event not initialized")

    fun init(client: BitTorrentClient) {
        if (this._client == null) {
            this._client = client
        }
    }
}
