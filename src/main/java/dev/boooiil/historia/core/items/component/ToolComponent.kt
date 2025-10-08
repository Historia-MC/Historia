package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.data.ToolData
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.NumberUtils
import net.kyori.adventure.text.Component
import org.bukkit.configuration.ConfigurationSection

class ToolComponent(
    val damageRange: MutableList<Float>,
    val speedRange: MutableList<Float>,
    val knockbackRange: MutableList<Float>,
    val durabilityRange: MutableList<Int>
) : ItemComponent {

    override val key = "tool"

    override fun data(): ToolData {
        val damage = NumberUtils
            .roundFloat(NumberUtils.random(this.damageRange[0], this.damageRange[1]), 2)
        val speed = NumberUtils
            .roundFloat(NumberUtils.random(this.speedRange[0], this.speedRange[1]), 2)
        val knockback = NumberUtils
            .roundFloat(NumberUtils.random(this.knockbackRange[0], this.knockbackRange[1]), 2)
        val durability = NumberUtils.randomInt(this.durabilityRange[0], this.durabilityRange[1])

        return ToolData(damage, speed, knockback, durability)
    }

    override fun previewLore(qualityModifier: Double): List<Component> {
        return listOf(Component.text("Quality: $qualityModifier"))
    }

    override fun toString(): String {
        val sb = "ToolComponent" +
                toJSON()

        return sb
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromList("damageRange", damageRange) + ", " +
                JSONUtils.fromList("speedRange", speedRange) + ", " +
                JSONUtils.fromList("knockbackRange", knockbackRange) + ", " +
                JSONUtils.fromList("durabilityRange", durabilityRange) +
                "}"

        return sb
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): ToolComponent {
            return ToolComponent(
                section.getFloatList("damage"),
                section.getFloatList("speed"),
                section.getFloatList("knockback"),
                section.getIntegerList("durability")
            )
        }
    }
}
