package dev.boooiil.historia;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import dev.boooiil.historia.worldguard.WorldGuardHandler;

public class ProjectileHit implements Listener {

    @EventHandler
    public void onArrowInBlock(ProjectileHitEvent event) {
        if (event.getHitEntity() == null) {
            if (event.getHitBlock().getWorld() == Bukkit.getWorld("world")) {

                if (event.getEntity().getShooter() instanceof HumanEntity) {

                    HumanEntity humanEntity = (HumanEntity) event.getEntity().getShooter();

                    if (!WorldGuardHandler.getPermissions((Player) humanEntity, event.getHitBlock().getLocation())) return;
                }

                if (event.getEntity().getType() == EntityType.ARROW) {

                    Arrow arrow = (Arrow) event.getEntity();

                    if (arrow.getFireTicks() > 0) {

                        Block fireBlock = Bukkit.getWorld("world").getBlockAt(event.getHitBlock().getX(), event.getHitBlock().getY() + 1, event.getHitBlock().getZ());

                        if (fireBlock.isEmpty()) fireBlock.setType(Material.FIRE);
                        else if (fireBlock.getType().isFlammable()) {

                            Block newFireBlock = Bukkit.getWorld("world").getBlockAt(fireBlock.getX(), fireBlock.getY() + 1, fireBlock.getZ());

                            if (newFireBlock.isEmpty()) newFireBlock.setType(Material.FIRE);

                        }
                    }
                }
            }
        }
    }

    public static void newEvent(Entity entity) {
        /*
         * if (event.getEntity().getShooter() instanceof HumanEntity) {
         * newEvent(event.getEntity()); HumanEntity humanEntity = (HumanEntity)
         * event.getEntity().getShooter();
         * 
         * if (!WorldGuardHandler.getPermissions((Player) humanEntity,
         * event.getHitBlock().getLocation())) return;
         * 
         * ItemStack itemInMainHand = humanEntity.getInventory().getItemInMainHand();
         * ItemStack itemInOffHand = humanEntity.getInventory().getItemInOffHand();
         * 
         * if (itemInMainHand.getType() == Material.BOW || itemInOffHand.getType() ==
         * Material.BOW) { if
         * (itemInMainHand.getEnchantments().containsKey(Enchantment.ARROW_FIRE) ||
         * itemInOffHand.getEnchantments().containsKey(Enchantment.ARROW_FIRE)) {
         * 
         * Block fireBlock =
         * Bukkit.getWorld("world").getBlockAt(event.getHitBlock().getX(),
         * event.getHitBlock().getY() + 1, event.getHitBlock().getZ());
         * 
         * if (fireBlock.isEmpty()) fireBlock.setType(Material.FIRE); else if
         * (fireBlock.getType().isFlammable()) {
         * 
         * Block newFireBlock = Bukkit.getWorld("world").getBlockAt(fireBlock.getX(),
         * fireBlock.getY() + 1, fireBlock.getZ());
         * 
         * if (newFireBlock.isEmpty()) newFireBlock.setType(Material.FIRE); } } } }
         */
    }
}