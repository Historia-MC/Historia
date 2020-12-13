package dev.boooiil.historia.crafting;

// import java.security.KeyStore.Entry.Attribute;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
//import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import dev.boooiil.historia.Config;
//import net.java.truecommons.key.macosx.keychain.Keychain.AttributeClass;

public class RecipeLoader {

    public static void load(Plugin plugin) {

        // ~~~ Iterate through weapon.List and armor.List in config ~~~

        Config weaponConfig = new Config("");
        Config armorConfig = new Config("");
        Map<String, ItemStack> items = new HashMap<>();
        
        Bukkit.getLogger().info("~~~ Weapons ~~~");

        int i = 0;

        for (String weapon : weaponConfig.weapons) {
            Config found = new Config(weapon);
            items.put(weapon, found.weaponItem);

            // Bukkit.getLogger().info("~Armor Name: " + armor);

            ItemStack item = new ItemStack(found.weaponItem);

            ItemMeta meta = item.getItemMeta();

            // We will initialise the next variable after changing the properties of the sword
            // This sets the name of the item.
            // Instead of the § symbol, you can use ChatColor.<color>
            meta.setDisplayName("§a" + meta.getDisplayName());
            meta.setLocalizedName(meta.getLocalizedName());

            // Set item damage
            AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

             // Set the meta of the sword to the edited meta.
            item.setItemMeta(meta);

            // create a NamespacedKey for your recipe
            NamespacedKey craftingKey = new NamespacedKey(plugin, meta.getLocalizedName().toLowerCase());

            // Create our custom recipe variable
            ShapedRecipe recipe = new ShapedRecipe(craftingKey, item);

            // RECIPES: 
            // How they work:
            // recipe.shape("XXX", "XXX", "XXX");
            // Where Each "X" is a space on the crafting bench

            // IMPORTNAT!
            // Weapon values by ID:
            // 0 = placeholder sword
            if (i == 0)
            {
                recipe.shape(" I ", " I ", " S ");
            }
            if (i == 1)
            {

            }
            if (i == 2)
            {

            }
            if (i == 3)
            {

            }

            

            // Set what the letters represent.
            // I = Iron Ingot, S = Stick
            recipe.setIngredient('I', Material.IRON_INGOT);
            recipe.setIngredient('S', Material.STICK);
            // recipe.setIngredient('S', Material.STICK);

            // Finally, add the recipe to the bukkit recipes
            Bukkit.addRecipe(recipe);

            //Bukkit.getLogger().info("~~~WE DID IT!~~~");


            i++;
        }

        // ~~~ ARMOR ~~~
        Bukkit.getLogger().info("~~~ Armor ~~~");

        // For testing purposes only
        i = 0;

        for (String armor : armorConfig.armor) {

            Config found = new Config(armor);
            items.put(armor, found.weaponItem);

            // Bukkit.getLogger().info("~Armor Name: " + armor);

            ItemStack item = new ItemStack(found.armorItem);

            ItemMeta meta = item.getItemMeta();

            // We will initialise the next variable after changing the properties of the sword
            // This sets the name of the item.
            // Instead of the § symbol, you can use ChatColor.<color>
            meta.setDisplayName("§a" + meta.getDisplayName());
            meta.setLocalizedName(meta.getLocalizedName());

             // Set the meta of the sword to the edited meta.
            item.setItemMeta(meta);

            // create a NamespacedKey for your recipe
            NamespacedKey craftingKey = new NamespacedKey(plugin, meta.getLocalizedName().toLowerCase());

            // Create our custom recipe variable
            ShapedRecipe recipe = new ShapedRecipe(craftingKey, item);

            // RECIPES: 
            // How they work:
            // recipe.shape("XXX", "XXX", "XXX");
            // Where Each "X" is a space on the crafting bench

            // IMPORTNAT!
            // Armor values by ID:
            // 0 = placeholder helmet
            // 1 = placeholder chest
            // 2 = placeholder leggings
            // 3 = placeholder boots
            if (i == 0)
            {
                recipe.shape("III", "I I", "   ");
            }
            if (i == 1)
            {
                recipe.shape("I I", "III", "III");
            }
            if (i == 2)
            {
                recipe.shape("III", "I I", "I I");
            }
            if (i == 3)
            {
                recipe.shape("   ", "I I", "I I");
            }

            // Here we will set the places. E and S can represent anything, and the letters can be anything. Beware; this is case sensitive.
            // recipe.shape("III", "I I", "   ");

            // Set what the letters represent.
            // I = Iron Ingot, S = Stick
            recipe.setIngredient('I', Material.IRON_INGOT);
            // recipe.setIngredient('S', Material.STICK);

            // Finally, add the recipe to the bukkit recipes
            Bukkit.addRecipe(recipe);

            //Bukkit.getLogger().info("~~~WE DID IT!~~~");


            i++;
        }

        Bukkit.getLogger().info("~~~");


    }

}