package dev.slohth.ocean.profile.manager

import dev.slohth.ocean.Ocean
import dev.slohth.ocean.profile.Profile
import dev.slohth.ocean.rank.Rank
import dev.slohth.ocean.utils.framework.config.Config
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*

class ProfileManager(private val ocean: Ocean) : Listener {

    private val profiles: MutableMap<UUID, Profile> = HashMap()

    init { Bukkit.getPluginManager().registerEvents(this, ocean.getPlugin()) }

    private fun registerUser(uuid: UUID) {
        val profile: Profile = Profile(ocean, uuid)

        if (Config.USERS.getConfig().getConfigurationSection("")!!.getKeys(false).contains(uuid.toString())) {
            Config.USERS.getConfig().get("$uuid.ranks")?.let {
                for (rank: String in Config.USERS.getStringList("$uuid.ranks")) {
                    val r: Rank? = ocean.getRankManager().getRankFromName(rank)
                    r?.let { profile.getRanks() + r }
                }
            }
            Config.USERS.getConfig().get("$uuid.permissions").let {
                for (perm: String in Config.USERS.getStringList("$uuid.permissions")) profile.getPermissions() + perm
            }
        }

        if (profile.getRanks().isEmpty()) profile.getRanks() + ocean.getRankManager().getDefaultRank()
        profile.recalculatePermissions()
        profile.getOptions().update()

        profiles[uuid] = profile
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        ocean.getNametagScoreboard().players + e.player
        registerUser(e.player.uniqueId)
    }

}