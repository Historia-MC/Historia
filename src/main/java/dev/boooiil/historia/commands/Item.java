package dev.boooiil.historia.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.Config;

public class Item implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("item") && args.length != 0 && sender instanceof Player) {

            Player player = (Player) sender;

            Map<String, Object> weapon = Config.getWeaponInfo(args[0]);

            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), (ItemStack) weapon.get("ITEM"));

            return true;
            
        }
        
        return false;
    }
}