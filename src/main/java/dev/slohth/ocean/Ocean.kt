package dev.slohth.ocean

import dev.slohth.ocean.profile.manager.ProfileManager
import dev.slohth.ocean.rank.manager.RankManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Scoreboard

class Ocean(private val plugin: JavaPlugin) {

    private val nametagScoreboard: Scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard

    private val rankManager: RankManager = RankManager()
    private val profileManager: ProfileManager = ProfileManager(this)

    init {
        Bukkit.getLogger().info("Woo!")
    }

    fun getPlugin(): JavaPlugin { return plugin }
    fun getNametagScoreboard(): Scoreboard { return nametagScoreboard }

    fun getRankManager(): RankManager { return rankManager }
    fun getProfileManager(): ProfileManager { return profileManager }

}