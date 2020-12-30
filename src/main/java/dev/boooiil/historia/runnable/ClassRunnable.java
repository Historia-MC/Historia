package dev.boooiil.historia.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.Config;
import dev.boooiil.historia.mysql.UserData;

public class ClassRunnable extends BukkitRunnable {

    private final JavaPlugin plugin;

    public ClassRunnable(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            UserData userData = new UserData(player);

            for (ItemStack item : player.getInventory().getContents()) {

                if (item != null && Config.getAllPickaxes().contains(item.getType())) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.DIG_SPEED);

                }

                if (item != null && Config.getAllBoots().contains(item.getType())) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.PROTECTION_FALL);

                }

                if (item != null && Config.getAllShovels().contains(item.getType())) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.DIG_SPEED);

                }

                if (item != null && Config.getAllHoes().contains(item.getType())) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.DIG_SPEED);

                }
                
                if (item != null && Config.getAllAxes().contains(item.getType())) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.DIG_SPEED);

                }
                
                if (item != null && Config.getAllPickaxes().contains(item.getType())) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.DIG_SPEED);

                }

            }

        }

    }
    
    private void validate(String className, ItemStack item, ItemMeta itemMeta, Enchantment enchant) {

        if (canApply(className, item.getType())) {
    
            itemMeta.addEnchant(enchant, 1, false);

            item.setItemMeta(itemMeta);

        }

        else if (!canApply(className, item.getType())) {

            itemMeta.removeEnchant(enchant);

            item.setItemMeta(itemMeta);

        }
    }
    
    private boolean canApply(String className, Material type) {

        if (className.equals("Miner") && Config.getAllPickaxes().contains(type)) return true;
        if (className.equals("Architect") && Config.getAllBoots().contains(type)) return true;
        if (className.equals("Lumberjack") && Config.getAllAxes().contains(type)) return true;
        if (className.equals("Farmer") && Config.getAllShovels().contains(type) || Config.getAllHoes().contains(type)) return true;
        else return false;

    }
}
