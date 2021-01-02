package dev.boooiil.historia.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

public class WeaponRunnable extends BukkitRunnable {
    
    private final JavaPlugin plugin;
    private final Set<String> weaponSet = Config.getWeaponSet();
    private final Set<String> armorSet = Config.getArmorSet();

    public WeaponRunnable(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            for (ItemStack item : player.getInventory().getContents()) {

                ItemMeta meta = item.getItemMeta();

                if (canApply(meta)) {

                    if (weaponSet.contains(meta.getLocalizedName())) setWeaponMeta(item);
                    if (armorSet.contains(meta.getLocalizedName())) setArmorMeta(item);


                }

            }

        }

    }
    private boolean canApply(ItemMeta meta) {

        return meta.hasLocalizedName() && (weaponSet.contains(meta.getLocalizedName()) || armorSet.contains(meta.getLocalizedName()));

    }

    private void setWeaponMeta(ItemStack item) {

       

    }

    private void setArmorMeta(ItemStack item) {

    }
}
