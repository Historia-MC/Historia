package dev.boooiil.historia;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerEntityInteract implements Listener{
    
    @EventHandler
    public void onBoatInteract(PlayerInteractEntityEvent event) {
        if(event.getHand().equals(EquipmentSlot.HAND)) {
            Player player = (Player) event.getPlayer();
            Entity entity = (Entity) event.getRightClicked();
            if (entity.getType().toString() == "BOAT" && !player.hasPermission("historia.boatplace")) {

                String deathX = "" + Math.rint(entity.getLocation().getX());
                String deathY = "" + Math.rint(entity.getLocation().getY());
                String deathZ = "" + Math.rint(entity.getLocation().getZ());

                System.out.println("§7[§9Alert§7] " + player.getName() + " interacted with a boat at §7[§9" + deathX + " " + deathY + " " + deathZ + "§7]");
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.hasPermission("historia.alerts") || online.isOp()) {

                        TextComponent message = new TextComponent("§7[§9Alert§7] " + player.getName() + " interacted with a boat at §7[§9" + deathX + " " + deathY + " " + deathZ + "§7]");
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + deathX + " " + deathY + " " + deathZ));
                        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleport to " + deathX + ", " + deathY + ", " + deathZ).color(ChatColor.GREEN).create()));
    
                        online.spigot().sendMessage(message);
                    }
                }
            }
            
            if (player.getInventory().getItemInMainHand().getType().equals(Material.SHEARS)) {
                if (entity.getType().toString() == "CHICKEN") {

                    Ageable chickenAge = (Ageable) entity;
    
                    if (chickenAge.getAge() < 0) return;
                    else {
                        
                        entity.remove();
    
                        Ageable newChicken = (Ageable) player.getWorld().spawnEntity(entity.getLocation(), EntityType.CHICKEN);
                        ItemStack feathers = new ItemStack(Material.FEATHER); feathers.setAmount((int) (Math.random() * 3) + 1);
                        
                        newChicken.setBaby(); player.getWorld().dropItemNaturally(entity.getLocation(), feathers);
                        player.giveExp(2);

                        ItemStack shears = player.getInventory().getItemInMainHand();
                        Short calculatedDurability = (short) ( shears.getDurability() + 10);
    
                        if (calculatedDurability >= (int) shears.getType().getMaxDurability()) {
                            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR)); player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 15, 1);
                        } else {
                            shears.setDurability(calculatedDurability);
                        }
                        
                    }
                }
            }
        }
    }

}