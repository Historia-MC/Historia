package dev.boooiil.historia.core.proficiency.skills.passive.entity

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.proficiency.skills.ISkill
import dev.boooiil.historia.core.proficiency.skills.ISkillHandler
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

class SkillEntityDropRestriction(section: ConfigurationSection) : ISkillHandler {
    override val name: NamespacedKey
    override val description: String = section.getString("description") ?: "No description provided."
    override val type: SkillType
        /**
         * Get the type of the skill.
         *
         * @return Type of the skill.
         */
        get() = SkillType.PASSIVE

    private val entities: Set<EntityType>
    private var materialMap: Map<Material, IntRange>

    init {
        require(section.contains("entity")) { "Key 'entity' must be specified." }
        require(section.contains("drops")) { "Key 'drops' must be specified." }

        this.entities = section.getStringList("entity").map { entity ->
            requireNotNull(EntityType.fromName(entity)) { "Invalid material specified $entity" }
        }.toSet()

        val sDrops = section.getConfigurationSection("drops")
            ?: error("Key 'attributes' must be specified.")

        this.materialMap = sDrops.getKeys(false).associate { key ->
            val sMaterial = section.getConfigurationSection(key)!!

            val material = requireNotNull(Material.matchMaterial(sMaterial.name)) {
                "Invalid material provided with $key in ${section.name}."
            }

            val min = section.getInt("min")
            val max = section.getInt("max")

            require(max > 0) { "Max value for $key in ${section.name} was 0 or less." }

            material to min..max
        }

        this.name = HistoriaCore.getNamespacedKey(section.name)

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
    override fun execute(vararg skillSuppliers: SkillSupplier<*>?) {

        /*
        We need to check these things:
        [] Player has skill
            [] else remove drops
        [] Entity is in the allowed list
        [] Replace drops with new drops
         */


        val event = (skillSuppliers[0]?.get() as? EntityDeathEvent)
            ?: error("Expected PlayerItemHeldEvent, but got null or wrong type")

        val entity = event.entity
        val player = event.damageSource.causingEntity as? Player ?: return
        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)
        val drops = event.drops
        // 1️⃣ Iterate over existing drops safely
        val iterator = drops.listIterator()

        if (historiaPlayer.proficiency.skills.contains(this) && entities.contains(entity.type)) {
            // Keep track of which materials we've processed
            val processedMaterials = mutableSetOf<Material>()

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
        } else {
            while (iterator.hasNext()) {
                val drop = iterator.next()
                if (materialMap.keys.contains(drop.type)) {
                    iterator.remove()
                }
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