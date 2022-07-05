package dev.boooiil.historia.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Construct various Bukkit types.
 */
public class Construct {

    private Construct() { throw new IllegalCallerException("Static utility class."); }

    /**
     * Construct an ItemStack (single item or multiple) based on given parameters.
     * @param type The Material <reference> of the item.
     * @param amount The amount of the item.
     * @param displayName The display name of the item.
     * @param localizedName The (hidden to player) name of the item.
     * @param lore Any flavor text added to the item.
     * @return Constructed item stack.
     */
    public ItemStack itemStack(String type, int amount, String displayName, String localizedName, List<String> lore) {

        //LOGGING TO BE REMOVED AFTER PUBLISH
        Logging.infoToServer("type: " + type + " amount: " + amount + " display-name: " + displayName + " loc-name: " + localizedName + " lore: " + lore);

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
