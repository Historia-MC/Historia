package dev.boooiil.historia.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import dev.boooiil.historia.mysql.UserData;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ClassSkills {
    
    // Skills:
    public static void initiate(Player player){

    }

    public void AddFeatherFalling(ItemStack item, HumanEntity entity){

        if (item != null && item.getType() == Material.LEATHER_BOOTS){
            // Get player from Human Entity and get the user data
            Player player = (Player) entity;
            UserData user = new UserData(player);

            // Return if not architect
            if (user.getClassName().equals("Architect")){
                // Get the inventory
                PlayerInventory inventory = player.getInventory();

                // Get the boots in the inventory

                // If the type is not air...
                // ...Continue otherwise return
                if (item.getType() != Material.AIR){
                    
                    // If there is not already  feather falling boots...
                    // ...Add feather falling otherwise return
                    ItemMeta meta = item.getItemMeta();
                    //if (inventory.getBoots() == item){
                        if (!meta.hasEnchant(Enchantment.PROTECTION_FALL)){
                            meta.addEnchant(Enchantment.PROTECTION_FALL, 3, false);
                            item.setItemMeta(meta);
                        }
                    //}
            
                }
            
            }

        }

    }
}