package dev.boooiil.historia.core.proficiency.skills.passive.entity

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.proficiency.skills.AbstractSkillHandler
import dev.boooiil.historia.core.proficiency.skills.ISkill
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier
import dev.boooiil.historia.core.proficiency.skills.SkillType
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import org.jspecify.annotations.NullMarked

@NullMarked
class SkillEntityDrop(section: ConfigurationSection) : AbstractSkillHandler() {
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
    private var materialMap: Map<Material, IntRange>

    init {

        val sDrops = section.getConfigurationSection("drops")
            ?: error("Key 'attributes' must be specified.")

        this.materialMap = sDrops.getKeys(false).associate { key ->
            val sMaterial = sDrops.getConfigurationSection(key)
                ?: error("Key '$key' was not found.")

            val material = requireNotNull(Material.matchMaterial(sMaterial.name)) {
                "Invalid material provided with $key in ${section.name}."
            }

            val min = sMaterial.getInt("min")
            val max = sMaterial.getInt("max")

            require(max > 0) { "Max value for $key in ${section.name} was 0 or less." }

            material to min..max
        }
    }

    @EventHandler
    fun handle(event: EntityDeathEvent) {
        execute(SkillSupplier(event))
    }

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    override fun execute(vararg skillSuppliers: SkillSupplier<*>) {
        val event: EntityDeathEvent = getOrThrow(skillSuppliers, 0)

        val entity = event.entity
        val player = event.damageSource.causingEntity as? Player ?: return
        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        if (!hasSkill(historiaPlayer) && !hasLevelRequirement(historiaPlayer)) return

        if (entities.contains(entity.type)) {

            val drops = event.drops

            // Keep track of which materials we've processed
            val processedMaterials = mutableSetOf<Material>()

            // 1️⃣ Iterate over existing drops safely
            val iterator = drops.listIterator()
            while (iterator.hasNext()) {
                val drop = iterator.next()
                val mat = drop.type

                if (mat in materialMap.keys) {
                    // Remove the old drop
                    iterator.remove()

                    // Recreate it using the materialMap info
                    val range = materialMap[mat]!!
                    val newDrop = ItemStack(mat, range.random()) // random count in min..max
                    iterator.add(newDrop)

                    processedMaterials.add(mat)
                }
                // else: keep it intact
            }

            // 2️⃣ Add any materials in the map that weren't in the drops
            materialMap.keys
                .filter { it !in processedMaterials && drops.none { drop -> drop.type == it } }
                .forEach { mat ->
                    val range = materialMap[mat]!!
                    drops.add(ItemStack(mat, range.random()))
                }
        }

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