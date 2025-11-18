package moe.shizuki.korrent.bittorrent.model

enum class QBittorrentState(state: String) {
    ERROR("error"),
    MISSING_FILES("missingFiles"),
    UPLOADING("uploading"),
    STOPPED_UPLOAD("stoppedUP"),
    QUEUED_UPLOAD("queuedUP"),
    STALLED_UPLOAD("stalledUP"),
    CHECKING_FINISHED("checkingUP"),
    FORCED_UPLOAD("forcedUP"),
    ALLOCATING("allocating"),
    DOWNLOADING("downloading"),
    META_DOWNLOAD("metaDL"),
    STOPPED_DOWNLOAD("stoppedDL"),
    QUEUED_DOWNLOAD("queuedDL"),
    STALLED_DOWNLOAD("stalledDL"),
    CHECKING_DOWNLOAD("checkingDL"),
    FORCED_DOWNLOAD("forcedDL"),
    CHECKING_RESUME_DATA("checkingResumeData"),
    MOVING("moving"),
    UNKNOWN("unknown")
}
