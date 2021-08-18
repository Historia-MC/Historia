package dev.boooiil.historia.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConstructItemStack {

    public static ItemStack construct(String type, int amount, String displayName, String localizedName, List<String> lore) {

        Bukkit.getLogger().info("type: " + type + " amount: " + amount + " display-name: " + displayName + " loc-name: " + localizedName + " lore: " + lore);

        if (type == null) throw new NullPointerException("Type can not be null!");

        ItemStack item = new ItemStack(Material.getMaterial(type), amount);

        ItemMeta meta = item.getItemMeta();

        if (displayName != null) meta.setDisplayName(displayName);
        if (localizedName != null) meta.setLocalizedName(localizedName);
        if (lore != null && !lore.isEmpty()) meta.setLore(lore);

        item.setItemMeta(meta);

        return item;

    }
    
}
