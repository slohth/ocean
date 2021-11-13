package dev.slohth.ocean.plugin

import dev.slohth.ocean.Ocean
import org.bukkit.plugin.java.JavaPlugin

class OceanPlugin : JavaPlugin() {

    private lateinit var plugin: Ocean

    override fun onEnable() {
        this.plugin = Ocean(this)
    }

}