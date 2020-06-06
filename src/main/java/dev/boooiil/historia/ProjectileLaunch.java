package dev.boooiil.historia;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class ProjectileLaunch implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            if (event.getEntity() instanceof Arrow) {

                Player player = (Player) event.getEntity().getShooter();
                ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                
                if (itemInMainHand.getType() == Material.BOW && itemInOffHand.getType() == Material.FLINT_AND_STEEL) {
                    if (!itemInMainHand.getEnchantments().containsKey(Enchantment.ARROW_INFINITE)) {
    
                        Arrow arrow = (Arrow) event.getEntity();
                        arrow.setFireTicks(1000);
    
                        Short calculatedDurability = (short) ( itemInOffHand.getDurability() + 1);
    
                        if (calculatedDurability >= 64) {
                            player.getInventory().setItemInOffHand(new ItemStack(Material.AIR)); player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 15, 1);
                        } else {
                            itemInOffHand.setDurability(calculatedDurability);
                        }
                        
                    }
                }
            }
        }
    }
}