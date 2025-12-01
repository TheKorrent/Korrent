package moe.shizuki.korrent.plugin.config

class PluginCommonConfig(
    val schedule: Schedule = Schedule()
) {
    class Schedule(
        val cron: String = "*/5 * * * * *"
    )
}
