package dev.slohth.ocean.rank

import dev.slohth.ocean.Ocean
import dev.slohth.ocean.rank.team.NametagTeam
import org.bukkit.ChatColor
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Rank(private val ocean: Ocean) {

    private val uuid: UUID = UUID.randomUUID()

    private var name: String = ""
    private var color: ChatColor = ChatColor.WHITE
    private var default: Boolean = false

    private val team: NametagTeam = NametagTeam(ocean, "", "", -1)
    private var priority: Int = -1
    private var nametagPrefix: String = ""
    private var nametagSuffix: String = ""

    private var chatPrefix: String = ""
    private var displayName: String = ""

    private val children: Set<Rank> = HashSet()
    private val permissions: Map<String, Boolean> = HashMap()

    fun getUUID(): UUID { return uuid }

    fun getName(): String { return name }
    fun getColor(): ChatColor { return color }
    fun isDefault(): Boolean { return default }

    fun getTeam(): NametagTeam { return team }
    fun getPriority(): Int { return priority }
    fun getNametagPrefix(): String { return nametagPrefix }
    fun getNametagSuffix(): String { return nametagSuffix }

    fun getChatPrefix(): String { return chatPrefix }
    fun getDisplayName(): String { return displayName }

    fun getChildren(): Set<Rank> { return children }
    fun getPermissions(): Map<String, Boolean> { return permissions }

}