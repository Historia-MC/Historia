package dev.boooiil.historia.crafting;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import dev.boooiil.historia.Config;

public class RecipeLoader {

    private RecipeLoader() { throw new IllegalStateException("Static utility class."); }

    public static void disableRecipes() {

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("wood_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_sword"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("wood_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_axe"));
        
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_pickaxe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_shovel"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_hoe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_nugget"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_nugget"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_boots"));


    }

    public static void loadRecipes() {

        NamespacedKey ironIngotUpgrade = new NamespacedKey(Bukkit.getPluginManager().getPlugin("Historia"), "iron_ingot_upgrade");

        FurnaceRecipe ironRecipe = new FurnaceRecipe(ironIngotUpgrade, new ItemStack(Material.IRON_INGOT), Material.IRON_INGOT, 0, 200);

        Bukkit.addRecipe(ironRecipe);

        NamespacedKey goldIngotUpgrade = new NamespacedKey(Bukkit.getPluginManager().getPlugin("Historia"), "gold_ingot_upgrade");

        FurnaceRecipe goldRecipe = new FurnaceRecipe(goldIngotUpgrade, new ItemStack(Material.GOLD_INGOT), Material.GOLD_INGOT, 0, 200);

        Bukkit.addRecipe(goldRecipe);

    }

    public static void load(Plugin plugin){

        // For each weapon in the config weapon list
        // (Loading Weapons)
        for (String weapon : Config.getWeaponSet()) {

            // Get the item stack of the weapon
            ItemStack item = (ItemStack) Config.getWeaponInfo(weapon).get("ITEM");

            // Get the item meta
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();

            // The display name shows the name of the weapon
            // While the localized name is used to determine the weapon type in code
            meta.setDisplayName("§a" + meta.getDisplayName());
            meta.setLocalizedName(meta.getLocalizedName());

            lore.add("");
            lore.add("§9-----------------");
            lore.add("");
            lore.add("§7Damage - §k?");
            lore.add("§7Sweeping - §k?");
            lore.add("§7Knockback - §k?");
            lore.add("§7Speed - §k?");
            lore.add("");

            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

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
            List<String> shape = (List<String>) Config.getWeaponInfo(weapon).get("SHAPE");

            // Set recipe shape (through Config.java)
            recipe.shape(shape.get(0), shape.get(1), shape.get(2));

            // Get recipe ingredients and keys (through Config.java)
            List<String> items = (List<String>) Config.getWeaponInfo(weapon).get("RECIPE");

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
        for (String armor : Config.getArmorSet()) {

            // Get the item stack of the weapon
            ItemStack item = (ItemStack) Config.getArmorInfo(armor).get("ITEM");

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
            List<String> shape = (List<String>) Config.getArmorInfo(armor).get("SHAPE");

            // Debug in case the recipe loader shape breaks
            // Bukkit.getLogger().info("ARMOR! " + armor);
            // Bukkit.getLogger().info(":" + shape.get(0) + ":" + shape.get(1) + ":" + shape.get(2) + ":");

            // Set recipe shape (through Config.java)
            recipe.shape(shape.get(0), shape.get(1), shape.get(2));

            // Get recipe ingredients and keys (through Config.java)
            List<String> items = (List<String>) Config.getArmorInfo(armor).get("RECIPE");

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

}