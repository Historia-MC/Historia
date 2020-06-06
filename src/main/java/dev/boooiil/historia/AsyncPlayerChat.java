package dev.boooiil.historia;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMessage(AsyncPlayerChatEvent event) {

        System.out.println("User sent message.");
        
        if (event.getPlayer() instanceof Player) {

            System.out.println("User is player.");

            if (!event.getPlayer().hasPermission("essentials.msg")) {

                System.out.println("User does not have permission.");

                event.getPlayer().sendMessage("§7[§9Historia§7] Please use /letter <message> and /post <user>!");

            }
        }
    }

}