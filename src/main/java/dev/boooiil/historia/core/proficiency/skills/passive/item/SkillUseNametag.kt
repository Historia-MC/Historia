package dev.boooiil.historia.core.proficiency.skills.passive.item

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.proficiency.skills.AbstractSkillHandler
import dev.boooiil.historia.core.proficiency.skills.ISkill
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier
import dev.boooiil.historia.core.proficiency.skills.SkillType
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEntityEvent

class SkillUseNametag(section: ConfigurationSection) : AbstractSkillHandler() {
    override val name: NamespacedKey = getNamespacedKey(section.name)
    override val description: String = section.getString("description") ?: "No description provided."

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    @EventHandler
    fun handle(event: PlayerInteractEntityEvent?) {
        execute(SkillSupplier(event))
    }

    override fun execute(vararg skillSuppliers: SkillSupplier<*>) {
        // TODO Auto-generated method stub
        throw UnsupportedOperationException("Unimplemented method 'execute'")
    }

    override fun register() {
        HistoriaCore.instance.registerEvent(this)
    }

    override fun deregister() {
        // TODO Auto-generated method stub
        throw UnsupportedOperationException("Unimplemented method 'deregister'")
    }

    override fun create(section: ConfigurationSection): ISkill {
        return SkillUseNametag(section)
    }

    override fun toJSON(): String {
        val sb = StringBuilder()

        sb.append("{")
        sb.append(JSONUtils.fromValue("name", this.name)).append(",")
        sb.append(JSONUtils.fromValue("description", this.description))
        sb.append("}")

        return sb.toString()
    }
}
