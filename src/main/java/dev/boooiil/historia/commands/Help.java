package dev.boooiil.historia.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Help implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("help")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("historia.help")) {
                    player.sendMessage(ChatColor.GREEN + "------------ < Page 1 > -------------");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "/help - Gets the list of current commands.");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "/heal <player> - Heal a player.");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "/launch <number> - Launch yourself.");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "/item <player> <item> - Give a player an item from the Gods.");
                    TextComponent next1 = new TextComponent("------------ Next >> --------------");
                    next1.setColor(ChatColor.GREEN);
                    next1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help 2"));
                    next1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(">>").color(ChatColor.GREEN).create()));
                    player.spigot().sendMessage(next1);
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.GOLD + "This worked too.");
                return true;
            }
        }
        return false;
    }
    
}