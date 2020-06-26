package dev.boooiil.historia.misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PreventLavaPickup {

    public static boolean onPickup(Player player) {
        if (!player.hasPermission("historia.pickup")) {
            
            player.sendMessage("§7[§9Historia§7] §cOuch! That lava was hot!");
            player.damage(4);
            player.setFireTicks(60);

            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                if (online.hasPermission("historia.alerts") || online.isOp()) {
                    online.sendMessage("§7[§9Alert§7] " + player.getDisplayName() + " tried to obtain lava.");
                }
            }
            return true;
        } return false;
    }
}