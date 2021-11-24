package dev.slohth.ocean.profile

import dev.slohth.ocean.Ocean
import dev.slohth.ocean.profile.options.ProfileOptions
import dev.slohth.ocean.rank.Rank
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import java.util.*

class Profile(private val ocean: Ocean, private val uuid: UUID) {

    private val attachment: PermissionAttachment = getPlayer().addAttachment(ocean.getPlugin())

    private val ranks: MutableSet<Rank> = HashSet()
    private val permissions: MutableSet<String> = HashSet()

    private val options: ProfileOptions = ProfileOptions(ocean, this)

    fun getPrimaryRank(): Rank {
        var rank: Rank? = null
        for (r: Rank in ranks) {
            if (rank == null || rank.getPriority() < r.getPriority()) rank = r
        }
        return rank ?: ocean.getRankManager().getDefaultRank()
    }

    fun recalculatePermissions() {
        attachment.permissions.clear()
        for (rank: Rank in ranks) applyRankPermissions(rank)
        for (perm: String in permissions) {
            if (perm == "*") for (p: Permission in Bukkit.getPluginManager().permissions) attachment.setPermission(p.name, true)
            attachment.setPermission(if (perm.startsWith("-")) perm.substring(1) else perm, !perm.startsWith("-"))
        }
    }

    private fun applyRankPermissions(rank: Rank) {
        for (e: Map.Entry<String, Boolean> in rank.getPermissions().entries) {
            if (e.key == "*") for (p: Permission in Bukkit.getPluginManager().permissions) attachment.setPermission(p.name, true)
            attachment.setPermission(e.key, e.value)
        }
        for (r: Rank in rank.getChildren()) applyRankPermissions(r)
    }

    fun getPlayer(): Player { return Bukkit.getPlayer(uuid)!! }

    fun getUUID(): UUID { return uuid }
    fun getAttachment(): PermissionAttachment { return attachment }

    fun getRanks(): MutableSet<Rank> { return ranks }
    fun getPermissions(): MutableSet<String> { return permissions }

    fun getOptions(): ProfileOptions { return options }

}
