package dev.boooiil.historia.misc;

import java.util.Arrays;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class StoneCutter {

    public static void onRightClick(ItemStack item) {

        if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
            
            int enchLevel = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            
            if (enchLevel == 1) { item.addEnchantment(Enchantment.DAMAGE_ALL, 2); item.getItemMeta().setLore(Arrays.asList("Sharpness II: 20/20")); }
            if (enchLevel == 2) { item.addEnchantment(Enchantment.DAMAGE_ALL, 3); item.getItemMeta().setLore(Arrays.asList("Sharpness III: 10/10")); }
            if (enchLevel == 3) { item.addEnchantment(Enchantment.DAMAGE_ALL, 3); item.getItemMeta().setLore(Arrays.asList("Sharpness III: 10/10")); }

        } else { item.addEnchantment(Enchantment.DAMAGE_ALL, 1); item.getItemMeta().setLore(Arrays.asList("Sharpness I: 30/30")); }
    }
}