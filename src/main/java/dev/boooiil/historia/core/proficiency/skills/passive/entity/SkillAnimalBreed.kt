package dev.boooiil.historia.core.proficiency.skills.passive.entity

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.proficiency.skills.AbstractSkillHandler
import dev.boooiil.historia.core.proficiency.skills.ISkill
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier
import dev.boooiil.historia.core.proficiency.skills.SkillType
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityBreedEvent

class SkillAnimalBreed(section: ConfigurationSection) : AbstractSkillHandler() {
    override val name: NamespacedKey = HistoriaCore.getNamespacedKey(section.name)
    override val description: String = section.getString("description") ?: "No description provided."

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    private val entities: Set<EntityType> = section.getStringList("entity").map { entity ->
        requireNotNull(EntityType.fromName(entity)) { "Invalid material specified $entity" }
    }.toSet()

    @EventHandler
    fun handle(event: EntityBreedEvent) {
        execute(SkillSupplier(event))
    }

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    override fun execute(vararg skillSuppliers: SkillSupplier<*>) {

        val event = skillSuppliers[0].get() as? EntityBreedEvent
            ?: error("Expected PlayerItemHeldEvent, but got null or wrong type")

        val entity = event.entity
        val player = event.breeder as Player
        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)
        val proficiency = HistoriaCore.PROFICIENCY_REGISTRY.get(historiaPlayer.proficiency.name)
            ?: error("Tried to get proficiency for player ${historiaPlayer.username} but it did not exist.")
        val hasLevel = historiaPlayer.level
        val wantedLevel = proficiency.skills.get(this) ?: return

        if (wantedLevel > hasLevel)
            event.isCancelled = true

        if (!entities.contains(entity.type))
            event.isCancelled = true
    }

    /**
     * Register to be used to handle when the skill executes.
     */
    override fun register() {
        HistoriaCore.instance.registerEvent(this)
    }

    override fun deregister() {
        TODO("Not yet implemented")
    }

    override fun create(section: ConfigurationSection): ISkill {
        return SkillEntityDrop(section)
    }

    override fun toJSON(): String {
        TODO("Not yet implemented")
    }
}