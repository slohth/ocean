package dev.slohth.ocean.utils

import org.bukkit.ChatColor
import java.util.ArrayList

object CC {
    fun trns(msg: String): String {
        return ChatColor.translateAlternateColorCodes('&', msg)
    }

    fun trns(lines: List<String?>): List<String> {
        val toReturn: MutableList<String> = ArrayList()
        for (line in lines) if (line != null) toReturn.add(ChatColor.translateAlternateColorCodes('&', line))
        return toReturn
    }

    fun trns(lines: Array<String?>): List<String> {
        val toReturn: MutableList<String> = ArrayList()
        for (line in lines) if (line != null) toReturn.add(ChatColor.translateAlternateColorCodes('&', line))
        return toReturn
    }

    fun getLastColor(msg: String): ChatColor {
        var color: ChatColor = ChatColor.WHITE
        for (i in 0..msg.length) {
            if (msg[i] == ChatColor.COLOR_CHAR && i != msg.length - 1) color = ChatColor.getByChar(msg[i+1])!!
        }
        return color
    }
}