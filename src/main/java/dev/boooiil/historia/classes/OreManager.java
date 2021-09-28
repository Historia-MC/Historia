package dev.boooiil.historia.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.Config;
import dev.boooiil.historia.configuration.OreConfig;
import dev.boooiil.historia.mysql.UserData;

public class OreManager {
    
    private static UserData userData;

    private OreManager() { throw new IllegalStateException("This class should not be instanced."); }
    
    public static void calculateDrop(BlockBreakEvent event) {

        Block block = event.getBlock();
        Material material = block.getType();
        World world = block.getWorld();
        Location location = block.getLocation();

        Bukkit.getLogger().severe("List? " + OreConfig.getOreBlocks());

        Bukkit.getLogger().info("True?" + OreConfig.getOreBlocks().contains(block.getType()));

        if (OreConfig.getOreBlocks().contains(block.getType())) {

            userData = new UserData(event.getPlayer());

            block.setType(Material.AIR);

            ItemStack item = getIngotByChance(material.toString());

            if (item.getType() == Material.AIR) {

                event.setCancelled(true);

            }

            else world.dropItemNaturally(location, item);
            
        } 

    }

    //Gets sulphur from coal
    private static String getOreByChance(String oreName) {

        Map<String, Integer> map = Config.getOreChance(oreName);
        
        Bukkit.getLogger().info("[OreManager.JAVA] [getIngotByChance] Map:" + map);
        Bukkit.getLogger().info("[OreManager.JAVA] [getIngotByChance] ORE: " + oreName);

        List<Integer> chances = new ArrayList<>();
        List<String> blocks = new ArrayList<>();

        Integer sum = 0;
        Integer found = 0;
        Integer random = new Random().ints(1, 100).findFirst().getAsInt();

        for (Integer integer : map.values()) {

            Bukkit.getLogger().warning("[OreManager.JAVA] [getIngotByChance] [FOR] SUM: " + sum + " INTEGER: " + integer + " LOOKING: " + random);

            if (random >= sum && random <= sum + integer) found = integer;
            
            sum += integer;

            chances.add(integer);

        }

        for (String key : map.keySet()) blocks.add(key);

        return blocks.get(chances.indexOf(found));

    }

    //gets SULPHUR from SULPHUR
    private static ItemStack getIngotByChance(String oreName) {

        String ingotType = getOreByChance(oreName);

        Bukkit.getLogger().info("[OreManager.JAVA] [getIngotByChance]  ORE: " + oreName + " INGOT TYPE:" + ingotType);

        Map<String, Integer> map = Config.getIngotChance(oreName, ingotType, userData.getClassName());
        
        Bukkit.getLogger().info("[OreManager.JAVA] [getIngotByChance] Map:" + map);

        List<Integer> chances = new ArrayList<>();
        List<String> blocks = new ArrayList<>();

        Integer sum = 0;
        Integer found = 0;
        Integer random = new Random().ints(1, 100).findFirst().getAsInt();

        for (Integer integer : map.values()) {

            Bukkit.getLogger().warning("[OreManager.JAVA] [getIngotByChance] [FOR] SUM: " + sum + " INTEGER: " + integer + " LOOKING: " + random);

            if (random >= sum && random <= sum + integer) found = integer;
            
            sum += integer;

            chances.add(integer);

        }

        for (String key : map.keySet()) blocks.add(key);
        
        Bukkit.getLogger().info("[OreManager.JAVA] [getIngotByChance] ORE: " + oreName + " INGOT TYPE:" + ingotType);
        Bukkit.getLogger().info("[OreManager.JAVA] [getIngotByChance] BLOCKS: " + blocks);
        Bukkit.getLogger().info("[OreManager.JAVA] [getIngotByChance] FOUND BLOCK: " + found);
        Bukkit.getLogger().info("[OreManager.JAVA] [getIngotByChance] SUM: " + sum + " FOUND: " + found + " LOOKING: " + random);

        if (found != 0) {

            return Config.getIngot(oreName, ingotType, blocks.get(chances.indexOf(found)));

        }

        else return new ItemStack(Material.AIR);

    }
}
