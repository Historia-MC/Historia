package dev.boooiil.historia.core.items.recipe;

import org.bukkit.NamespacedKey;

import java.util.List;

public record ItemRecipe(List<String> ingredientLocation, List<RecipeComponent> components, RecipeMatchType matchType,
                         NamespacedKey resultItem, boolean shaped) {

}
