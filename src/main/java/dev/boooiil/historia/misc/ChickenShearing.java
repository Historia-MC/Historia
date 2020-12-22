package dev.boooiil.historia.misc;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ChickenShearing {
    
    public static void shearChicken(Player player, Entity entity) {

        if (player.getInventory().getItemInMainHand().getType().equals(Material.SHEARS)) {
            
            Ageable chickenAge = (Ageable) entity;

            if (chickenAge.getAge() < 0) return;
            else {
                
                entity.remove();

                Ageable newChicken = (Ageable) player.getWorld().spawnEntity(entity.getLocation(), EntityType.CHICKEN);
                ItemStack feathers = new ItemStack(Material.FEATHER); 

                feathers.setAmount((int) (Math.random() * 3) + 1);
                
                newChicken.setBaby(); 

                player.getWorld().dropItemNaturally(entity.getLocation(), feathers);

                player.giveExp(2);

                ItemStack shears = player.getInventory().getItemInMainHand();

                // Damageable item = (Damageable) shears.getItemMeta();
                
                ItemMeta item = shears.getItemMeta();
                
                if (item instanceof Damageable){
                            
                    // Get damageable out of the item meta
                    Damageable damageable = ((Damageable) item);

                    Short calculatedDurability = (short) ( damageable.getDamage() + 10);

                    if (calculatedDurability >= (int) shears.getType().getMaxDurability()) {
    
                        player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 15, 1);
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR)); player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 15, 1);
                        
                    } else {
    
                        damageable.setDamage(calculatedDurability);
                        player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 15, 1);
                    }

                    shears.setItemMeta(item);
                }

                
                
            }
        }
    }
}