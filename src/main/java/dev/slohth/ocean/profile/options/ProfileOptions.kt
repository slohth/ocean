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
            val check: NametagTeam? = NametagTeam.getSimilar(getRank().getPriority(), prefix, getRank().getTeam().getSuffix())
            team = check ?: NametagTeam(ocean, getRank().getPriority(), prefix, getRank().getTeam().getSuffix(), false)
        } else {
            val check: NametagTeam? = NametagTeam.getSimilar(team!!.getPriority(), prefix, team!!.getSuffix())
            if (team!!.size() > 1) team!!.removeMember(profile) else team!!.setPrefix(prefix)
            team = check ?: if (!team!!.isMember(profile)) NametagTeam(ocean, team!!.getPriority(), prefix, team!!.getSuffix(), false) else team
        }
        update()
    }

    fun setSuffix(suffix: String) {
        if (team == null) {
            val check: NametagTeam? = NametagTeam.getSimilar(getRank().getPriority(), getRank().getTeam().getPrefix(), suffix)
            team = check ?: NametagTeam(ocean, getRank().getPriority(), getRank().getTeam().getPrefix(), suffix, false)
        } else {
            val check: NametagTeam? = NametagTeam.getSimilar(team!!.getPriority(), team!!.getPrefix(), suffix)
            if (team!!.size() > 1) team!!.removeMember(profile) else team!!.setSuffix(suffix)
            team = check ?: if (!team!!.isMember(profile)) NametagTeam(ocean, team!!.getPriority(), team!!.getPrefix(), suffix, false) else team
        }
        update()
    }

    fun setPriority(priority: Int) {
        team = if (team == null) {
            val check: NametagTeam? = NametagTeam.getSimilar(priority, getRank().getTeam().getPrefix(), getRank().getTeam().getSuffix())
            check ?: NametagTeam(ocean, priority, getRank().getTeam().getPrefix(), getRank().getTeam().getSuffix(), false)
        } else {
            val check: NametagTeam? = NametagTeam.getSimilar(priority, team!!.getPrefix(), team!!.getSuffix())
            if (team!!.size() > 1) team!!.removeMember(profile) else team!!.setPriority(priority)
            check ?: if (!team!!.isMember(profile)) NametagTeam(ocean, priority, team!!.getPrefix(), team!!.getSuffix(), false) else team
        }
    }

    fun resetNametag() {
        if (team != null) {
            if (team!!.size() > 1) team!!.removeMember(profile) else team!!.remove()
            team = null
        }
        update()
    }

    fun update() {
        team?.removeMember(profile)
        ocean.getRankManager().getDefaultRank().getTeam().removeMember(profile)
        profile.getPrimaryRank().getTeam().removeMember(profile)

        if (team == null) getRank().getTeam().addMember(profile) else team!!.addMember(profile)
    }

}