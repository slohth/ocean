package dev.slohth.ocean.rank.manager

import dev.slohth.ocean.Ocean
import dev.slohth.ocean.rank.Rank
import dev.slohth.ocean.utils.framework.config.Config
import org.bukkit.Bukkit
import java.util.*
import kotlin.collections.HashMap

class RankManager(private val ocean: Ocean) {

    private val ranks: MutableMap<UUID, Rank> = HashMap();

    init {
        Bukkit.getScheduler().runTaskLater(ocean.getPlugin(), Runnable {
            for (rankName: String in Config.RANKS.getConfig().getConfigurationSection("")!!.getKeys(false)) {
                val rank: Rank = Rank(ocean)

                rank.setName(rankName.lowercase())
                rank.setDisplayName(Config.RANKS.getStringOrDefault("$rankName.display", ""))

                rank.setNametagPrefix(Config.RANKS.getStringOrDefault("$rankName.nametag.prefix", ""))
                rank.setNametagSuffix(Config.RANKS.getStringOrDefault("$rankName.nametag.suffix", ""))

                rank.setChatPrefix(Config.RANKS.getStringOrDefault("$rankName.prefix", ""))
                rank.setChatPrefix(Config.RANKS.getStringOrDefault("$rankName.suffix", ""))

                rank.setDefault(Config.RANKS.getBoolean("$rankName.default"))

                Config.RANKS.getConfig().get("$rankName.permissions")?.let {
                    for (perm: String in Config.RANKS.getConfig().getStringList("$rankName.permissions")) {
                        rank.getPermissions()[if (perm.startsWith("-")) perm.substring(1) else perm] = !perm.startsWith("-")
                    }
                }

                ranks[rank.getUUID()] = rank
            }

            Bukkit.getScheduler().runTaskLater(ocean.getPlugin(), Runnable {
                for (rank: Rank in ranks.values) {
                    Config.RANKS.getConfig().get("${rank.getName()}.children")?.let {
                        for (rankName: String in Config.RANKS.getStringList("${rank.getName()}.children")) {
                            val child: Rank? = ocean.getRankManager().getRankFromName(rankName)
                            child?.let { rank.getChildren() + child }
                        }
                    }
                }
            }, 1)
        }, 1)
    }

    fun getDefaultRank(): Rank {
        for (rank: Rank in ranks.values) if (rank.isDefault()) return rank
        throw IllegalStateException("No default rank has been detected!")
    }

    fun getRankFromName(name: String): Rank? {
        for (rank: Rank in ranks.values) if (rank.getName() == name.lowercase()) return rank
        return null
    }

}