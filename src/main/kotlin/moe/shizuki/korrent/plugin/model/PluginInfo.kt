package moe.shizuki.korrent.plugin.model

import org.pf4j.PluginDependency
import org.pf4j.PluginState

class PluginInfo(
    val id: String,
    val version: String,
    val provider: String,
    val description: String,
    val license: String,
    val dependencies: List<PluginDependency>,
    val state: PluginState,
)
