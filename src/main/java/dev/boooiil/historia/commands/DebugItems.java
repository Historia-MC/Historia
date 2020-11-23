package dev.boooiil.historia.commands;

import com.sk89q.worldedit.event.platform.PlayerInputEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.Config;

public class DebugItems implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                 if (label.equalsIgnoreCase("debugitems")) {
                    if (args[0].equalsIgnoreCase("name")) {

                        Player player = (Player) sender;

                        PlayerInventory playerInventory = player.getInventory();

                        ItemStack[] playerArmor = playerInventory.getArmorContents();

                        ItemStack helmet = playerArmor[0] != null ? playerArmor[0] : new ItemStack(Material.AIR);
                        ItemStack chestplate = playerArmor[1] != null ? playerArmor[1] : new ItemStack(Material.AIR);
                        ItemStack leggings = playerArmor[2] != null ? playerArmor[2] : new ItemStack(Material.AIR);
                        ItemStack boots = playerArmor[3] != null ? playerArmor[3] : new ItemStack(Material.AIR);

                        ItemStack mainHand = playerInventory.getItemInMainHand();
                        ItemStack offHand = playerInventory.getItemInOffHand();

                        //ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                        //ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                        //ItemStack itemHelmet = player.getInventory().getHelmet();
                        //ItemStack itemChestPlate = player.getInventory().getChestplate();
                        //ItemStack itemLeggings = player.getInventory().getLeggings();
                        //ItemStack itemBoots = player.getInventory().getBoots();

                        //ItemMeta itemInMainHandMeta = itemInMainHand.getItemMeta();
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

                        //Send armors.
                        if (helmet.getType() != Material.AIR) player.sendMessage("Helmet: " + helmet.getType() + " " + helmet.getItemMeta().getDisplayName() + " " + helmet.getItemMeta().getLocalizedName());
                        if (chestplate.getType() != Material.AIR) player.sendMessage("Chestplate: " + chestplate.getType() + " " + chestplate.getItemMeta().getDisplayName() + " " + chestplate.getItemMeta().getLocalizedName());
                        if (leggings.getType() != Material.AIR) player.sendMessage("Leggings: " + leggings.getType() + " " + leggings.getItemMeta().getDisplayName() + " " + leggings.getItemMeta().getLocalizedName());
                        if (boots.getType() != Material.AIR) player.sendMessage("Boots: " + boots.getType() + " " + boots.getItemMeta().getDisplayName() + " " + boots.getItemMeta().getLocalizedName());

                        //Send off / main hand.
                        if (mainHand.getType() != Material.AIR) player.sendMessage("Main Hand: " + mainHand.getType() + " " + mainHand.getItemMeta().getDisplayName() + " " + mainHand.getItemMeta().getLocalizedName());
                        if (offHand.getType() != Material.AIR) player.sendMessage("Off Hand: " + offHand.getType() + " " + offHand.getItemMeta().getDisplayName() + " " + offHand.getItemMeta().getLocalizedName());


                        //String messageToSend = (itemInMainHandMeta.getDisplayName() + " : " + itemInMainHandMeta.getLocalizedName());

                        // Bukkit.getServer().broadcastMessage(messageToSend); //send player's helmet display name and localized name
                        
                        //player.sendMessage(messageToSend); 

                        return true;

                        //send player's chestplate display name and localized name
                        // etc
                    }
                    if (args[0].equalsIgnoreCase("config")) {
                        Config weapon = new Config("Placeholder_Sword"); 
                        Config helmet = new Config("Placeholder_Helmet");
                        Config chestplate = new Config("Placeholder_Chestplate");
                        Config leggings = new Config("Placeholder_Leggings");
                        Config boots = new Config("Placeholder_Boots");


                        sender.sendMessage("Weapon: " + weapon.toString());
                        sender.sendMessage("Helmet: " + helmet.toString());
                        sender.sendMessage("Chestplate: " + chestplate.toString());
                        sender.sendMessage("Leggings: " + leggings.toString());
                        sender.sendMessage("Boots: " + boots.toString());
                    
                    }
                }

            }
        return false;
    }
}
