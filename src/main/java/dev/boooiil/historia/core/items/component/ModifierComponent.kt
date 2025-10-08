package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.data.ModifierData
import dev.boooiil.historia.core.items.types.Quality
import dev.boooiil.historia.core.items.types.Weights
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.configuration.ConfigurationSection

class ModifierComponent(
    val weight: Weights,
    val hasQuality: Boolean
) : ItemComponent {

    override val key = "modifier"

    override fun data(): ModifierData {
        return ModifierData(
            weight,
            if (hasQuality) Quality.entries.random() else null
        )
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
