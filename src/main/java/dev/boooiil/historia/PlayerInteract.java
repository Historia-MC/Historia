package dev.boooiil.historia;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.jobs.CheckJob;
import dev.boooiil.historia.jobs.DoJobsPayment;
import dev.boooiil.historia.towny.TownyHandler;
import dev.boooiil.historia.worldguard.WorldGuardHandler;

public class PlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!event.hasBlock()) return;
        if (!WorldGuardHandler.getPermissions(event.getPlayer(), event.getClickedBlock().getLocation())) return;
        if (!TownyHandler.getPermissions(event.getPlayer(), event.getClickedBlock().getLocation(), event.getClickedBlock().getType())) return;

        //Change to check for the specific block, not a specific job. IE: If the player's job gets paid for breaking grass
        boolean hasjob = CheckJob.hasJob(event.getPlayer(), "Farmer");
        
        DoJobsPayment.payout(event.getPlayer(), "Farmer");

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            int failChance = (int) (Math.random() * 25) + 1;
            int plantChance = (int) (Math.random() * 30) + 1;
            int plantAmount = (int) (Math.random() * 2) + 1;

            Block clickedBlock = event.getClickedBlock();
            Material blockMaterial = clickedBlock.getType();
            Material handMaterial = event.getPlayer().getInventory().getItemInMainHand().getType();

            if (blockMaterial.equals(Material.GRASS_BLOCK) && handMaterial.equals(Material.AIR)) {

                if (failChance == 10) {
                    clickedBlock.setType(Material.DIRT);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You failed to dig out grass!");
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    return;
                }

                if (plantChance == 15) {
                    ItemStack item = new ItemStack(Material.WHEAT_SEEDS);
                    item.setAmount(plantAmount);

                    event.getPlayer().getWorld().dropItemNaturally(clickedBlock.getLocation(), item);
                    event.getPlayer().giveExp(2);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    clickedBlock.setType(Material.DIRT);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You dug out some grass!");

                }
            }
            if (handMaterial.equals(Material.WHEAT_SEEDS) && blockMaterial.equals(Material.DIRT)) {

                if (failChance == 10) {
                    clickedBlock.setType(Material.DIRT);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You failed to place grass!");
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    return;
                }

                event.getPlayer().getInventory().getItemInMainHand()
                        .setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                event.getPlayer().giveExp(2);
                event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                clickedBlock.setType(Material.GRASS_BLOCK);
                event.getPlayer().sendMessage("§7[§9Historia§7] You planted some grass!");

            }

            if (blockMaterial.equals(Material.FERN) && handMaterial.equals(Material.AIR)) {

                if (failChance == 10) {
                    clickedBlock.setType(Material.AIR);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You failed to dig out potatoes!");
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    return;
                }

                if (plantChance == 15) {
                    ItemStack item = new ItemStack(Material.POTATO);
                    item.setAmount(plantAmount);

                    event.getPlayer().getWorld().dropItemNaturally(clickedBlock.getLocation(), item);
                    event.getPlayer().giveExp(2);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    clickedBlock.setType(Material.AIR);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You dug out some potatoes!");

                }
            }
            if (blockMaterial.equals(Material.LARGE_FERN) && handMaterial.equals(Material.AIR)) {

                if (failChance == 10) {
                    clickedBlock.setType(Material.AIR);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You failed to dig out potatoes!");
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    return;
                }

                if (plantChance == 15) {
                    ItemStack item = new ItemStack(Material.POTATO);
                    item.setAmount(plantAmount);

                    event.getPlayer().giveExp(2);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    event.getPlayer().getWorld().dropItemNaturally(clickedBlock.getLocation(), item);
                    clickedBlock.setType(Material.AIR);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You dug out some potatoes!");

                }
            }
            if (blockMaterial.equals(Material.GRASS) && handMaterial.equals(Material.AIR)) {

                if (failChance == 10) {
                    clickedBlock.setType(Material.AIR);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You failed to dig out carrots!");
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    return;
                }

                if (plantChance == 15) {

                    ItemStack item = new ItemStack(Material.CARROT);
                    item.setAmount(plantAmount);

                    event.getPlayer().giveExp(2);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    event.getPlayer().getWorld().dropItemNaturally(clickedBlock.getLocation(), item);
                    clickedBlock.setType(Material.AIR);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You dug out some carrots!");

                }
            }
            if (blockMaterial.equals(Material.TALL_GRASS) && handMaterial.equals(Material.AIR)) {

                if (failChance == 10) {
                    clickedBlock.setType(Material.AIR);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You failed to dig out carrots!");
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    return;
                }

                if (plantChance == 15) {
                    ItemStack item = new ItemStack(Material.CARROT);
                    item.setAmount(plantAmount);

                    event.getPlayer().giveExp(2);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    event.getPlayer().getWorld().dropItemNaturally(clickedBlock.getLocation(), item);
                    clickedBlock.setType(Material.AIR);
                    event.getPlayer().sendMessage("§7[§9Historia§7] You dug out some carrots!");

                }
            }


            if (blockMaterial.equals(Material.WHEAT)) {

                if (((Ageable) clickedBlock.getBlockData()).getAge() == 7) {

                    event.getPlayer().giveExp(1);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    clickedBlock.breakNaturally();
                    clickedBlock.setType(Material.WHEAT);
                }

            }
            if (blockMaterial.equals(Material.CARROTS)) {

                if (((Ageable) clickedBlock.getBlockData()).getAge() == 7) {

                    event.getPlayer().giveExp(1);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    clickedBlock.breakNaturally();
                    clickedBlock.setType(Material.CARROTS);
                }

            }
            if (blockMaterial.equals(Material.POTATOES)) {

                if (((Ageable) clickedBlock.getBlockData()).getAge() == 7) {

                    event.getPlayer().giveExp(1);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    clickedBlock.breakNaturally();
                    clickedBlock.setType(Material.POTATOES);
                }

            }
            if (blockMaterial.equals(Material.BEETROOTS)) {

                if (((Ageable) clickedBlock.getBlockData()).getAge() == 3) {

                    event.getPlayer().giveExp(1);
                    event.getPlayer().playSound(clickedBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 15, 1);
                    clickedBlock.breakNaturally();
                    clickedBlock.setType(Material.BEETROOTS);
                }

            }
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