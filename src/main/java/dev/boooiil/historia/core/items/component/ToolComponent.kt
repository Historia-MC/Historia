package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.configuration.specific.LoreConfiguration
import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.ItemData
import dev.boooiil.historia.core.items.data.ToolData
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.getDoubleRange
import dev.boooiil.historia.core.util.getIntRange
import dev.boooiil.historia.core.util.lerp
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.configuration.ConfigurationSection
import kotlin.random.Random

class ToolComponent(
    val damageRange: ClosedRange<Double>,
    val speedRange: ClosedRange<Double>,
    val knockbackRange: ClosedRange<Double>,
    val durabilityRange: IntRange,
) : ItemComponent {

    override val key = "tool"

    override fun data(): ToolData {
        return ToolData(
            damageRange.start,
            speedRange.start,
            knockbackRange.start,
            durabilityRange.start,
        )
    }

    override fun data(qualityModifier: Double): ItemData {
        val base = qualityModifier * 0.8
        return ToolData(
            damageRange.lerp(base + Random.nextDouble(0.2)),
            speedRange.lerp(base + Random.nextDouble(0.2)),
            knockbackRange.lerp(base + Random.nextDouble(0.2)),
            durabilityRange.lerp(base + Random.nextDouble(0.2)),
        )
    }

    override fun previewLore(qualityModifier: Double): List<Component> {
        val base = qualityModifier * 0.8

        val raw = LoreConfiguration.get("tool").get("attribute")!!

        val parsed = raw.map { line ->
            Component.text()
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.GRAY)
                .append(
                    MiniMessage.miniMessage().deserialize(
                        line,
                        Placeholder.parsed("tool-damage",
                            "${"%.2f".format(damageRange.lerp(base))}-${"%.2f".format(damageRange.lerp(base + 0.2))}"),
                        Placeholder.parsed("tool-speed",
                            "${"%.2f".format(speedRange.lerp(base))}-${"%.2f".format(speedRange.lerp(base + 0.2))}"),
                        Placeholder.parsed("tool-knockback",
                            "${"%.2f".format(knockbackRange.lerp(base))}-${"%.2f".format(knockbackRange.lerp(base + 0.2))}"),
                    )
                )
                .build()
        }
        return parsed
    }

    override fun displayLore(): List<Component> {
        val raw = LoreConfiguration.get("tool").get("attribute")!!

        val parsed = raw.map { line ->
            Component.text()
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.GRAY)
                .append(
                    MiniMessage.miniMessage().deserialize(
                        line,
                        Placeholder.parsed("tool-damage",
                            "${"%.2f".format(damageRange.start)}-${"%.2f".format(damageRange.endInclusive)}"),
                        Placeholder.parsed("tool-speed",
                            "${"%.2f".format(speedRange.start)}-${"%.2f".format(speedRange.endInclusive)}"),
                        Placeholder.parsed("tool-knockback",
                            "${"%.2f".format(knockbackRange.start)}-${"%.2f".format(knockbackRange.endInclusive)}"),
                    )
                )
                .build()
        }
        return parsed
    }

    override fun toString(): String {
        val sb = "ToolComponent" +
                toJSON()

        return sb
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromList("damageRange", listOf(damageRange.start, damageRange.endInclusive)) + ", " +
                JSONUtils.fromList("speedRange", listOf(speedRange.start, speedRange.endInclusive)) + ", " +
                JSONUtils.fromList("knockbackRange", listOf(knockbackRange.start, knockbackRange.endInclusive)) + ", " +
                JSONUtils.fromList("durabilityRange", listOf(durabilityRange.start, durabilityRange.endInclusive)) +
                "}"

        return sb
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): ToolComponent {
            return ToolComponent(
                section.getDoubleRange("damage"),
                section.getDoubleRange("speed"),
                section.getDoubleRange("knockback"),
                section.getIntRange("durability")
            )
        }
    }
}
