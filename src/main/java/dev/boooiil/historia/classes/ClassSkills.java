package dev.boooiil.historia.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;

// import dev.boooiil.historia.mysql.UserData;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ClassSkills {
    
    // Skills:
    public static void initiate(Player player){

    }

    public void AddFeatherFalling(InventoryClickEvent event){

        HumanEntity playerEntity = event.getWhoClicked();
        // ItemStack itemClicked = event.getCurrentItem();

        Player player = (Player) playerEntity;
        PlayerInventory inventory = player.getInventory();



        // Get the event action
        if (event.getAction() != InventoryAction.UNKNOWN){
            // Bukkit.getLogger().info("Event: " + event.getAction().toString());
            // If the boots is not null remove the enchantment
            if (event.getCurrentItem() != null && inventory.getBoots() != event.getCurrentItem()){
                if (IsBoots(event.getCurrentItem())){
                    // Get the item and the meta
                    ItemStack item = event.getCurrentItem();
                    ItemMeta meta = item.getItemMeta();
                    // Remove the enchantment if it has one
                    if (meta.hasEnchant(Enchantment.PROTECTION_FALL)) {
                        meta.removeEnchant(Enchantment.PROTECTION_FALL);
                        item.setItemMeta(meta);
                        Bukkit.getLogger().info("REMOVE ENCHANT: Boots Item: " + item.getEnchantments().toString());
                    }         
                }   
            }
            // Else add the enchantment
            if (inventory.getBoots() != null){
                // Get the boots and the meta
                ItemStack item = inventory.getBoots();
                ItemMeta meta = item.getItemMeta();
                // Add the enchantment if there is none
                if (!meta.hasEnchant(Enchantment.PROTECTION_FALL)) {
                    meta.addEnchant(Enchantment.PROTECTION_FALL, 3, false);
                    item.setItemMeta(meta);
                    Bukkit.getLogger().info("ADD ENCHANT: Boots Item: " + item.getEnchantments().toString());
                }           
            }
            
        }
    }

    boolean IsBoots(ItemStack item){
        if (item != null){
            if (item.getType() == Material.LEATHER_BOOTS){
                return true;
            }
        }
        return false;
    }

}