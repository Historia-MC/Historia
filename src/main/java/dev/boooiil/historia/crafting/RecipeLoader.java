package dev.boooiil.historia.crafting;

//import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class RecipeLoader {

    public static void load(Plugin plugin) {

        NamespacedKey craftingKey = new NamespacedKey(plugin, "test_item");

        ItemStack item = new ItemStack(Material.IRON_SWORD);

        // item.getItemMeta().setDisplayName("Test Iron Sword");
        // item.getItemMeta().setLore(Arrays.asList("This was something", "This was also
        // something"));

        ShapedRecipe testRecipe = new ShapedRecipe(craftingKey, item);

        testRecipe.shape(" E ", " E ", " S ");

        // Set what the letters represent.
        // E = Emerald, S = Stick
        testRecipe.setIngredient('E', Material.EMERALD);
        testRecipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(testRecipe);

    }

}