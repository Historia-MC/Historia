package dev.boooiil.historia.core.condition

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.items.recipe.CustomRecipe
import dev.boooiil.historia.core.registry.RegistryHolder
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.InvalidConfigurationException

class ProficiencyCondition(
    private val proficiencyKey: NamespacedKey,
) : Condition {

    override fun satisfied(ctx: Condition.Context): Boolean {
        val player = ctx.player ?: return false
        val hPlayer = PlayerStorage.getPlayer(player)
        return proficiencyKey == hPlayer.proficiency.key
    }

    object Type: ConditionType<ProficiencyCondition> {
        override val key = "proficiency"

        override fun fromConfig(section: ConfigurationSection): ProficiencyCondition {
            val key = HistoriaCore.getNamespacedKey(section.getString(key)!!)
            if (!RegistryHolder.PROFICIENCY_REGISTRY.contains(key)) throw InvalidConfigurationException(
                "Invalid proficiency key '$key'"
            )
            return ProficiencyCondition(key)
        }
    }
}