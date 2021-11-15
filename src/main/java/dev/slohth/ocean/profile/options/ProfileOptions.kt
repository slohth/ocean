package dev.slohth.ocean.profile.options

import dev.slohth.ocean.Ocean
import dev.slohth.ocean.profile.Profile
import dev.slohth.ocean.rank.Rank
import dev.slohth.ocean.rank.team.NametagTeam

class ProfileOptions(private val ocean: Ocean, private val profile: Profile) {

    private var team: NametagTeam? = null
    private var hidden: Boolean = false

    fun getRank(): Rank {
        return if (hidden) ocean.getRankManager().getDefaultRank() else profile.getPrimaryRank()
    }

    fun setPrefix(prefix: String) {
        if (team == null) {
            val check: NametagTeam? = NametagTeam.getSimilar(getRank().getPriority(), prefix, getRank().getNametagSuffix())
            team = check ?: NametagTeam(ocean, getRank().getPriority(), prefix, getRank().getNametagSuffix())
        } else {
            val check: NametagTeam? = NametagTeam.getSimilar(team!!.getPriority(), prefix, team!!.getSuffix())
            if (team!!.size() > 1) team!!.removeMember(profile) else team!!.remove()
            team = check ?: NametagTeam(ocean, team!!.getPriority(), prefix, team!!.getSuffix())
        }
        update()
    }

    fun setSuffix(suffix: String) {
        if (team == null) {
            val check: NametagTeam? = NametagTeam.getSimilar(getRank().getPriority(), getRank().getNametagPrefix(), suffix)
            team = check ?: NametagTeam(ocean, getRank().getPriority(), getRank().getNametagPrefix(), suffix)
        } else {
            val check: NametagTeam? = NametagTeam.getSimilar(team!!.getPriority(), team!!.getPrefix(), suffix)
            if (team!!.size() > 1) team!!.removeMember(profile) else team!!.remove()
            team = check ?: NametagTeam(ocean, team!!.getPriority(), team!!.getPrefix(), suffix)
        }
        update()
    }

    fun setPriority(priority: Int) {
        if (team == null) {
            val check: NametagTeam? = NametagTeam.getSimilar(priority, getRank().getNametagPrefix(), getRank().getNametagSuffix())
            team = check ?: NametagTeam(ocean, priority, getRank().getNametagPrefix(), getRank().getNametagSuffix())
        } else {
            val check: NametagTeam? = NametagTeam.getSimilar(priority, team!!.getPrefix(), team!!.getSuffix())
            if (team!!.size() > 1) team!!.removeMember(profile) else team!!.remove()
            team = check ?: NametagTeam(ocean, priority, team!!.getPrefix(), team!!.getSuffix())
        }
    }

    fun update() {

    }

}