package dev.boooiil.historia.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DebugItems implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                 if (label.equalsIgnoreCase("debugitems")) {
                    if (args[0].equalsIgnoreCase("name")) {

                        Player player = (Player) sender;

                        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                        ItemStack itemHelmet = player.getInventory().getHelmet();
                        ItemStack itemChestPlate = player.getInventory().getChestplate();
                        ItemStack itemLeggings = player.getInventory().getLeggings();
                        ItemStack itemBoots = player.getInventory().getBoots();

                        ItemMeta itemInMainHandMeta = itemInMainHand.getItemMeta();
                        /*
                        ItemMeta itemInOffHandMeta = itemInOffHand.getItemMeta();
                        ItemMeta itemHelmetMeta = itemHelmet.getItemMeta();
                        ItemMeta itemChestPlateMeta = itemChestPlate.getItemMeta();
                        ItemMeta itemLeggingsMeta = itemLeggings.getItemMeta();
                        ItemMeta itemBootsMeta = itemBoots.getItemMeta();
                        */

                        // ItemMeta meta = item.getItemMeta()

                        // DO NOT TRY IF THERE IS AIR IN YOUR HAND!
                        // !
                        // !
                        // !

                        String messageToSend = (itemInMainHandMeta.getDisplayName() + " : " + itemInMainHandMeta.getLocalizedName());

                        // Bukkit.getServer().broadcastMessage(messageToSend); //send player's helmet display name and localized name
                        
                        player.sendMessage(messageToSend); 

                        return true;

                        //send player's chestplate display name and localized name
                        // etc
                    }
                }
            }
        return false;
    }
}
