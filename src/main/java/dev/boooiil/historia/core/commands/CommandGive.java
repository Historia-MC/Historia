package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.classes.items.craftable.Armor;
import dev.boooiil.historia.core.classes.items.craftable.CustomItem;
import dev.boooiil.historia.core.classes.items.craftable.Weapon;
import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.configuration.specific.ArmorConfig;
import dev.boooiil.historia.core.configuration.specific.CustomItemConfig;
import dev.boooiil.historia.core.configuration.specific.WeaponConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGive implements CommandExecutor {

    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
            return false;
        if (args.length == 0)
            return false;

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("weapon")) {

            WeaponConfig weaponConfig = ConfigurationLoader.getWeaponConfig();

            if (weaponConfig.isValid(args[1])) {

                Weapon weapon = weaponConfig.getObject(args[1]);
                player.getWorld().dropItemNaturally(player.getLocation(), weapon.getItemStack());

            }

            else {
                    
                    sender.sendMessage("Invalid item name.");
            }

        } else if (args[0].equalsIgnoreCase("armor")) {

            ArmorConfig armorConfig = ConfigurationLoader.getArmorConfig();

            if (armorConfig.isValid(args[1])) {

                Armor armor = armorConfig.getObject(args[1]);
                player.getWorld().dropItemNaturally(player.getLocation(), armor.getItemStack());

            }

            else {
                    
                    sender.sendMessage("Invalid item name.");
            }

        } else if (args[0].equalsIgnoreCase("other")) {

            CustomItemConfig customItemConfig = ConfigurationLoader.getCustomItemConfig();

            if (customItemConfig.isValid(args[1])) {

                CustomItem item = customItemConfig.getObject(args[1]);
                player.getWorld().dropItemNaturally(player.getLocation(), item.getItemStack());

            }

            else {
                    
                    sender.sendMessage("Invalid item name.");
            }

        } else {
            sender.sendMessage("Syntax: /give <weapon/armor/other> <item name>");
            return false;
        }

        return false;

    }
}
