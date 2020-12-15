package dev.boooiil.historia;

import java.util.Date;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.alerts.BoatNotify;
import dev.boooiil.historia.alerts.DeathNotify;
import dev.boooiil.historia.classes.ClassManager;
import dev.boooiil.historia.expiry.ExpiryManager;
import dev.boooiil.historia.misc.ChickenShearing;
import dev.boooiil.historia.misc.FlameArrowHandler;
import dev.boooiil.historia.misc.OreDrops;
import dev.boooiil.historia.misc.PreventLavaPickup;
import dev.boooiil.historia.misc.ReplaceBlocks;
import dev.boooiil.historia.misc.StoneCutterItem;
//import dev.boooiil.historia.misc.StoneCutter;
import dev.boooiil.historia.mysql.UserData;
import dev.boooiil.historia.tools.DamageManager;
import dev.boooiil.historia.towny.TownyHandler;
import dev.boooiil.historia.worldguard.WorldGuardHandler;

public class HistoriaEvents implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        FlameArrowHandler.onShoot(event.getEntity());
    }
    
    @EventHandler
    public void onArrowInBlock(ProjectileHitEvent event) {
        
        //If the arrow didn't hit an entity
        if (event.getHitEntity() == null) FlameArrowHandler.onBlockHit(event);

    }

    @EventHandler
    public void onPlayerScrollHotbar(PlayerItemHeldEvent event) {

        if (event.getPlayer().getInventory().getItem(event.getNewSlot()) != null) {
            ExpiryManager manager = new ExpiryManager();
            manager.initiate(event.getPlayer().getInventory().getItem(event.getNewSlot()), event.getPlayer());
        } 

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ExpiryManager manager = new ExpiryManager();
        manager.initiate(event.getCurrentItem(), event.getWhoClicked());
        
    }

    @EventHandler
    public void onBoatInteract(PlayerInteractEntityEvent event) {

        if (event.getRightClicked().getType() == EntityType.BOAT && event.getHand().equals(EquipmentSlot.HAND)) BoatNotify.boatNotify(event.getHand(), event.getPlayer(), event.getRightClicked());
        if (event.getRightClicked().getType() == EntityType.CHICKEN && event.getHand().equals(EquipmentSlot.HAND)) ChickenShearing.shearChicken(event.getPlayer(), event.getRightClicked());
        
    }

    @EventHandler
    public void onPickupLava(PlayerBucketFillEvent event) {
        
        if (event.getBlock().getType() == Material.LAVA) {
            if (PreventLavaPickup.onPickup(event.getPlayer())) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        ExpiryManager manager = new ExpiryManager();
        if (manager.handleExpiredFood(event.getItem(), event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        
        DeathNotify.deathAlert(event.getEntity(), event.getDeathMessage());
        
    }
    
    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        ExpiryManager manager = new ExpiryManager();
        manager.initiate(event.getItem().getItemStack(), (HumanEntity) event.getPlayer());
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ExpiryManager manager = new ExpiryManager();
        manager.initiate(event.getItemDrop().getItemStack(), (HumanEntity) event.getPlayer());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        ClassManager manager = new ClassManager();
        manager.initiate(event.getPlayer());

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!event.hasBlock()) return;
        if (!WorldGuardHandler.getPermissions(event.getPlayer(), event.getClickedBlock().getLocation())) return;
        if (!TownyHandler.getPermissions(event.getPlayer(), event.getClickedBlock().getLocation(), event.getClickedBlock().getType())) return;

        //Change to check for the specific block, not a specific job. IE: If the player's job gets paid for breaking grass
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ItemStack handItem = player.getInventory().getItemInMainHand();
        ItemStack offhandItem = player.getInventory().getItemInOffHand();
        Material blockMaterial = block.getType();
        Material handMaterial = player.getInventory().getItemInMainHand().getType();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            //Use a switch satatemnet here.

            if (blockMaterial.equals(Material.GRASS_BLOCK) && handMaterial.equals(Material.AIR)) ReplaceBlocks.doReplacement(player, block, Material.DIRT, null, null, 15, 2, 2, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.FERN) && handMaterial.equals(Material.AIR)) ReplaceBlocks.doReplacement(player, block, Material.AIR, null, Material.POTATO, 15, 2, 2, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.LARGE_FERN) && handMaterial.equals(Material.AIR)) ReplaceBlocks.doReplacement(player, block, Material.AIR, null, Material.POTATO, 15, 2, 2, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.GRASS) && handMaterial.equals(Material.AIR)) ReplaceBlocks.doReplacement(player, block, Material.AIR, null, Material.CARROT, 15, 2, 2, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.TALL_GRASS) && handMaterial.equals(Material.AIR)) ReplaceBlocks.doReplacement(player, block, Material.AIR, null, Material.CARROT, 15, 2, 2, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.DIRT) && handMaterial.equals(Material.WHEAT_SEEDS)) ReplaceBlocks.doReplacement(player, block, Material.GRASS_BLOCK, Material.DIRT, null, 15, 2, 2, 0, 0, true, Sound.BLOCK_GRASS_BREAK, null, null);

            if (blockMaterial.equals(Material.WHEAT)) ReplaceBlocks.doReplacement(player, block, Material.WHEAT, null, null, 0, 1, 0, 0, 7, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.CARROTS)) ReplaceBlocks.doReplacement(player, block, Material.CARROTS, null, null, 0, 1, 0, 0, 7, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.POTATOES)) ReplaceBlocks.doReplacement(player, block, Material.POTATOES, null, null, 0, 1, 0, 0, 7, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.BEETROOTS)) ReplaceBlocks.doReplacement(player, block, Material.BEETROOTS, null, null, 0, 1, 0, 0, 3, false, Sound.BLOCK_GRASS_BREAK, null, null);

            if (blockMaterial.equals(Material.STONECUTTER) && handMaterial.toString().contains("SWORD")) { 
                
                StoneCutterItem sci = new StoneCutterItem(player, handItem);

                if (sci.incrementSharpness()) player.sendMessage("Your weapon has been enhanced.");
                else player.sendMessage("You were not able to enhance that weapon.");

                event.setCancelled(true);
            
            }
        }
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

            if (blockMaterial.toString().contains("SAPLING")) ReplaceBlocks.doReplacement(player, block, Material.AIR, null, Material.STICK, 4, 0, 4, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.DEAD_BUSH)) ReplaceBlocks.doReplacement(player, block, Material.AIR, null, Material.STICK, 4, 2, 3, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageEvent event) {
        //DamageManager manager = new DamageManager();
        //manager.initiate(event);

    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        //Type     Variable       New OreDrops object containing doOreDrop method.
        //OreDrops oreDrops = new OreDrops(); https://hub.spigotmc.org/javadocs/spigot/org/bukkit/block/Block.html


        //String string = string;
        //returns null;

        Block block = event.getBlock();

        Material material = block.getType();

        if (material == Material.COAL_BLOCK && TownyHandler.getPermissions(event.getPlayer(), event.getPlayer().getLocation(), event.getPlayer().getInventory().getItemInMainHand().getType())) {

            OreDrops.doOreDrop(event.getPlayer(), block, Material.FLINT, "Hurty", 1);

        }

        if (material == Material.IRON_ORE) {

            ItemStack item = new ItemStack(Material.IRON_HELMET);

            ItemMeta meta = item.getItemMeta(); 
            
            AttributeModifier modifier = new AttributeModifier("generic.armor", (double) 10, AttributeModifier.Operation.ADD_NUMBER);

            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

            item.setItemMeta(meta);

            System.out.println("Serialization: " + item.serialize());
            

            //Config blockConfig = new Config("LOW_IRON_CHUNK");
            //Config armorConfig = new Config("Placeholder_Helmet");

            //event.getPlayer().getLocation().getWorld().dropItemNaturally(event.getPlayer().getLocation(), blockConfig.blockItem);
            event.getPlayer().getLocation().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);

        }




    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) { 

        Date date = new Date();

        UserData playerData = new UserData(event.getPlayer());

        ClassManager manager = new ClassManager();

        manager.initiate(event.getPlayer());

        playerData.setLogin(event.getPlayer().getUniqueId(), date.getTime());

        System.out.println("Login: " + playerData.getClassName());
        System.out.println("Login: " + playerData.getDisplayName());
        System.out.println("Login: " + playerData.getLogin());
        System.out.println("Login: " + playerData.getLogout());

    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {

        Date date = new Date();

        UserData playerData = new UserData(event.getPlayer());

        playerData.setLogout(event.getPlayer().getUniqueId(), date.getTime());

        System.out.println("Logout: " + playerData.getClassName());
        System.out.println("Logout: " + playerData.getDisplayName());
        System.out.println("Logout: " + playerData.getLogin());
        System.out.println("Logout: " + playerData.getLogout());

    }
}