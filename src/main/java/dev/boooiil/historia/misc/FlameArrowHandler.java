package dev.boooiil.historia.misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
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

    public static void onBlockHit(ProjectileHitEvent event) {


    /*
        GET SIDE OF BLOCK (will be north, south, east, west, top, bottom)
        GET IF BLOCK IS IGNITABLE

        IF (BLOCK IGNITABLE)
            IF (TOP) APPLY FIRE TO TOP OF BLOCK (Y+1)
            IF (BOTTOM) APPLY FIRE TO BOTTOM OF BLOCK (Y-1)
            ELSE 
                CALCULATE X Y Z BASED ON SIDE
                SET FIRE TO SIDE OF BLOCK

        ELSE
            IF (BLOCK HIT ON TOP) APPLY FIRE TO TOP OF BLOCK
            ELSE
                CALCULATE SIDE X Y Z
                CHECK IF BLOCK UNDER NEW COORDINATE (Y-1)
                IF (BLOCK EXISTS) SET FIRE TO SIDE OF BLOCK
    */
        Arrow arrow;
        Projectile projectile = event.getEntity();
        Block block = event.getHitBlock();
        BlockFace blockFace = event.getHitBlockFace();
        Block fireBlock;
        
        if (projectile instanceof Arrow){

            arrow = (Arrow) projectile;

            if (arrow.getFireTicks() > 0){

                //boolean ignitable = block.getType().isFlammable();

                //if (ignitable){

                    if (blockFace == BlockFace.DOWN) {

                        fireBlock = Bukkit.getWorld("world").getBlockAt(block.getX(), block.getY() - 1, block.getZ());

                    }
                    else if (blockFace == BlockFace.UP) {

                        fireBlock = Bukkit.getWorld("world").getBlockAt(block.getX(), block.getY() + 1, block.getZ());

                    }
                    else if (blockFace == BlockFace.NORTH) {

                        fireBlock = Bukkit.getWorld("world").getBlockAt(block.getX(), block.getY(), block.getZ() - 1);

                    }
                    else if (blockFace == BlockFace.SOUTH) {

                        fireBlock = Bukkit.getWorld("world").getBlockAt(block.getX(), block.getY(), block.getZ() + 1);

                    }
                    else if (blockFace == BlockFace.EAST) {

                        fireBlock = Bukkit.getWorld("world").getBlockAt(block.getX() + 1, block.getY(), block.getZ());
                        
                    }
                    else if (blockFace == BlockFace.WEST) {

                        fireBlock = Bukkit.getWorld("world").getBlockAt(block.getX() - 1, block.getY(), block.getZ());

                    }
                    else fireBlock = Bukkit.getWorld("world").getBlockAt(block.getX(), 0, block.getZ());


                    if (fireBlock.isEmpty()) fireBlock.setType(Material.FIRE);

                //}

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