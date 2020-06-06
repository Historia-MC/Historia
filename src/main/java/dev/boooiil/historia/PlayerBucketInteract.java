package dev.boooiil.historia;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class PlayerBucketInteract implements Listener{
    
    @EventHandler
    public void onPickupLava(PlayerBucketFillEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getBlockClicked().getType().toString() == "LAVA" && !player.hasPermission("historia.pickup")) {
            
            player.sendMessage("§7[§9Historia§7] §cOuch! That lava was hot!");
            player.damage(4);
            player.setFireTicks(60);
            event.setCancelled(true);

            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                if (online.hasPermission("historia.alerts") || online.isOp()) {
                    online.sendMessage("§7[§9Alert§7] " + player.getDisplayName() + " tried to obtain lava.");
                }
            }
        }
    }

}