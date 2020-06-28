package dev.boooiil.historia;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.jobs.CheckJob;
import dev.boooiil.historia.jobs.DoJobsPayment;
import dev.boooiil.historia.misc.ReplaceBlocks;
import dev.boooiil.historia.towny.TownyHandler;
import dev.boooiil.historia.worldguard.WorldGuardHandler;

public class PlayerInteract implements Listener {

    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!event.hasBlock()) return;
        if (!WorldGuardHandler.getPermissions(event.getPlayer(), event.getClickedBlock().getLocation())) return;
        if (!TownyHandler.getPermissions(event.getPlayer(), event.getClickedBlock().getLocation(), event.getClickedBlock().getType())) return;

        //Change to check for the specific block, not a specific job. IE: If the player's job gets paid for breaking grass
        boolean hasJob = CheckJob.hasJob(event.getPlayer(), "Farmer");
        
        //if (hasJob) DoJobsPayment.payout(event.getPlayer(), "Farmer");

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            Material blockMaterial = block.getType();
            Material handMaterial = player.getInventory().getItemInMainHand().getType();

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

        }
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

            Block clickedBlock = event.getClickedBlock();
            Material blockMaterial = clickedBlock.getType();

            if (blockMaterial.toString().contains("SAPLING")) {

                int fail = (int) (Math.random() * 4) + 1;
                int amount = (int) (Math.random() * 3) + 1;
                if (fail == 1) {

                    ItemStack item = new ItemStack(Material.STICK);
                    item.setAmount(amount);
                    event.getClickedBlock().setType(Material.AIR);
                    event.getPlayer().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), item);
                    event.getPlayer().sendMessage("§7[§9Historia§7] This sapling is unusable!");

                }
            }
            if (blockMaterial.equals(Material.DEAD_BUSH)) {

                int fail = (int) (Math.random() * 4) + 1;
                int amount = (int) (Math.random() * 3) + 1;

                if (fail != 1) {

                    ItemStack item = new ItemStack(Material.STICK);

                    item.setAmount(amount);
                    event.getClickedBlock().setType(Material.AIR); event.getPlayer().giveExp(2);
                    event.getPlayer().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), item);

                }
            }
        }
    }
}