package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.BitTorrentClient

open class ScheduleEvent(
) {
    var client: BitTorrentClient? = null

    fun init(client: BitTorrentClient) {
        if (this.client == null) {
            this.client = client
        }
    }
}
