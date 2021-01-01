package dev.boooiil.historia.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Ageable;
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
        // Convert to itemStack and get meta
        Item item = (Item)fishEvent.getCaught();
        ItemStack itemStack = item.getItemStack();
        ItemMeta meta = itemStack.getItemMeta();

        // Debug Print
        // Bukkit.getLogger().info("Item material: " + itemStack.getType());

        // If the type is equal to a cookable fish...
        // Either cod or salmon...
        // Add a weight to it

        // NOTE: If you want to change the value upon smelting, change the value in the FurnaceManager
        // NOTE: Percent chance for base class: 3% Legendary, 7% Large, 15% Medium, 75% Small

        if (itemStack.getType() == Material.COD || itemStack.getType() == Material.SALMON){
            // Create item lore string list
            List<String> itemLore = new ArrayList<String>();

            // Get a new instance of random
            Random random = new Random();

            // New way (fish description)
            Integer randomInteger = random.nextInt(100) + 1;

            if (randomInteger > 97){
                itemLore.add("Legendary" + " catch");
            }
            else if (randomInteger <= 97 && randomInteger > 90){
                itemLore.add("Large" + " catch");
            }
            else if (randomInteger <= 90 && randomInteger > 75){
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

    public static void SheepDeathEvent(EntityDeathEvent e){

        // Debug statment
        // Bukkit.getLogger().info("Sheep Death! " + e.getDrops());

        // Ageable check (we don't want baby dropping items)
        Ageable ageable = (Ageable)e.getEntity();

        if (!ageable.isAdult()) return;


        // Spawned min is the minimum amount to spawn
        Integer spawnedMin = 1;
        Integer spawnedAmount = 0;

        // Add a random value to the spawned amount
        // Returns 1-3
        Random random = new Random();
        spawnedAmount = random.nextInt(3) + spawnedMin;

        // Spawn Material STRING
        e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.STRING, spawnedAmount));

        // ~~~ Add lore values to the meat if any exists

        List<ItemStack> itemStacks = new ArrayList<ItemStack>();
        
        // Get each drop
        for (ItemStack drop : e.getDrops()){
            // if the drop is edible
            if (drop.getType().isEdible()){
                // Add the drop to the item stacks list
                // And set the type of the drop to AIR
                itemStacks.add(new ItemStack(drop.getType(), drop.getAmount()));
                drop.setType(Material.AIR);
            }
        }

        // Set the new drops as well
        // Needed because the item meta can't be set with drops
        for (ItemStack item : itemStacks){
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add(randomMeatLore());
            meta.setLore(lore);
            item.setItemMeta(meta);
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
        }


    }

 

    public static void HorseDeathEvent(EntityDeathEvent e){
        // Debug statment
        // Bukkit.getLogger().info("Sheep Death! " + e.getDrops());

        // Ageable check (we don't want baby dropping items)
        Ageable ageable = (Ageable)e.getEntity();

        if (!ageable.isAdult()) return;

        // Spawned min is the minimum amount to spawn
        Integer spawnedMin = 1;
        Integer spawnedAmount = 0;

        // Add a random value to the spawned amount
        // Returns 1-5
        Random random = new Random();
        spawnedAmount = random.nextInt(5) + spawnedMin;

        // We need to do this because we are spawning meat with a value (Small, Medium, Large)
        ItemStack itemStack = new ItemStack(Material.MUTTON);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(randomMeatLore());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        itemStack.setAmount(spawnedAmount);

        // Spawn mutton
        e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);

    }

    public static void ChickenDeathEvent(EntityDeathEvent e){
        // Debug statment
        // Bukkit.getLogger().info("Sheep Death! " + e.getDrops());

        // Ageable check (we don't want baby dropping items)
        Ageable ageable = (Ageable)e.getEntity();

        if (!ageable.isAdult()) return;

        // Spawned min is the minimum amount to spawn
        Integer spawnedMin = 0;
        Integer spawnedAmount = 0;

        // Add a random value to the spawned amount
        // Returns 0-1
        Random random = new Random();
        spawnedAmount = random.nextInt(2) + spawnedMin;

        if (spawnedAmount > 0){
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.EGG, spawnedAmount));
        }

         // ~~~ Add lore values to the meat if any exists

         List<ItemStack> itemStacks = new ArrayList<ItemStack>();
        
         // Get each drop
         for (ItemStack drop : e.getDrops()){
             // if the drop is edible
             if (drop.getType().isEdible()){
                 // Add the drop to the item stacks list
                 // And set the type of the drop to AIR
                 itemStacks.add(new ItemStack(drop.getType(), drop.getAmount()));
                 drop.setType(Material.AIR);
             }
         }
 
         // Set the new drops as well
         // Needed because the item meta can't be set with drops
         for (ItemStack item : itemStacks){
             ItemMeta meta = item.getItemMeta();
             List<String> lore = new ArrayList<String>();
             lore.add(randomMeatLore());
             meta.setLore(lore);
             item.setItemMeta(meta);
             e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
         }
    }

   // Gets the random meat lore as a string
   public static String randomMeatLore(){

        String str = "Small amount of meat";
        
        // Get a new instance of random
        Random random = new Random();

        // New way (fish description)
        Integer randomInteger = random.nextInt(100) + 1;

        if (randomInteger >= 67){
            str = "Large amount of meat";
        }
        else if (randomInteger < 67 && randomInteger >= 37){
            str = "Medium amount of meat";
        }

        return str;
    }

}
