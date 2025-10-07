package dev.boooiil.historia.core.items.recipe

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.registry.RegistryHolder
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.inventory.ItemStack

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

    fun getResult(section: ConfigurationSection): ItemStack {

        val resultSection = section.getConfigurationSection(RESULT_KEY)
            ?: throw InvalidConfigurationException("Shaped recipe must have a result")

        val resultString = resultSection.getString(ID_KEY) ?: throw InvalidConfigurationException(
            "Recipe result must have an item id"
        )
//            val resultId = NamespacedKey.fromString(resultString) ?: throw InvalidConfigurationException(
//                "$resultString is not a valid item id"
//            )
        val amount = resultSection.getInt(AMOUNT_KEY, 1)

        return ItemStack.of(Material.valueOf(resultString), amount)
    }

    companion object {
        private const val RESULT_KEY = "result"
        private const val ID_KEY = "id"
        private const val AMOUNT_KEY = "amount"
    }
}