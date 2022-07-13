package dev.boooiil.historia.util;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldedit.entity.Player;

/**
 * Construct various Bukkit types.
 */
public class Construct {

    private Construct() {
        throw new IllegalCallerException("Static utility class.");
    }

    /**
     * Construct an ItemStack (single item or multiple) based on given parameters.
     * 
     * @param type          The Material <reference> of the item.
     * @param amount        The amount of the item.
     * @param displayName   The display name of the item.
     * @param localizedName The (hidden to player) name of the item.
     * @param lore          Any flavor text added to the item.
     * @return Constructed item stack.
     */
    public static ItemStack itemStack(Material material, int amount, String displayName, String localizedName,
            List<String> lore) {

        // LOGGING TO BE REMOVED AFTER PUBLISH
        Logging.infoToServer("material: " + material.toString() + " amount: " + amount + " display-name: " + displayName
                + " loc-name: "
                + localizedName + " lore: " + lore);

        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(displayName);
        if (localizedName != null)
            meta.setLocalizedName(localizedName);
        if (lore != null && !lore.isEmpty())
            meta.setLore(lore);

        item.setItemMeta(meta);

        return item;

    }

    public static boolean blockReplacement(Player player, Block brokenBlock, Material newBlock, Sound sound,
            List<HashMap<String, String>> givenItems) {

        // If the size of the list is greater than 0
        // IE, if there are items to be given to the player
        if (givenItems.size() > 0) {

            brokenBlock.getDrops().clear();

            // Iterate through the given item list
            for (HashMap<String, String> item : givenItems) {

                Material material = Material.matchMaterial(item.get("material"));
                Integer amount = Integer.getInteger(item.get("amount"));
                String displayName = item.get("display-name");
                String localizedName = item.get("localized-name");
                List<String> lore = null;

                ItemStack droppedItem = Construct.itemStack(material, amount, displayName, localizedName, lore);

                brokenBlock.getDrops().add(droppedItem);

            }

            return true;

        }

        return false;

    }

}
