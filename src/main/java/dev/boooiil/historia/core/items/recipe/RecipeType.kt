package dev.boooiil.historia.core.items.recipe

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.items.recipe.ingredient.TypeIngredient
import dev.boooiil.historia.core.items.recipe.result.Result
import dev.boooiil.historia.core.registry.RegistryHolder
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.InvalidConfigurationException

interface RecipeType<C: CustomRecipe<*>> {
    val key: String
    fun fromConfig(recipeKey: String, section: ConfigurationSection): C

    fun parseIngredient(itemKey: String): TypeIngredient {
        if (itemKey.contains(':')) {
            val key = NamespacedKey.fromString(itemKey.lowercase())
                ?: throw InvalidConfigurationException("Invalid item id: $itemKey")
            return TypeIngredient(key)
        }

        val historiaKey = HistoriaCore.getNamespacedKey(itemKey.lowercase())
        if (RegistryHolder.ITEM_REGISTRY.containsKey(historiaKey)) {
            return TypeIngredient(historiaKey)
        }

        if (Material.valueOf(itemKey.uppercase()) != Material.AIR) {
            val minecraftKey = NamespacedKey.minecraft(itemKey.lowercase())
            return TypeIngredient(minecraftKey)
        }

        throw InvalidConfigurationException("Invalid item id: $itemKey")
    }

    fun getResult(section: ConfigurationSection): Result {
        val resultSection = section.getConfigurationSection(RESULT_KEY)
            ?: throw InvalidConfigurationException("Shaped recipe must have a result")

        return Result.fromConfig(resultSection)
    }

    companion object {

        private const val RESULT_KEY = "result"
    }
}