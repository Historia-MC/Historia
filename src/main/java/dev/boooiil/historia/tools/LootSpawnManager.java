package dev.boooiil.historia.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.meta.ItemMeta;

public class LootSpawnManager {
    void initiate() {

    }
    
    public void SpawnFishingLoot(PlayerFishEvent fishEvent){

        // If the event is not fishing related, return
        if (!(fishEvent instanceof PlayerFishEvent)) return;

        // If we haven't caught anything, return
        if (fishEvent.getCaught() == null) return;

        // Debug Print
        Bukkit.getLogger().info("Fishing Event! Caught: " + fishEvent.getCaught().getName());

        // Cast fishEvent.getCaught() to an Item
        // Convert to itemStack
        // Get the item meta as well
        Item item = (Item)fishEvent.getCaught();
        ItemStack itemStack = item.getItemStack();
        ItemMeta meta = itemStack.getItemMeta();

        // Debug Print
        Bukkit.getLogger().info("Item material: " + itemStack.getType());

        // Create item lore string list
        List<String> itemLore = new ArrayList<String>();

        // Add the string to the lore
        itemLore.add("10" + " lbs");

        // Set the lore
        meta.setLore(itemLore);

        // Set item Meta
        itemStack.setItemMeta(meta);

    }

}
