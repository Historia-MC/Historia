package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.data.ExecutorData
import dev.boooiil.historia.core.items.executor.ItemExecutable
import dev.boooiil.historia.core.items.types.Triggers
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.configuration.ConfigurationSection

class ExecutorComponent(
    val executables: HashMap<Triggers, ItemExecutable>
) : ItemComponent {

    override val key = "executor"

    override fun data(): ExecutorData {
        return ExecutorData(executables)
    }

    override fun toString(): String {
        if (executables.isEmpty()) return "ExecutorComponent{}"

        val sb = "ExecutorComponent" +
                "{" +
                JSONUtils.fromMap("executables", executables, true) +
                "}"

        return sb
    }

    override fun toJSON(): String {
        if (executables.isEmpty()) return "{}"

        val sb = "{" +
                JSONUtils.fromMap("executables", executables) +
                "}"

        return sb
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): ExecutorComponent {
            val executables = HashMap<Triggers, ItemExecutable>()
            val triggers: MutableList<Triggers> = ArrayList()

            // get valid triggers from the config
            for (sTrigger in section.getKeys(false)) {
                val trigger = Triggers.fromString(sTrigger)

                if (trigger == Triggers.UNKNOWN) {
                    CoreLogger.errorToConsole("Tried to get trigger $sTrigger from executor but it does not exist.")
                    continue
                }

                triggers.add(trigger!!)
            }

            for (trigger in triggers) {
                val triggerSection = section.getConfigurationSection(trigger.lowercase)

                val commands = triggerSection!!.getStringList("commands")
                val cooldown = triggerSection.getInt("cooldown")
                val uses = triggerSection.getInt("uses")
                val hasElevation = triggerSection.getBoolean("elevation")

                executables[trigger] = ItemExecutable(commands, cooldown, uses, hasElevation, false)
            }

            return ExecutorComponent(executables)
        }
    }
}
