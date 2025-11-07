package moe.shizuki.korrent.bittorrent.event

import moe.shizuki.korrent.bittorrent.client.QBittorrentClient
import org.springframework.context.ApplicationContext

class QBittorrentEventPublisher(
    val eventbus: ApplicationContext
) {
    private var rid = -1

    private val categories = HashSet<String>()
    private val trackers = HashMap<String, HashSet<String>>()
    private val torrents = HashSet<String>()

    fun polling(client: QBittorrentClient) {
        val syncData = client.getSyncMainData(rid).execute().body() ?: return

        if (syncData.rid != null) {
            rid = syncData.rid
        }

        if (syncData.fullUpdate == true) {
            categories.clear()
            trackers.clear()
            torrents.clear()

            if (syncData.categories != null && syncData.categories.isNotEmpty()) {
                for (category in syncData.categories) {
                    categories.add(category.value.name)
                }
            }

            if (syncData.trackers != null && syncData.trackers.isNotEmpty()) {
                for (tracker in syncData.trackers) {
                    trackers[tracker.key] = tracker.value.toHashSet()
                }
            }

            if (syncData.torrents != null && syncData.torrents.isNotEmpty()) {
                for (torrent in syncData.torrents) {
                    torrents.add(torrent.key)
                }
            }

            return
        }

        if (syncData.categories != null && syncData.categories.isNotEmpty()) {
            for (category in syncData.categories) {
                if (categories.contains(category.value.name)) {
                    continue
                }

                eventbus.publishEvent(QBittorrentCategoryCreatedEvent(client, category.value))
                categories.add(category.value.name)
            }
        }

        if (syncData.categoriesRemoved != null && syncData.categoriesRemoved.isNotEmpty()) {
            for (category in syncData.categoriesRemoved) {
                eventbus.publishEvent(QBittorrentCategoryRemovedEvent(client, category))
                categories.remove(category)
            }
        }

        if (syncData.tags != null && syncData.tags.isNotEmpty()) {
            for (tag in syncData.tags) {
                eventbus.publishEvent(QBittorrentTagCreatedEvent(client, tag))
            }
        }

        if (syncData.tagsRemoved != null && syncData.tagsRemoved.isNotEmpty()) {
            for (tag in syncData.tagsRemoved) {
                eventbus.publishEvent(QBittorrentTagRemovedEvent(client, tag))
            }
        }

        if (syncData.trackers != null && syncData.trackers.isNotEmpty()) {
            for (tracker in syncData.trackers) {
                val seenTorrents = trackers.getOrPut(tracker.key) {
                    HashSet()
                }

                for (torrent in tracker.value) {
                    if (seenTorrents.add(torrent)) {
                        eventbus.publishEvent(QBittorrentTrackerAddedEvent(client, tracker.key, torrent))
                    }
                }
            }
        }

        if (syncData.trackersRemoved != null && syncData.trackersRemoved.isNotEmpty()) {
            for (tracker in syncData.trackersRemoved) {
                eventbus.publishEvent(QBittorrentTrackerRemovedEvent(client, tracker))
                trackers.remove(tracker)
            }
        }

        if (syncData.torrents != null && syncData.torrents.isNotEmpty()) {
            for (torrent in syncData.torrents) {
                if (torrents.contains(torrent.key)) {
                    continue
                }

                eventbus.publishEvent(QBittorrentTorrentAddedEvent(client, torrent.key))
                torrents.add(torrent.key)
            }
        }

        if (syncData.torrentsRemoved != null && syncData.torrentsRemoved.isNotEmpty()) {
            for (torrent in syncData.torrentsRemoved) {
                eventbus.publishEvent(QBittorrentTorrentRemovedEvent(client, torrent))
                torrents.remove(torrent)
            }
        }
    }
}
