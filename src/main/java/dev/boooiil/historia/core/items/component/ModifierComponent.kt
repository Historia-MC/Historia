package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.data.ModifierData
import dev.boooiil.historia.core.items.types.Qualities
import dev.boooiil.historia.core.items.types.Weights
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.configuration.ConfigurationSection

@JvmRecord
data class ModifierComponent(
    val weight: Weights,
    val hasQuality: Boolean
) : ItemComponent {
    override fun data(): ModifierData {
        return ModifierData(
            weight,
            if (hasQuality) Qualities.entries.random() else null
        )
    }

    override fun data(qualityModifier: Float): ModifierData {
        return data()
    }

    override fun getKey(): String {
        return "modifier"
    }

    override fun toString(): String {
        val sb = "ModifierComponent" +
                toJSON()

        return sb
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromValue("weight", weight.lowercase()) +
                JSONUtils.fromValue("quality", hasQuality) +
                "}"

        return sb
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): ModifierComponent {
            val weight = Weights.fromString(section.getString("weight"))
            val hasQuality = section.getBoolean("quality")

            return ModifierComponent(
                weight,
                hasQuality
            )
        }
    }
}
