package dev.boooiil.historia.crafting;

// import java.security.KeyStore.Entry.Attribute;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import dev.boooiil.historia.Config;

public class RecipeLoader {

    public static void load(Plugin plugin){

        // Create new config
        Config config = new Config();

        // For each weapon in the config weapon list
        // (Loading Weapons)
        for (String weapon : config.getWeaponList()) {

            // Get the item stack of the weapon
            ItemStack item = (ItemStack) config.getWeaponInfo(weapon).get("ITEM");

            // Get the item meta
            ItemMeta meta = item.getItemMeta();

            // The display name shows the name of the weapon
            // While the localized name is used to determine the weapon type in code
            meta.setDisplayName("§a" + meta.getDisplayName());
            meta.setLocalizedName(meta.getLocalizedName());

            // Get stats
            double damage = (double)config.getWeaponInfo(weapon).get("DAMAGE");
            double knockback = (double)config.getWeaponInfo(weapon).get("KNOCKBACK");
            double sweeping = (double)config.getWeaponInfo(weapon).get("SWEEPING");

            // Set weapon attributes (set to 100 for testing purposes) 
            AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            AttributeModifier knockbackModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackKnockback", knockback, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            // AttributeModifier sweepignModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackSweeping", sweeping, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, knockbackModifier);
            // meta.addAttributeModifier(Attribute., modifier)


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

            // Get the shape (through Config.java)
            List<String> shape = (List<String>) config.getWeaponInfo(weapon).get("SHAPE");

            // Set recipe shape (through Config.java)
            recipe.shape(shape.get(0), shape.get(1), shape.get(2));

            // Get recipe ingredients and keys (through Config.java)
            List<String> items = (List<String>) config.getWeaponInfo(weapon).get("RECIPE");

            // The letters used to identify the ingredients
            // A = 0th element, B = 1st element, and so on
            String upperAlphabet = "ABCDEFGHIJ";

            // Add recipe ingredients
            for (int i = 0; i < items.size(); i++){
                Material material = Material.getMaterial(items.get(i));
                recipe.setIngredient(upperAlphabet.charAt(i), material);
            }

            // Finally, add the recipe to the bukkit recipes
            Bukkit.addRecipe(recipe);

        }

        // For each armor in the config weapon list
        // (Loading Armor)
        for (String armor : config.getArmorList()) {

            // Get the item stack of the weapon
            ItemStack item = (ItemStack) config.getArmorInfo(armor).get("ITEM");

            // Get the item meta
            ItemMeta meta = item.getItemMeta();

            // The display name shows the name of the weapon
            // While the localized name is used to determine the weapon type in code
            meta.setDisplayName("§a" + meta.getDisplayName());
            meta.setLocalizedName(meta.getLocalizedName());

            // Set armor attributes (set to 100 for testing purposes) 
            // AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            // meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

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

            // Get the shape (through Config.java)
            List<String> shape = (List<String>) config.getArmorInfo(armor).get("SHAPE");

            // Set recipe shape (through Config.java)
            recipe.shape(shape.get(0), shape.get(1), shape.get(2));

            // Get recipe ingredients and keys (through Config.java)
            List<String> items = (List<String>) config.getArmorInfo(armor).get("RECIPE");

            // The letters used to identify the ingredients
            // A = 0th element, B = 1st element, and so on
            String upperAlphabet = "ABCDEFGHIJ";

            // Add recipe ingredients
            for (int i = 0; i < items.size(); i++){
                Material material = Material.getMaterial(items.get(i));
                recipe.setIngredient(upperAlphabet.charAt(i), material);
            }

            // Finally, add the recipe to the bukkit recipes
            Bukkit.addRecipe(recipe);

        }


    }

    public static void loadLegacy(Plugin plugin) {

        // ~~~ Iterate through weapon.List and armor.List in config ~~~

        Config config = new Config();

        //You don't technically need this
        
        Bukkit.getLogger().info("~~~ Weapons ~~~");

        int i = 0;

        for (String weapon : config.getWeaponList()) {

            Bukkit.getLogger().info("~Armor Name: " + weapon);
            Bukkit.getLogger().info(config.getWeaponInfo(weapon).toString());

            ItemStack item = (ItemStack) config.getWeaponInfo(weapon).get("ITEM");

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

        for (String armor : config.getArmorList()) {

            ItemStack item = (ItemStack) config.getArmorInfo(armor).get("ITEM");

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