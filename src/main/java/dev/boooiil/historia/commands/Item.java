package dev.boooiil.historia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.boooiil.historia.configuration.WeaponConfig;

public class Item implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("item") && args.length != 0 && sender instanceof Player) {

            Player player = (Player) sender;

            WeaponConfig weaponConfig = new WeaponConfig();
            WeaponConfig.Weapon weapon = weaponConfig.new Weapon(args[0]);

            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), weapon.weaponItemStack);

            return true;
            
        }
        
        return false;
    }
}