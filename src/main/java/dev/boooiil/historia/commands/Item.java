package dev.boooiil.historia.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("item")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("historia.item")) {
                    if (args[0] == "" || args[0] == player.getDisplayName()) {
                        if (args[1] == "") {
                            // give undefined item
                            if (player.getInventory().firstEmpty() == -1) {

                                Location location = player.getLocation(); World world = player.getWorld();

                                world.dropItemNaturally(location, getItem(""));
                                player.sendMessage(ChatColor.GOLD + "You have been given boots from the gods.");

                                return true;

                            }
                            player.getInventory().addItem(getItem(""));
                            player.sendMessage(ChatColor.GOLD + "You have been given boots from the gods.");

                            return true;

                        } else {
                            // give other item
                            try {
                                String item = "" + Material.matchMaterial(args[1].toUpperCase());

                                if (player.getInventory().firstEmpty() == -1) {

                                    Location location = player.getLocation(); World world = player.getWorld();

                                    world.dropItemNaturally(location, getItem(item));
                                    player.sendMessage(
                                            ChatColor.GOLD + "You have been given " + item + " from the gods.");
                                    return true;

                                }
                                player.getInventory().addItem(getItem(item));
                                player.sendMessage(ChatColor.GOLD + "You have been given " + item + " from the gods.");

                                return true;

                            } catch (Exception e) {
                                player.sendMessage(ChatColor.RED + "That material does not exist.");
                                return true;
                            }
                        }
                    } else {
                        try {
                            Player obtainedPlayer = Bukkit.getPlayerExact(args[0]);

                            if (args[1] == "") {
                                // give undefined item
                                if (obtainedPlayer.getInventory().firstEmpty() == -1) {

                                    Location location = obtainedPlayer.getLocation(); World world = obtainedPlayer.getWorld();

                                    world.dropItemNaturally(location, getItem(""));
                                    obtainedPlayer.sendMessage(ChatColor.GOLD + "You have been given boots from the gods.");

                                    return true;

                                }
                                obtainedPlayer.getInventory().addItem(getItem(""));
                                obtainedPlayer.sendMessage(ChatColor.GOLD + "You have been given boots from the gods.");

                                return true;

                            } else {
                                // give other item
                                try {
                                    String item = "" + Material.matchMaterial(args[1].toUpperCase());

                                    if (obtainedPlayer.getInventory().firstEmpty() == -1) {

                                        Location location = obtainedPlayer.getLocation(); World world = obtainedPlayer.getWorld();

                                        world.dropItemNaturally(location, getItem(item));
                                        obtainedPlayer.sendMessage(ChatColor.GOLD + "You have been given " + item + " from the gods.");

                                        return true;

                                    }
                                    obtainedPlayer.getInventory().addItem(getItem(item));
                                    obtainedPlayer.sendMessage(ChatColor.GOLD + "You have been given " + item + " from the gods.");

                                    return true;

                                } catch (Exception e) {
                                    player.sendMessage(ChatColor.RED + "That material does not exist.");
                                    return true;
                                }
                            }

                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "That player could not be found.");
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                    return true;
                }
            } else {
                if (args.length == 0)
                    sender.sendMessage(ChatColor.RED + "You must specify a player and item.");
                // check if user exists
                return true;
            }
        }
        return false;
    }

    public ItemStack getItem(String material) {

        if (material == "") {
            ItemStack boots = new ItemStack(Material.IRON_BOOTS);
            ItemMeta meta = boots.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Boots of Leaping");

            List<String> lore = new ArrayList<String>();

            lore.add("");
            lore.add(ChatColor.GOLD + "Boots of the Gods.");
            meta.setLore(lore);
            meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setUnbreakable(true);

            boots.setItemMeta(meta);

            return boots;
        }
        ItemStack item = new ItemStack(Material.matchMaterial(material));
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "" + material + " of Legends");

        List<String> lore = new ArrayList<String>();

        lore.add("");
        lore.add(ChatColor.GOLD + "" + material + " of the Gods.");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);

        item.setItemMeta(meta);

        return item;
    }
}