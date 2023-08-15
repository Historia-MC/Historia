package dev.boooiil.historia.core.commands;

import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSet implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] arguments) {

        if (arguments.length == 0) {

            sender.sendMessage("Invalid arguments.");
            return true;

        }

        if (arguments[0].equals("health")) {

            AttributeInstance attribute = ( (Player) sender ).getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH);
            attribute.setBaseValue(Double.parseDouble(arguments[1]));

        }

        if (arguments[0].equals("speed")) {

            ( (Player) sender ).setWalkSpeed(Float.parseFloat(arguments[1]));

        }
 
        return true;

    }

}
