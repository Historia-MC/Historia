package dev.boooiil.historia.expiry;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.boooiil.historia.Config;

public class ExpiryManager {

    /*
        When the event fires we get 3 options from the events I've seen

        Event1 -> Player and ItemStack (Pickup Event, Drop Event)
        Event2 -> Player, Inventory Slot (Inventory Interact Event, Container Interact Event)
        Event3 -> Player, Old Item Slot, New Item Slot (Player Item Held Event)

        For calculating the correct time to apply to exipred items we need to do four things:
            Get the current EPOCH date
            Convert the amount of days to milliseconds
            Add the milliseconds to the EPOCH time
            Create a new date based on that new time
        
        Once we get those values we can apply the correct day, month, and year. We have to do this since we can't conveniently
        add the amount of days to a month and expect a correct result.
    */
    
    ItemStack foundItem;
    Player player;
    boolean expired;
    String expiryLore;
    ItemMeta newExpiryMeta;

    //These values will stay until the plugin gets unloaded0.

    public void initiate(ItemStack itemStack, HumanEntity humanEntity) {
        
        //We have to check if we were provided an item and if the item is edible, since we are dealing with food.
        if (itemStack != null && itemStack.getType().isEdible() && !itemStack.getItemMeta().hasLore()) { foundItem = itemStack; }
        
        //NPCs count as human entities, it is more sane to check this way in case NPCs fire off an event.
        //We are checking if the human entity is an actual player.
        if (humanEntity instanceof Player) { player = (Player) humanEntity; }
        else { System.out.println("[Historia] Expected Player but got " + humanEntity.getName() + " which is of type " + humanEntity.getEntityId()); return; }

        //If we don't have an item (happens if the item wasn't edible)
        //If we don't have a player (happens if the entity was an NPC)
        if (foundItem == null || player == null) return;

        //Check if the food item has lore (all existing expired food will have lore)
        if (foundItem.getItemMeta().hasLore()) {
            
            //This returns the boolean expired, showing if the food item was expired or not.
            checkIfExpired(foundItem);

            //If the food item was expired we need to change the lore on the item.
            if (expired) setExpired(foundItem);

        } else setExpiry(foundItem); //If there wasn't lore, apply new lore.

        //At this point we have the new lore to be applied.
        //If we are using an inventory slot, apply it here.
        //If we don't have the slot, apply the lore to the item stack that currently exists.
        //System.out.println("[START]\nFound Item: " + foundItem + "\nInventory Slot: " + "\nItem Stack: " + itemStack + "\nPlayer: " + player + "\nExpired: " + expired + "\nExpiry Lore: " + expiryLore + "\nNew Meta: " + newExpiryMeta + "\n[END]");

        foundItem.setItemMeta(newExpiryMeta);

    }

    private ItemMeta setExpiry(ItemStack item) {

        Calendar calendar = Calendar.getInstance();
        ItemMeta meta = item.getItemMeta();

        // START William: This should get from the config the expiry data
        Integer expiry = (Integer)Config.getFoodInfo(item.getType().toString()).get("EXPIRY");

        //Calculate the new date based on the values.
        calendar.add(Calendar.DATE, expiry);

        // Set the lore
        meta.setLore(Arrays.asList("Expiry: " + (calendar.get(Calendar.MONTH) + 1)  + "-" + calendar.get(Calendar.DATE) + "-" + calendar.get(Calendar.YEAR))); 

        // END WILLIAM
        return newExpiryMeta = meta;

    }

    private ItemMeta setExpired(ItemStack item) {

        ItemMeta meta = item.getItemMeta();
        
        meta.setLore(Arrays.asList("Spoiled!")); 

        return newExpiryMeta = meta;

    }
    
    private boolean checkIfExpired(ItemStack itemStack) {

        for (String line : itemStack.getItemMeta().getLore()) { if (line.contains("Expiry: ")) expiryLore = line; };

        String[] compare = expiryLore.replace("Expiry: ", "").split("-");

            Calendar date = Calendar.getInstance();

            int year = 0; int month = 0; int day = 0;

            month = (int) Integer.parseInt(compare[0]);
            day = (int) Integer.parseInt(compare[1]);
            year = (int) Integer.parseInt(compare[2]);
                        
            if (year < date.get(Calendar.YEAR)) return expired = true;
            else if (month < (date.get(Calendar.MONTH) + 1)) return expired = true;
            else if (month == (date.get(Calendar.MONTH) + 1) && day < date.get(Calendar.DATE)) return expired = true;
            else return expired = false;

    }

    public boolean handleExpiredFood(ItemStack item, Player player) {

        if (item.getItemMeta().hasLore()) {
            for (String lore :item.getItemMeta().getLore()) {

                if (lore.contains("Expiry: ") || lore.contains("expiry")) {

                    for (String word : lore.split(" ")) {

                        if (word.contains("-")) {
                            String[] compare = word.split("-");

                            Calendar date = Calendar.getInstance();

                            int year = 0; int month = 0; int day = 0;

                            if (lore.contains("Expiry: ")) {
                                month = (int) Integer.parseInt(compare[0]);
                                day = (int) Integer.parseInt(compare[1]);
                                year = (int) Integer.parseInt(compare[2]);
                            }
                            else if (lore.contains("expiry ")) {
                                year = (int) Integer.parseInt(compare[0]);
                                month = (int) Integer.parseInt(compare[1]);
                                day = (int) Integer.parseInt(compare[2]);
                            } else return false;

                            if (year < date.get(Calendar.YEAR)) {

                                PotionEffect posion = new PotionEffect(PotionEffectType.POISON, 120, 1);
                                PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER, 600, 2);
                                

                                player.sendMessage("§7[§9Historia§7] §cThat food was bad!");
                                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                                posion.apply(player); hunger.apply(player); return true;
                                
                            } 
                            else if (month < (date.get(Calendar.MONTH) + 1)) {

                                PotionEffect posion = new PotionEffect(PotionEffectType.POISON, 120, 1);
                                PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER, 600, 2);
                                

                                player.sendMessage("§7[§9Historia§7] §cThat food was bad!");
                                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                                posion.apply(player); hunger.apply(player); return true;
                            }
                            else if (month == (date.get(Calendar.MONTH) + 1) && day < date.get(Calendar.DATE)) {

                                PotionEffect posion = new PotionEffect(PotionEffectType.POISON, 120, 1);
                                PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER, 600, 2);
                                

                                player.sendMessage("§7[§9Historia§7] §cThat food was bad!");
                                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                                posion.apply(player); hunger.apply(player); return true;
                            } else return false;
                            
                        } return false;
                    }
                } return false;
            }
        } else { item.setItemMeta(setExpiry(item)); } return false;



    }
}