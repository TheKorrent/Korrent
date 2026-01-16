package moe.shizuki.korrent.bittorrent.event

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient
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
        val response = client.getSyncMainData(rid).execute()

        if (!response.isSuccessful) {
            return
        }

        val syncData = response.body()

        if (syncData == null) {
            return
        }

        if (syncData.rid == null) {
            return
        }

        rid = syncData.rid

        if (syncData.fullUpdate == true) {
            categories.clear()
            trackers.clear()
            torrents.clear()

            if (!syncData.categories.isNullOrEmpty()) {
                for (category in syncData.categories) {
                    categories.add(category.value.name)
                }
            }

            if (!syncData.trackers.isNullOrEmpty()) {
                for (tracker in syncData.trackers) {
                    trackers[tracker.key] = tracker.value.toHashSet()
                }
            }

            if (!syncData.torrents.isNullOrEmpty()) {
                for (torrent in syncData.torrents) {
                    torrents[torrent.key] = torrent.value
                }
            }

            return
        }

        if (!syncData.categories.isNullOrEmpty()) {
            for (category in syncData.categories) {
                if (categories.contains(category.value.name)) {
                    eventbus.post(QBittorrentCategoryChangedEvent(client, category.value))

                    continue
                }

                eventbus.post(QBittorrentCategoryCreatedEvent(client, category.value))
                categories.add(category.value.name)
            }
        }

        if (!syncData.categoriesRemoved.isNullOrEmpty()) {
            for (category in syncData.categoriesRemoved) {
                eventbus.post(QBittorrentCategoryRemovedEvent(client, category))
                categories.remove(category)
            }
        }

        if (!syncData.tags.isNullOrEmpty()) {
            for (tag in syncData.tags) {
                eventbus.post(QBittorrentTagCreatedEvent(client, tag))
            }
        }

        if (!syncData.tagsRemoved.isNullOrEmpty()) {
            for (tag in syncData.tagsRemoved) {
                eventbus.post(QBittorrentTagRemovedEvent(client, tag))
            }
        }

        if (!syncData.trackers.isNullOrEmpty()) {
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
                    if (trackers[torrent] != null) {
                        continue
                    }

                    eventbus.post(QBittorrentTorrentTrackerAddedEvent(client, torrent, tracker.key))
                }

                trackers[tracker.key] = torrents
            }
        }

        if (!syncData.trackersRemoved.isNullOrEmpty()) {
            for (tracker in syncData.trackersRemoved) {
                eventbus.post(QBittorrentTrackerRemovedEvent(client, tracker))
                trackers.remove(tracker)
            }
        }

        if (!syncData.torrents.isNullOrEmpty()) {
            for (torrent in syncData.torrents) {
                if (!torrents.contains(torrent.key)) {
                    eventbus.post(QBittorrentTorrentAddedEvent(client, torrent.key))
                }

                val newState = torrent.value.state
                val oldState = torrents[torrent.key]?.state

                if (newState != null) {
                    if (newState == QBittorrentState.STOPPED_UPLOAD) {
                        eventbus.post(QBittorrentTorrentStoppedUploadEvent(client, torrent.key))
                    }

                    if (torrent.value.state == QBittorrentState.STOPPED_DOWNLOAD) {
                        eventbus.post(QBittorrentTorrentStoppedDownloadEvent(client, torrent.key))
                    }

                    if (oldState != null) {
                        if (newState.type == QBittorrentState.Type.UPLOAD && oldState.type == QBittorrentState.Type.DOWNLOAD) {
                            eventbus.post(QBittorrentTorrentDownloadedEvent(client, torrent.key))
                        }

                        if (oldState != newState) {
                            eventbus.post(QBittorrentTorrentStateChangedEvent(client, torrent.key, oldState, newState))
                        }
                    }
                }

                if (oldState == QBittorrentState.UPLOADING) {
                    eventbus.post(QBittorrentTorrentUploadingEvent(client, torrent.key))
                }

                if (oldState == QBittorrentState.DOWNLOADING) {
                    eventbus.post(QBittorrentTorrentDownloadingEvent(client, torrent.key))
                }

                torrents[torrent.key] = torrents[torrent.key]?.mergeWith(torrent.value) ?: torrent.value
            }
        }

        if (!syncData.torrentsRemoved.isNullOrEmpty()) {
            for (torrent in syncData.torrentsRemoved) {
                eventbus.post(QBittorrentTorrentRemovedEvent(client, torrent))
                torrents.remove(torrent)
            }
        }

        eventbus.post(QBittorrentPollingEvent(client))
    }
}
