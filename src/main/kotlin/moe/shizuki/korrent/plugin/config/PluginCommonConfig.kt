package moe.shizuki.korrent.plugin.config

class PluginCommonConfig(
    val schedule: Schedule
) {
    class Schedule(
        val cron: String
    )
}
