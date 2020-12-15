package dev.boooiil.historia.commands;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import dev.boooiil.historia.Config;
import dev.boooiil.historia.mysql.UserData;

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

                        Config config = new Config();

                        Map<String, Object> weapon = config.getWeaponInfo("Placeholder_Sword"); 
                        Map<String, Object> helmet = config.getArmorInfo("Placeholder_Helmet");
                        Map<String, Object> chestplate = config.getArmorInfo("Placeholder_Chestplate");
                        Map<String, Object> leggings = config.getArmorInfo("Placeholder_Leggings");
                        Map<String, Object> boots = config.getArmorInfo("Placeholder_Boots");


                        sender.sendMessage("Weapon: " + weapon.toString());
                        sender.sendMessage("Helmet: " + helmet.toString());
                        sender.sendMessage("Chestplate: " + chestplate.toString());
                        sender.sendMessage("Leggings: " + leggings.toString());
                        sender.sendMessage("Boots: " + boots.toString());
                    
                    }

                    if (args[0].equalsIgnoreCase("player")) {

                        UserData data = new UserData((Player) sender);

                        sender.sendMessage(data.getClassName());
                    }
                    if (args[0].equalsIgnoreCase("class")) {

                        Player player = (Player) sender;
                        Config config = new Config();
                        UserData user = new UserData(player);

                        if (args[1].equalsIgnoreCase("add")) {

                            if (config.validClass(args[2])) {
                                
                                user.setClass(player.getUniqueId(), args[2]);

                            } else sender.sendMessage("That class was not in the list of classes.");

                        }
                        else if (args[1].equalsIgnoreCase("remove")) {

                            user.setClass(player.getUniqueId(), "None");

                        }
                        else if (args[1].equalsIgnoreCase("stats")) {

                            sender.sendMessage("Name:" + user.getClassName());
                            sender.sendMessage("Armor:" + user.getArmorValue());
                            sender.sendMessage("Experience:" + user.getExperience());
                            sender.sendMessage("Level:" + user.getLevel());
                            sender.sendMessage("Health:" + user.getHealth());

                        } else sender.sendMessage("Must be format /debugitems class add <class>, /debugitems class remove <class>, /debugitems class stats");

                    }
                }

            }
        return false;
    }
}
