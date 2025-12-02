package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.BitTorrentClient

class PluginInitializeEvent(
    val client: BitTorrentClient,
)
