package moe.shizuki.korrent.bittorrent.event

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.client.QBittorrentClient
import moe.shizuki.korrent.bittorrent.model.QBittorrentState
import moe.shizuki.korrent.bittorrent.model.QBittorrentTorrentInfo

class QBittorrentEventPublisher(
    val eventbus: EventBus,
) {
    private var rid = -1

    private val categories = HashSet<String>()
    private val trackers = HashMap<String, HashSet<String>>()
    private val torrents = HashMap<String, QBittorrentTorrentInfo>()

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
                    torrents[torrent.key] = torrent.value
                }
            }

            return
        }

        if (syncData.categories != null && syncData.categories.isNotEmpty()) {
            for (category in syncData.categories) {
                if (categories.contains(category.value.name)) {
                    eventbus.post(QBittorrentCategoryChangedEvent(client, category.value))

                    continue
                }

                eventbus.post(QBittorrentCategoryCreatedEvent(client, category.value))
                categories.add(category.value.name)
            }
        }

        if (syncData.categoriesRemoved != null && syncData.categoriesRemoved.isNotEmpty()) {
            for (category in syncData.categoriesRemoved) {
                eventbus.post(QBittorrentCategoryRemovedEvent(client, category))
                categories.remove(category)
            }
        }

        if (syncData.tags != null && syncData.tags.isNotEmpty()) {
            for (tag in syncData.tags) {
                eventbus.post(QBittorrentTagCreatedEvent(client, tag))
            }
        }

        if (syncData.tagsRemoved != null && syncData.tagsRemoved.isNotEmpty()) {
            for (tag in syncData.tagsRemoved) {
                eventbus.post(QBittorrentTagRemovedEvent(client, tag))
            }
        }

        if (syncData.trackers != null && syncData.trackers.isNotEmpty()) {
            for (tracker in trackers) {
                for (torrent in tracker.value) {
                    if (syncData.trackers[tracker.key]?.contains(torrent) == true) {
                        continue
                    }

                    eventbus.post(QBittorrentTorrentTrackerRemovedEvent(client, torrent, tracker.key))
                }
            }

            for (tracker in syncData.trackers) {
                if (!trackers.containsKey(tracker.key)) {
                    eventbus.post(QBittorrentTrackerAddedEvent(client, tracker.key))
                }

                val torrents = tracker.value.toHashSet()

                for (torrent in torrents) {
                    if (trackers.get(torrent) != null) {
                        continue
                    }

                    eventbus.post(QBittorrentTorrentTrackerAddedEvent(client, torrent, tracker.key))
                }

                trackers[tracker.key] = torrents
            }
        }

        if (syncData.trackersRemoved != null && syncData.trackersRemoved.isNotEmpty()) {
            for (tracker in syncData.trackersRemoved) {
                eventbus.post(QBittorrentTrackerRemovedEvent(client, tracker))
                trackers.remove(tracker)
            }
        }

        if (syncData.torrents != null && syncData.torrents.isNotEmpty()) {
            for (torrent in syncData.torrents) {
                if (!torrents.contains(torrent.key)) {
                    eventbus.post(QBittorrentTorrentAddedEvent(client, torrent.key))
                }

                val info = torrents[torrent.key]

                if (info != null && info.state != null && torrent.value.state != null) {
                    if (info.state == torrent.value.state) {
                        eventbus.post(QBittorrentTorrentStateChangedEvent(client, info.state, torrent.value.state!!))

                        if (torrent.value.state == QBittorrentState.STOPPED_UPLOAD) {
                            eventbus.post(QBittorrentTorrentStoppedUploadEvent(client, torrent.key))
                        }
                    }
                }

                torrents[torrent.key]?.mergeWith(torrent.value)
            }
        }

        if (syncData.torrentsRemoved != null && syncData.torrentsRemoved.isNotEmpty()) {
            for (torrent in syncData.torrentsRemoved) {
                eventbus.post(QBittorrentTorrentRemovedEvent(client, torrent))
                torrents.remove(torrent)
            }
        }

        eventbus.post(QBittorrentPollingEvent(client))
    }
}
