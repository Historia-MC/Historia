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

    // A private constructor that throws an exception if it is called.
    private Construct() {
        throw new IllegalCallerException("Static utility class.");
    }

    /**
     * It creates an ItemStack with the given parameters
     * 
     * @param material The material of the item.
     * @param amount The amount of the item
     * @param displayName The name that will be displayed on the item.
     * @param localizedName The name of the item in the language file.
     * @param lore The lore of the item.
     * @return An ItemStack
     */
    public static ItemStack itemStack(String material, int amount, String displayName, String localizedName,
            List<String> lore) {

        // LOGGING TO BE REMOVED AFTER PUBLISH
        Logging.infoToConsole("material: " + material + " amount: " + amount + " display-name: " + displayName
                + " loc-name: "
                + localizedName + " lore: " + lore);

        ItemStack item = new ItemStack(Material.getMaterial(material, false), amount);

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

    /**
     * It takes a list of items, and replaces the drops of a block with those items
     * 
     * @param player The player who broke the block
     * @param brokenBlock The block that was broken
     * @param newBlock The material of the block that will replace the broken block
     * @param sound The sound to be played when the block is broken
     * @param givenItems A list of HashMaps that contain the following keys:
     * @return A boolean value.
     */
    public static boolean blockReplacement(Player player, Block brokenBlock, Material newBlock, Sound sound,
            List<HashMap<String, String>> givenItems) {

        // If the size of the list is greater than 0
        // IE, if there are items to be given to the player
        if (givenItems.size() > 0) {

            brokenBlock.getDrops().clear();

            // Iterate through the given item list
            for (HashMap<String, String> item : givenItems) {

                Integer amount = Integer.getInteger(item.get("amount"));
                String displayName = item.get("display-name");
                String localizedName = item.get("localized-name");
                List<String> lore = null;

                ItemStack droppedItem = Construct.itemStack(item.get("material"), amount, displayName, localizedName, lore);

                brokenBlock.getDrops().add(droppedItem);

            }

            return true;

        }

        return false;

    }

}
