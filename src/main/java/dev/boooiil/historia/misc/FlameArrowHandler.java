package dev.boooiil.historia.misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
// Add the Inventory Item Meta
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.worldguard.WorldGuardHandler;

public class FlameArrowHandler {

    static HumanEntity humanEntity;
    static Arrow arrow;

    public static void onShoot(Projectile projectile) {

        if (projectile.getShooter() instanceof Player) {
            if (projectile instanceof Arrow) {

                Player player = (Player) projectile.getShooter();
                ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                // OLD WAY (doesn't work)
                // Damageable item = (Damageable) itemInOffHand.getItemMeta();

                // NEW WAY
                // Get item meta
                ItemMeta item = itemInOffHand.getItemMeta();

                if (itemInMainHand.getType() == Material.BOW && itemInOffHand.getType() == Material.FLINT_AND_STEEL
                        && !itemInMainHand.getEnchantments().containsKey(Enchantment.ARROW_INFINITE)) {

                    arrow = (Arrow) projectile;
                    arrow.setFireTicks(1000);

                    // Check if item is instanceof Damageable
                    if (item instanceof Damageable) {

                        // Get damageable out of the item meta
                        Damageable damageable = ((Damageable) item);

                        // Add 2 to durability
                        int calculatedDurability = damageable.getDamage() + 2;

                        // Break if over 64
                        if (calculatedDurability >= 64) {
                            player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 15, 1);
                            // Otherwise set damage
                        } else {
                            damageable.setDamage(calculatedDurability);
                        }

                        // Set item meta
                        itemInOffHand.setItemMeta(item);
                    }
                }
            }
        }
    }

    public static void onBlockHit(Projectile projectile, Block block) {

        // If the block that was hit isn't on the world "world", we return.
        if (block.getWorld() != Bukkit.getWorld("world"))
            return;

        // If the entity is an arrow we assign it to the variable "arrow".
        if (projectile.getType() == EntityType.ARROW) {
            arrow = (Arrow) projectile;
        } else
            return;

        // If the shooter of the projectile is a human entity, we assign it to
        // "humanEntity".
        if (projectile.getShooter() instanceof HumanEntity) {
            humanEntity = (HumanEntity) projectile.getShooter();
        } else
            return;

        // If the block that was hit is in a worldguard region and they don't have
        // permissions, return.
        if (!WorldGuardHandler.getPermissions((Player) humanEntity, block.getLocation()))
            return;

        // If the flame arrow is stil on fire when it hits the ground.
        if (arrow.getFireTicks() > 0) {

            // Check what block is above the block that was hit and assign it to
            // "fireBlock".
            Block fireBlock = Bukkit.getWorld("world").getBlockAt(block.getX(), block.getY() + 1, block.getZ());

            // If the block is AIR we change it to FIRE.
            // Else, if the block above the one that was hit is flamable, we check to see if
            // we can ignite it.
            if (fireBlock.isEmpty())
                fireBlock.setType(Material.FIRE);
            else if (fireBlock.getType().isFlammable()) {

                Block secondFirebBlock = Bukkit.getWorld("world").getBlockAt(fireBlock.getX(), fireBlock.getY() + 1,
                        fireBlock.getZ());

                if (secondFirebBlock.isEmpty())
                    secondFirebBlock.setType(Material.FIRE);
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