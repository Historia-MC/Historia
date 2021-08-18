package dev.boooiil.historia;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.alerts.BoatNotify;
import dev.boooiil.historia.alerts.DeathNotify;
import dev.boooiil.historia.classes.ClassManager;
import dev.boooiil.historia.classes.CropManager;
import dev.boooiil.historia.expiry.ExpiryManager;
import dev.boooiil.historia.misc.ChickenShearing;
import dev.boooiil.historia.misc.FlameArrowHandler;
import dev.boooiil.historia.classes.OreManager;
import dev.boooiil.historia.configuration.WeaponConfig;
import dev.boooiil.historia.crafting.CraftingTableManager;
import dev.boooiil.historia.misc.PreventLavaPickup;
import dev.boooiil.historia.misc.ReplaceBlocks;
import dev.boooiil.historia.misc.StoneCutterItem;
import dev.boooiil.historia.mysql.MySQLHandler;
import dev.boooiil.historia.tools.FurnaceManager;
import dev.boooiil.historia.tools.LootSpawnManager;
import dev.boooiil.historia.dependents.towny.TownyHandler;
import dev.boooiil.historia.dependents.worldguard.WorldGuardHandler;

public class HistoriaEvents implements Listener {

    CraftingTableManager ctm = new CraftingTableManager();

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
    public void onBlockPlace(BlockPlaceEvent event) {

        ItemStack item = event.getItemInHand();
        boolean hasLocalizedName = item.getItemMeta().hasLocalizedName();

        if (hasLocalizedName) {

            event.setCancelled(true);
            event.getPlayer().sendMessage("§7[§9Historia§7] You can not place this block.");

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
    public void onPickup(EntityPickupItemEvent event) {

        if (event.getEntity() instanceof HumanEntity) {

            ExpiryManager manager = new ExpiryManager();
            manager.initiate(event.getItem().getItemStack(), (HumanEntity) event.getEntity());
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ExpiryManager manager = new ExpiryManager();
        manager.initiate(event.getItemDrop().getItemStack(), (HumanEntity) event.getPlayer());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        ClassManager.initiate(event.getPlayer());

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!event.hasBlock()) return;
        if (!WorldGuardHandler.getPermissions(event.getPlayer(), event.getClickedBlock().getLocation())) return;
        if (!TownyHandler.getPermissionByMaterial(event.getPlayer(), event.getClickedBlock().getLocation(), event.getClickedBlock().getType())) return;

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

                if (sci.incrementSharpness()) player.sendMessage("§7[§9Historia§7] Your weapon has been enhanced.");
                else player.sendMessage("§7[§9Historia§7] You were not able to enhance that weapon.");

                event.setCancelled(true);
            
            }
        }
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

            if (blockMaterial.toString().contains("SAPLING")) ReplaceBlocks.doReplacement(player, block, Material.AIR, null, Material.STICK, 4, 0, 4, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            if (blockMaterial.equals(Material.DEAD_BUSH)) ReplaceBlocks.doReplacement(player, block, Material.AIR, null, Material.STICK, 4, 2, 3, 0, 0, false, Sound.BLOCK_GRASS_BREAK, null, null);
            
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {

            Player player = (Player) event.getDamager();
            ItemStack handItem = player.getInventory().getItemInMainHand();

            if (handItem == null) handItem = new ItemStack(Material.AIR);

            StoneCutterItem sci = new StoneCutterItem(player, handItem);

            if (handItem.getItemMeta().hasLocalizedName() && WeaponConfig.getWeaponSet().contains(handItem.getItemMeta().getLocalizedName()) && handItem.containsEnchantment(Enchantment.DAMAGE_ALL)) sci.decrementSharpness();

        }

    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        Material material = block.getType();
        Material handMaterial = player.getInventory().getItemInMainHand().getType();

        boolean hasPermission = TownyHandler.getPermissionByMaterial(player, location, material);

        if (hasPermission && Config.getAllPickaxes().contains(handMaterial)) OreManager.calculateDrop(event);
        if (hasPermission) CropManager.calculateDrop(event);

    }

    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent fishEvent) {

        LootSpawnManager lootSpawnManager = new LootSpawnManager();

        lootSpawnManager.SpawnFishingLoot(fishEvent);

    }

    @EventHandler
    public void onFurnaceEvent(FurnaceSmeltEvent event) {

        ItemStack item = event.getSource();
        ItemMeta meta = item.getItemMeta();

        if (Config.getFish().contains(item.getType())) FurnaceManager.fishSmelting(event);

        if (meta.hasLocalizedName() && meta.getLocalizedName().contains("CHUNK")) {

            Bukkit.getLogger().info("SMELTING CHUNK TYPE: " + meta.getLocalizedName());
            event.setResult(FurnaceManager.smeltChunk(item));
            Bukkit.getLogger().info("RESULT: " + event.getResult());

        }


        if ((meta.hasLocalizedName() && meta.getLocalizedName().contains("INGOT")) || item.getType() == Material.IRON_INGOT) {
            
            if (meta.hasLocalizedName()) Bukkit.getLogger().info("SMELTING INGOT TYPE: " + meta.getLocalizedName());
            else Bukkit.getLogger().info("SMELTING FROM BASIC IRON");

            event.setResult(FurnaceManager.upgradeIngot(item));

        }

    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) { 

        ClassManager.initiate(event.getPlayer());

        MySQLHandler.setLogin(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {

        MySQLHandler.setLogout(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent event){

        ctm.craftItem(event.getInventory());

        // WeaponStats.initiate(prepareItemCraftEvent);
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent entityDeathEvent){

        


        // If the ageable entity is an adult
            // If a sheep is killed
        if (entityDeathEvent.getEntityType() == EntityType.SHEEP){
            LootSpawnManager.SheepDeathEvent(entityDeathEvent);
        }
        // If a horse is killed
        if (entityDeathEvent.getEntityType() == EntityType.HORSE){
            LootSpawnManager.HorseDeathEvent(entityDeathEvent);
        }
        // If a chicken is killed
        if (entityDeathEvent.getEntityType() == EntityType.CHICKEN){
            LootSpawnManager.ChickenDeathEvent(entityDeathEvent);
        }
        
    }

    private List<InventoryAction> place() {

        List<InventoryAction> place = new ArrayList<>();

        place.add(InventoryAction.PLACE_ALL);
        place.add(InventoryAction.PLACE_ONE);
        place.add(InventoryAction.PLACE_SOME);

        return place;
        
    }

    private List<InventoryAction> take() {

        List<InventoryAction> take = new ArrayList<>();

        take.add(InventoryAction.DROP_ALL_SLOT);
        take.add(InventoryAction.PICKUP_ALL);
        take.add(InventoryAction.PICKUP_ONE);
        take.add(InventoryAction.PICKUP_HALF);
        take.add(InventoryAction.PICKUP_SOME);
        take.add(InventoryAction.HOTBAR_SWAP);
        take.add(InventoryAction.SWAP_WITH_CURSOR);

        return take;

    }
}