package dev.boooiil.historia.crafting;

import java.util.Arrays;

//import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class RecipeLoader {

    public static void load(Plugin plugin) {

        // NamespacedKey craftingKey = new NamespacedKey(plugin, "test_item");

        // ItemStack item = new ItemStack(Material.IRON_SWORD);

        // TEST ITEM
        /*
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
        */

        // ~~~ Nerfed Axe ~~~

        // Our custom variable which we will be changing around.
        ItemStack item = new ItemStack(Material.IRON_AXE);

        // The meta of the diamond sword where we can change the name, and properties of the item.
        ItemMeta meta = item.getItemMeta();

        // We will initialise the next variable after changing the properties of the sword
        // This sets the name of the item.
        // Instead of the § symbol, you can use ChatColor.<color>
        meta.setDisplayName("§aNerfed Axe");
        meta.setLocalizedName("Nerfed_Axe");

        // Set the meta of the sword to the edited meta.
        item.setItemMeta(meta);

        // create a NamespacedKey for your recipe
        NamespacedKey craftingKey = new NamespacedKey(plugin, "nerfed_axe");

        // Create our custom recipe variable
        ShapedRecipe recipe = new ShapedRecipe(craftingKey, item);

        // Debug
        /*
        String shape = recipe.getShape().toString();
        Bukkit.getLogger().info(shape);
        */

        // RECIPES: 
        // How they work:
        // recipe.shape("XXX", "XXX", "XXX");
        // Where Each "X" is a space on the crafting bench

        // Here we will set the places. E and S can represent anything, and the letters can be anything. Beware; this is case sensitive.
        recipe.shape("II ", "IS ", " S ");

        // Debug
        /*
        shape = recipe.getShape().toString();
        Bukkit.getLogger().info(shape);
        */

        // Set what the letters represent.
        // I = Iron Ingot, S = Stick
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('S', Material.STICK);

        // Finally, add the recipe to the bukkit recipes
        Bukkit.addRecipe(recipe);

        // DEBUG
        String fullName = "Display Name: " + meta.getDisplayName() + "\n Localized Name " + meta.getLocalizedName();

        Bukkit.getLogger().info(fullName);

    }

}