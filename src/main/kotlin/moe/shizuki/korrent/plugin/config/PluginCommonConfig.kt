package moe.shizuki.korrent.plugin.config

class PluginCommonConfig(
    val schedule: Schedule = Schedule(),
    val proxy: Proxy  = Proxy()
) {
    class Schedule(
        val cron: String = "*/5 * * * * *",
        val enabled: Boolean = false
    )

    class Proxy(
        val host: String = "0.0.0.0",
        val port: Int = 1080,
        val type: java.net.Proxy.Type = java.net.Proxy.Type.HTTP,
        val enabled: Boolean = false
    )
}
