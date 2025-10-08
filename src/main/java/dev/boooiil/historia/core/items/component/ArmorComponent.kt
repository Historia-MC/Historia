package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.data.ArmorData
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.NumberUtils
import org.bukkit.configuration.ConfigurationSection

class ArmorComponent(
    val defenseRange: MutableList<Float>,
    val durabilityRange: MutableList<Int>
) : ItemComponent {

    override val key = "armor"

    override fun data(): ArmorData {
        val defense = NumberUtils
            .roundFloat(NumberUtils.random(this.defenseRange[0], this.defenseRange[1]), 2)
        val durability = NumberUtils.randomInt(this.durabilityRange[0], this.durabilityRange[1])

        return ArmorData(defense, durability)
    }

    override fun toString(): String {
        val sb = "ArmorComponent" +
                toJSON()

        return sb
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromList("defenseRange", defenseRange) + ", " +
                JSONUtils.fromList("durabilityRange", durabilityRange) +
                "}"

        return sb
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): ArmorComponent {
            return ArmorComponent(
                section.getFloatList("defense"),
                section.getIntegerList("durability")
            )
        }
    }
}
