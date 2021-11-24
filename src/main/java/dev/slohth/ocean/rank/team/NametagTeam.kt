package dev.slohth.ocean.rank.team

import dev.slohth.ocean.Ocean
import dev.slohth.ocean.profile.Profile
import dev.slohth.ocean.utils.CC
import org.bukkit.scoreboard.Team

class NametagTeam(private val ocean: Ocean, private var priority: Int, private var prefix: String, private var suffix: String, private var rank: Boolean) {

    companion object {
        var ID: Int = 0
        val TEAMS: MutableSet<NametagTeam> = HashSet()

        fun getSimilar(priority: Int, prefix: String, suffix: String): NametagTeam? {
            for (team: NametagTeam in TEAMS) if (team.isSimilar(priority, prefix, suffix)) return team
            return null
        }
    }

    private val members: MutableSet<Profile> = HashSet()

    private var teamName: String
    private var team: Team

    init {
        val name: String = getTeamNameFromPriority(priority) + ID++
        teamName = if (name.length <= 16) name else name.substring(0, 16)

        team = ocean.getNametagScoreboard().registerNewTeam(teamName)
        team.prefix = CC.trns(prefix);
        team.suffix = CC.trns(suffix);
        team.color = CC.getLastColor(prefix)

        TEAMS + this
    }

    fun addMember(profile: Profile) {
        if (!members.contains(profile)) {
            members + profile
            team.addPlayer(profile.getPlayer())
        }
    }

    fun removeMember(profile: Profile) {
        if (members.contains(profile)) {
            members - profile
            team.removePlayer(profile.getPlayer())
        }
    }

    fun isMember(profile: Profile): Boolean { return members.contains(profile) }
    fun size(): Int { return members.size }

    fun getTeamName(): String { return teamName }
    fun getPrefix(): String { return prefix }
    fun getSuffix(): String { return suffix }
    fun getPriority(): Int { return priority }
    fun isRankTeam(): Boolean { return rank }

    fun setPrefix(prefix: String) {
        this.prefix = prefix
        team.prefix = CC.trns(prefix)
        team.color = CC.getLastColor(prefix)
    }

    fun setSuffix(suffix: String) {
        this.suffix = suffix
        team.suffix = CC.trns(suffix)
    }

    fun setPriority(priority: Int) {
        this.priority = priority
        for (profile: Profile in members) team.removePlayer(profile.getPlayer())

        team.unregister()
        ocean.getNametagScoreboard().teams.remove(team)

        val name: String = getTeamNameFromPriority(priority) + ID++
        teamName = if (name.length <= 16) name else name.substring(0, 16)

        team = ocean.getNametagScoreboard().registerNewTeam(teamName)
        team.prefix = CC.trns(prefix);
        team.suffix = CC.trns(suffix);
        team.color = CC.getLastColor(prefix)

        for (profile: Profile in members) team.addPlayer(profile.getPlayer())
    }

    fun getSimilar(): NametagTeam? {
        for (team: NametagTeam in TEAMS) if (team.isSimilar(this)) return team
        return null
    }

    fun isSimilar(team: NametagTeam): Boolean {
        return (priority == team.getPriority() && prefix == team.getPrefix() && suffix == team.getSuffix())
    }

    fun isSimilar(priority: Int, prefix: String, suffix: String): Boolean {
        return (this.priority == priority && this.prefix == prefix && this.suffix == suffix)
    }

    fun remove() {
        for (profile: Profile in members) team.removePlayer(profile.getPlayer())
        team.unregister()
        ocean.getNametagScoreboard().teams.remove(team)
        TEAMS - this
    }

    private fun getTeamNameFromPriority(priority: Int): String {
        if (priority < 0) return "Z";
        return ((priority / 5) + 65).toChar().toString().repeat(priority % 5 + 1);
    }

}