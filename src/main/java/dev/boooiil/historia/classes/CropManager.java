package dev.boooiil.historia.classes;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.Config;
import dev.boooiil.historia.mysql.UserData;

public class CropManager {

    private static BlockBreakEvent bbe;

    private CropManager() { throw new IllegalStateException("This class should not be instanced."); }
    
    public static void calculateDrop(BlockBreakEvent event) {

        bbe = event;

        Block block = event.getBlock();

        if (Config.getCropBlocks().contains(block.getType())) {

            Player player = event.getPlayer();

            UserData userData = new UserData(player);
    
            if (userData.getClassName().equals("Farmer")) {
    
                doFarmerDrop(userData, player, block);
    
            }
    
            else {
    
                doOtherDrop(userData, player, block);
    
            }
        } 

    }

    private static void doFarmerDrop(UserData userData, Player player, Block block) {

        double chance = userData.getHarvestChance();
        double twice = userData.getDoubleHarvestChance();

        if (!willBotch(chance)) {

            if (willDouble(twice)) {

                Collection<ItemStack> drops = block.getDrops();

                World world = block.getWorld();
                Location location = block.getLocation();

                for (ItemStack item : drops) {

                    world.dropItemNaturally(location, item);

                }

            }

        }

        else {

            player.sendMessage("§7[§9Historia§7] §4You have botched this harvest.");

            bbe.setCancelled(true);
            block.breakNaturally();

        }

    }

    private static void doOtherDrop(UserData userData, Player player,  Block block) {

        double chance = userData.getHarvestChance();

        if (!willBotch(chance)) {

            block.breakNaturally();

        }

        else {

            player.sendMessage("§7[§9Historia§7] §4You have botched this harvest.");

            bbe.setCancelled(true);
            block.breakNaturally();

        }

    }

    private static boolean willBotch(double chance) {

        DecimalFormat df = new DecimalFormat("#.0");

        double random = new Random().nextDouble();

        random = Double.parseDouble(df.format(random));

        return random <= chance;
        
    }
    
    private static boolean willDouble(double chance) {

        DecimalFormat df = new DecimalFormat("#.00");

        double random = new Random().nextDouble();

        random = Double.parseDouble(df.format(random));

        return random <= chance;

    }
}
