package dev.boooiil.historia.core.proficiency.skills

import dev.boooiil.historia.core.util.JSONSerializable
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.jspecify.annotations.NullMarked

@NullMarked
interface ISkill : JSONSerializable {
    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    val type: SkillType

    /**
     * Get the name of the skill.
     *
     * @return Name of the skill.
     */
    val name: NamespacedKey

    /**
     * Get the description of the skill.
     *
     * @return Description of the skill.
     */
    val description: String

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    fun execute(vararg skillSuppliers: SkillSupplier<*>?)

    /**
     * Register to be used to handle when the skill executes.
     */
    fun register()

    fun deregister()

    fun create(section: ConfigurationSection): ISkill
}
