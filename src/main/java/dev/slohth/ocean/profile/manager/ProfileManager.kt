package dev.slohth.ocean.profile.manager

import com.mongodb.client.model.Filters
import dev.slohth.ocean.Ocean
import dev.slohth.ocean.profile.Profile
import dev.slohth.ocean.rank.Rank
import dev.slohth.ocean.utils.framework.config.Config
import dev.slohth.ocean.utils.framework.database.Database
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*
import kotlin.collections.ArrayList

class ProfileManager(private val ocean: Ocean) : Listener {

    private val profiles: Map<UUID, Profile> = HashMap()

    init { Bukkit.getPluginManager().registerEvents(this, ocean.getPlugin()) }

    private fun registerUser(uuid: UUID) {
        val profile: Profile = Profile(ocean, uuid)

//        val doc: Document? = Database.PROFILES.getCollection().find().filter(Filters.eq(uuid.toString())).first()
//        if (doc != null) {
//            val ranks: List<String> = ArrayList()
//            if (doc.get("ranks") != null) for (rank: String in doc.getList("ranks", String::class.java)) ranks + rank
//            for (rank: String in ranks) {
//                val r: Rank? = ocean.getRankManager().getRankFromName(rank)
//                if (r != null) profile.getRanks() + r
//            }
//        }

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

    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        registerUser(e.player.uniqueId)
    }

}