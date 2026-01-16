package moe.shizuki.korrent.bittorrent.event

import com.google.common.eventbus.EventBus
import moe.shizuki.korrent.bittorrent.client.call.QBittorrentClient
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.ALLOCATING
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.CHECKING_DOWNLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.CHECKING_RESUME_DATA
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.CHECKING_UPLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.DOWNLOADING
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.ERROR
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.FORCED_DOWNLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.FORCED_UPLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.META_DOWNLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.MISSING_FILES
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.MOVING
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.QUEUED_DOWNLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.QUEUED_UPLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.STALLED_DOWNLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.STALLED_UPLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.STOPPED_DOWNLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.STOPPED_UPLOAD
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.Type
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.UNKNOWN
import moe.shizuki.korrent.bittorrent.model.QBittorrentState.UPLOADING
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
                    val event = when (newState) {
                        ERROR -> QBittorrentTorrentErrorEvent(client, torrent.key)
                        MISSING_FILES -> QBittorrentTorrentMissingFilesEvent(client, torrent.key)
                        UPLOADING -> QBittorrentTorrentUploadingEvent(client, torrent.key)
                        STOPPED_UPLOAD -> QBittorrentTorrentStoppedUploadEvent(client, torrent.key)
                        QUEUED_UPLOAD -> QBittorrentTorrentQueuedUploadEvent(client, torrent.key)
                        STALLED_UPLOAD -> QBittorrentTorrentStalledUploadEvent(client, torrent.key)
                        CHECKING_UPLOAD -> QBittorrentTorrentCheckingUploadEvent(client, torrent.key)
                        FORCED_UPLOAD -> QBittorrentTorrentForcedUploadEvent(client, torrent.key)
                        ALLOCATING -> QBittorrentTorrentAllocatingEvent(client, torrent.key)
                        DOWNLOADING -> QBittorrentTorrentDownloadingEvent(client, torrent.key)
                        META_DOWNLOAD -> QBittorrentTorrentMetaDownloadEvent(client, torrent.key)
                        STOPPED_DOWNLOAD -> QBittorrentTorrentStoppedDownloadEvent(client, torrent.key)
                        QUEUED_DOWNLOAD -> QBittorrentTorrentQueuedDownloadEvent(client, torrent.key)
                        STALLED_DOWNLOAD -> QBittorrentStalledDownloadEvent(client, torrent.key)
                        CHECKING_DOWNLOAD -> QBittorrentTorrentCheckingDownloadEvent(client, torrent.key)
                        FORCED_DOWNLOAD -> QBittorrentTorrentForcedDownloadEvent(client, torrent.key)
                        CHECKING_RESUME_DATA -> QBittorrentTorrentCheckingResumeDataEvent(client, torrent.key)
                        MOVING -> QBittorrentTorrentMovingEvent(client, torrent.key)
                        UNKNOWN -> QBittorrentTorrentUnknownEvent(client, torrent.key)
                    }

                    eventbus.post(event)

                    if (oldState != null) {
                        if (newState.type == Type.UPLOAD && oldState.type == Type.DOWNLOAD) {
                            eventbus.post(QBittorrentTorrentDownloadedEvent(client, torrent.key))
                        }

                        if (oldState != newState) {
                            eventbus.post(QBittorrentTorrentStateChangedEvent(client, torrent.key, oldState, newState))
                        }
                    }
                } else {
                    if (oldState == UPLOADING) {
                        eventbus.post(QBittorrentTorrentUploadingEvent(client, torrent.key))
                    }

                    if (oldState == DOWNLOADING) {
                        eventbus.post(QBittorrentTorrentDownloadingEvent(client, torrent.key))
                    }
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
