package dev.boooiil.historia.core.items.recipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record RecipeComponent(ItemStack minecraftItem) {

    public RecipeComponent(Material material) {
        this(new ItemStack(material));
    }

}
