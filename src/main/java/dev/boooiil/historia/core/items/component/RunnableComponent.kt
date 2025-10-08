package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.data.RunnableData
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.configuration.ConfigurationSection

class RunnableComponent(
    private val ticks: Int,
    private val command: String,
    private val permission: String
) : ItemComponent {

    override val key = "runnable"

    override fun data(qualityModifier: Double?): RunnableData {
        return RunnableData(this.ticks, this.command, this.permission)
    }

    override fun toString(): String {
        val sb = "RunnableComponent" +
                "{" +
                JSONUtils.fromValue("ticks", ticks) + ", " +
                JSONUtils.fromValue("command", command) + ", " +
                JSONUtils.fromValue("permission", permission) +
                "}"

        return sb
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromValue("ticks", ticks) + ", " +
                JSONUtils.fromValue("command", command) + ", " +
                JSONUtils.fromValue("permission", permission) +
                "}"

        return sb
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): RunnableComponent {
            val ticks = section.getInt("ticks")
            val command = section.getString("command")
            val permission = section.getString("permission")

            return RunnableComponent(ticks, command!!, permission!!)
        }
    }
}
