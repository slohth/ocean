package dev.slohth.ocean.rank

import dev.slohth.ocean.Ocean
import dev.slohth.ocean.rank.team.NametagTeam
import org.bukkit.ChatColor
import sun.plugin.dom.exception.InvalidStateException
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Rank(private val ocean: Ocean) {

    private val uuid: UUID = UUID.randomUUID()

    private var name: String = ""
    private var default: Boolean = false

    private val team: NametagTeam = NametagTeam(ocean, -1, "", "", true)
    private var priority: Int = -1

    private var chatPrefix: String = ""
    private var chatSuffix: String = ""
    private var displayName: String = ""

    private val children: Set<Rank> = HashSet()
    private val permissions: Map<String, Boolean> = HashMap()

    fun getUUID(): UUID { return uuid }

    fun getName(): String { return name }
    fun isDefault(): Boolean { return default }

    fun getTeam(): NametagTeam { return team }
    fun getPriority(): Int { return priority }

    fun getChatPrefix(): String { return chatPrefix }
    fun getChatSuffix(): String { return chatSuffix }
    fun getDisplayName(): String { return displayName }

    fun getChildren(): Set<Rank> { return children }
    fun getPermissions(): Map<String, Boolean> { return permissions }

    fun setName(name: String): Boolean {
        ocean.getRankManager().getRankFromName(name.lowercase())?.let { return false }
        this.name = name.lowercase()
        return true
    }
    fun setDefault(def: Boolean) { this.default = def }

    fun setPriority(priority: Int) {
        team.setPriority(priority)
        this.priority = priority
    }

    fun setNametagPrefix(prefix: String) { team.setPrefix(prefix) }
    fun setNametagSuffix(suffix: String) { team.setSuffix(suffix) }
    fun setChatPrefix(prefix: String) { chatPrefix = prefix }
    fun setChatSuffix(suffix: String) { chatSuffix = suffix }
    fun setDisplayName(name: String) { displayName = name }

}