package dev.boooiil.historia.alerts;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class BoatNotify {
    
    public static void boatNotify(EquipmentSlot slot, Player player, Entity entity) {
        if(slot.equals(EquipmentSlot.HAND)) {
            
            if (!player.hasPermission("historia.boatplace")) {
    
                String deathX = "" + Math.rint(entity.getLocation().getX());
                String deathY = "" + Math.rint(entity.getLocation().getY());
                String deathZ = "" + Math.rint(entity.getLocation().getZ());
    
                System.out.println("§7[§9Alert§7] " + player.getName() + " interacted with a boat at §7[§9" + deathX + " " + deathY + " " + deathZ + "§7]");
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.hasPermission("historia.alerts") || online.isOp()) {
    
                        TextComponent message = new TextComponent("§7[§9Alert§7] " + player.getName() + " interacted with a boat at §7[§9" + deathX + " " + deathY + " " + deathZ + "§7]");
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + deathX + " " + deathY + " " + deathZ));
                        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleport to " + deathX + ", " + deathY + ", " + deathZ).color(ChatColor.GREEN).create()));
    
                        online.spigot().sendMessage(message);
                    }
                }
            }
        }
    }
}