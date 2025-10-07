package dev.boooiil.historia.core.configuration

import dev.boooiil.historia.core.file.FileIO
import dev.boooiil.historia.core.file.FileKeys
import dev.boooiil.historia.core.items.recipe.CustomShapedRecipe
import dev.boooiil.historia.core.items.recipe.CustomShapelessRecipe
import dev.boooiil.historia.core.items.recipe.RecipeType
import dev.boooiil.historia.core.registry.RegistryHolder
import dev.boooiil.historia.core.util.CoreLogger
import org.bukkit.configuration.file.YamlConfiguration
import java.util.function.Supplier

object RecipeLoader {
    private val configuration: YamlConfiguration = FileIO.get(FileKeys.RECIPE)

    private val types = mapOf<String, RecipeType<*>>(
        "shaped" to CustomShapedRecipe.Type,
        "shapeless" to CustomShapelessRecipe.Type,
    )

    fun load() {
        CoreLogger.infoToConsole("Loading recipes...")
        for (key in configuration.getKeys(false)) {
            if (key == "version") continue

            CoreLogger.infoToConsole("Reading recipe $key")
            val section = configuration.getConfigurationSection(key) ?: continue
            val typeKey = section.getString("type")?.lowercase() ?: continue
            val type = types[typeKey] ?: continue

            val recipe = type.fromConfig(key, section)
            RegistryHolder.RECIPE_REGISTRY.register(recipe.key, recipe)
            CoreLogger.infoToConsole("Registered recipe $key")
        }
    }
}