package moe.shizuki.korrent.plugin.config

class PluginCommonConfig(
    val proxy: Proxy  = Proxy()
) {
    class Proxy(
        val host: String = "127.0.0.1",
        val port: Int = 1080,
        val type: java.net.Proxy.Type = java.net.Proxy.Type.HTTP,
        val enabled: Boolean = false
    )
}
