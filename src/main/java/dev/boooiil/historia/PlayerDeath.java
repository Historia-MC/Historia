package dev.boooiil.historia;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerDeath implements Listener {
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = (Player) event.getEntity();
        //Get player that killed other player

        for (String killerString : event.getDeathMessage().toString().split(" ")) {

            for (Player online : Bukkit.getOnlinePlayers()) {

                if (!player.getDisplayName().equals(killerString) && online.getDisplayName().equals(killerString)) {

                    Player killer = (Player) online;
                    PlayerInventory killerInventory = (PlayerInventory) killer.getInventory();

                    String deathX = "" + Math.rint(player.getLocation().getX());
                    String deathY = "" + Math.rint(player.getLocation().getY());
                    String deathZ = "" + Math.rint(player.getLocation().getZ());

                    System.out.println("§7[§9Alert§7] " + killer.getDisplayName() + " killed " + player.getDisplayName() + " using §7[§9" + killerInventory.getItemInMainHand().getType() + "§7] §7[§9" + deathX + " " + deathY + " " + deathZ +"§7]");
                    
                    TextComponent baseMessage = new TextComponent("§7[§9Alert§7] " + killer.getDisplayName() + " killed " + player.getDisplayName() + " using ");

                    TextComponent openBracket = new TextComponent("§7[");
                    TextComponent closeBracket = new TextComponent("§7] ");

                    TextComponent itemMessage = new TextComponent(ChatColor.BLUE + "" + killerInventory.getItemInMainHand().getType());
                    //get meta
                    if (killerInventory.getItemInMainHand().hasItemMeta()) {
                        ItemMeta item = killerInventory.getItemInMainHand().getItemMeta();

                        if (item.hasDisplayName()) {
                            if (item.hasLore()) {
                                if (item.hasEnchants()) {
                                    String itemLore = "";
                                    for (String lore : item.getLore()) {
                                        itemLore += "\n§5" + lore;
                                    }
                                    itemMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(item.getDisplayName() + "\n" + item.getEnchants() + "\n" + itemLore.trim()).create()));
                                } else {
                                    String itemLore = "";
                                    for (String lore : item.getLore()) {
                                        itemLore += "\n§5" + lore;
                                    }
                                    itemMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(item.getDisplayName() + "\n" + itemLore.trim()).create()));
                                }
                            }

                            else if (item.hasEnchants()) {
                                itemMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(item.getDisplayName() + "\n" + item.getEnchants()).create()));
                            } else {
                                itemMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(item.getDisplayName()).create()));
                            }
                        }

                        else if (item.hasLore()) {
                            if (item.hasEnchants()) {
                                String itemLore = "";
                                for (String lore : item.getLore()) {
                                    itemLore += "\n§5" + lore;
                                }
                                itemMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(item.getEnchants() + "\n" + itemLore.trim()).create()));
                            } else {
                                String itemLore = "";
                                for (String lore : item.getLore()) {
                                    itemLore += "\n§5" + lore;
                                }
                                itemMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(itemLore.trim()).create()));
                            }
                        }

                        else if (item.hasEnchants()) {
                            itemMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("" + item.getEnchants()).create()));
                        } 
                    }

                    TextComponent locationMessage = new TextComponent(ChatColor.BLUE + "" + deathX + " " + deathY + " " + deathZ);

                    locationMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + deathX + " " + deathY + " " + deathZ));
                    locationMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleport to " + deathX + ", " + deathY + ", " + deathZ).create()));

                    for (Player onlineUsersPerm : Bukkit.getServer().getOnlinePlayers()) {
                        if (onlineUsersPerm.hasPermission("historia.alerts") || onlineUsersPerm.isOp()) {
                            onlineUsersPerm.spigot().sendMessage(baseMessage, openBracket, itemMessage, closeBracket, openBracket, locationMessage, closeBracket);
                        }
                    }


                    break;
                }
            }
        }
    }

}