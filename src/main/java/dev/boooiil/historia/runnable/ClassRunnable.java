package dev.boooiil.historia.runnable;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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

                if (item != null && item.getType().toString().contains("PICKAXE")) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.DIG_SPEED);

                }

                if (item != null && item.getType().toString().contains("BOOTS")) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.PROTECTION_FALL);

                }

                if (item != null && item.getType().toString().contains("AXE")) {

                    validate(userData.getClassName(), item, item.getItemMeta(), Enchantment.DIG_SPEED);

                }

            }

        }

    }
    
    private boolean canApply(String className, Enchantment enchantment) {

        if (className.equals("Miner") && enchantment == Enchantment.DIG_SPEED) return true;
        if (className.equals("Architect") && enchantment == Enchantment.PROTECTION_FALL) return true;
        if (className.equals("Lumberjack") && enchantment == Enchantment.DIG_SPEED) return true;
        else return false;

    }
    
    private void validate(String className, ItemStack item, ItemMeta itemMeta, Enchantment enchant) {

        if (canApply(className, enchant)) {
    
            itemMeta.addEnchant(enchant, 1, false);

            item.setItemMeta(itemMeta);

        }

        else if (!canApply(className, enchant)) {

            itemMeta.removeEnchant(enchant);

            item.setItemMeta(itemMeta);

        }
    }
}
