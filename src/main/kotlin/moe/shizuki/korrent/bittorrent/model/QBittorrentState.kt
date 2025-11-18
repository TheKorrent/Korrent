package moe.shizuki.korrent.bittorrent.model

import com.fasterxml.jackson.annotation.JsonCreator

enum class QBittorrentState(
    val state: String,
    val type: Type
) {
    ERROR("error", Type.NONE),
    MISSING_FILES("missingFiles", Type.NONE),
    UPLOADING("uploading", Type.UPLOAD),
    STOPPED_UPLOAD("stoppedUP", Type.UPLOAD),
    QUEUED_UPLOAD("queuedUP", Type.UPLOAD),
    STALLED_UPLOAD("stalledUP", Type.UPLOAD),
    CHECKING_FINISHED("checkingUP", Type.UPLOAD),
    FORCED_UPLOAD("forcedUP", Type.UPLOAD),
    ALLOCATING("allocating", Type.NONE),
    DOWNLOADING("downloading", Type.DOWNLOAD),
    META_DOWNLOAD("metaDL", Type.DOWNLOAD),
    STOPPED_DOWNLOAD("stoppedDL", Type.DOWNLOAD),
    QUEUED_DOWNLOAD("queuedDL", Type.DOWNLOAD),
    STALLED_DOWNLOAD("stalledDL", Type.DOWNLOAD),
    CHECKING_DOWNLOAD("checkingDL", Type.DOWNLOAD),
    FORCED_DOWNLOAD("forcedDL", Type.DOWNLOAD),
    CHECKING_RESUME_DATA("checkingResumeData", Type.NONE),
    MOVING("moving", Type.NONE),
    UNKNOWN("unknown", Type.NONE);

    companion object {
        @JvmStatic
        @JsonCreator
        fun fromString(state: String): QBittorrentState {
            return when (state) {
                "error" -> ERROR
                "missingFiles" -> MISSING_FILES
                "uploading" -> UPLOADING
                "stoppedUP" -> STOPPED_UPLOAD
                "queuedUP" -> QUEUED_UPLOAD
                "stalledUP" -> STALLED_UPLOAD
                "checkingUP" -> CHECKING_FINISHED
                "forcedUP" -> FORCED_UPLOAD
                "allocating" -> ALLOCATING
                "downloading" -> DOWNLOADING
                "metaDL" -> META_DOWNLOAD
                "stoppedDL" -> STOPPED_DOWNLOAD
                "queuedDL" -> QUEUED_DOWNLOAD
                "stalledDL" -> STALLED_DOWNLOAD
                "checkingDL" -> CHECKING_DOWNLOAD
                "forcedDL" -> FORCED_DOWNLOAD
                "checkingResumeData" -> CHECKING_RESUME_DATA
                "moving" -> MOVING
                "unknown" -> UNKNOWN
                else -> valueOf(state)
            }
        }
    }

    enum class Type {
        UPLOAD,
        DOWNLOAD,
        NONE
    }
}
