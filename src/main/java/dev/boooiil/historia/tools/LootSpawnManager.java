package dev.boooiil.historia.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.meta.ItemMeta;

public class LootSpawnManager {
    
    public void SpawnFishingLoot(PlayerFishEvent fishEvent){

        // If the event is not fishing related, return
        if (!(fishEvent instanceof PlayerFishEvent)) return;

        // If we haven't caught anything, return
        if (fishEvent.getCaught() == null) return;

        // Debug statement (COMNMENT OUT UNLESS YOU WANT A LOT OF MESSAGES!)
        // Bukkit.getLogger().info("Fishing Event! Caught: " + fishEvent.getCaught().getName());

        // Cast fishEvent.getCaught() to an Item
        // Convert to itemStack
        // Get the item meta as well
        Item item = (Item)fishEvent.getCaught();
        ItemStack itemStack = item.getItemStack();
        ItemMeta meta = itemStack.getItemMeta();

        // Debug Print
        // Bukkit.getLogger().info("Item material: " + itemStack.getType());

        // If the type is equal to a cookable fish...
        // Either cod or salmon...
        // Add a weight to it
        if (itemStack.getType() == Material.COD || itemStack.getType() == Material.SALMON){
            // Create item lore string list
            List<String> itemLore = new ArrayList<String>();

            // Get a new instance of random
            Random random = new Random();

            // OLD WAY (pounds)
            /*
            // Create a random int from 0-29 and add 1 so we never get 0
            // it never reaches 30 unless we add 1!
            Integer lbs = random.nextInt(30) + 1;

            // Add the string to the lore
            itemLore.add(lbs.toString() + " lbs");
            */

            // New way (fish description)
            Integer randomInteger = random.nextInt(100) + 1;

            if (randomInteger > 97){
                itemLore.add("Legendary" + " catch");
            }
            else if (randomInteger < 97 && randomInteger > 90){
                itemLore.add("Large" + " catch");
            }
            else if (randomInteger < 90 && randomInteger > 75){
                itemLore.add("Medium" + " catch");
            }
            else{
                itemLore.add("Small" + " catch");
            }

            // Set the lore
            meta.setLore(itemLore);

            // Set item Meta
            itemStack.setItemMeta(meta);
        }
        
        

    }

}
