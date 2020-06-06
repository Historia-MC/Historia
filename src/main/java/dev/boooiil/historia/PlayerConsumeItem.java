package dev.boooiil.historia;

import java.util.Calendar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerConsumeItem implements Listener {
    
    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        if (event.getItem().getItemMeta().hasLore()) {
            for (String lore : event.getItem().getItemMeta().getLore()) {

                if (lore.contains("Expiry: ") || lore.contains("expiry")) {

                    for (String word : lore.split(" ")) {

                        if (word.contains("-")) {
                            String[] compare = word.split("-");

                            Calendar date = Calendar.getInstance();

                            int year = 0; int month = 0; int day = 0;

                            if (lore.contains("Expiry: ")) {
                                month = (int) Integer.parseInt(compare[0]);
                                day = (int) Integer.parseInt(compare[1]);
                                year = (int) Integer.parseInt(compare[2]);
                            }
                            if (lore.contains("expiry ")) {
                                year = (int) Integer.parseInt(compare[0]);
                                month = (int) Integer.parseInt(compare[1]);
                                day = (int) Integer.parseInt(compare[2]);
                            }

                            if (year < date.get(Calendar.YEAR)) {

                                PotionEffect posion = new PotionEffect(PotionEffectType.POISON, 120, 1);
                                PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER, 600, 2);
                                

                                event.getPlayer().sendMessage("§7[§9Historia§7] §cThat food was bad!");
                                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                                posion.apply(event.getPlayer()); hunger.apply(event.getPlayer()); event.setCancelled(true);
                                
                            } 
                            else if (month < (date.get(Calendar.MONTH) + 1)) {

                                PotionEffect posion = new PotionEffect(PotionEffectType.POISON, 120, 1);
                                PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER, 600, 2);
                                

                                event.getPlayer().sendMessage("§7[§9Historia§7] §cThat food was bad!");
                                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                                posion.apply(event.getPlayer()); hunger.apply(event.getPlayer()); event.setCancelled(true);
                            }
                            else if (month == (date.get(Calendar.MONTH) + 1) && day < date.get(Calendar.DATE)) {

                                PotionEffect posion = new PotionEffect(PotionEffectType.POISON, 120, 1);
                                PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER, 600, 2);
                                

                                event.getPlayer().sendMessage("§7[§9Historia§7] §cThat food was bad!");
                                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                                posion.apply(event.getPlayer()); hunger.apply(event.getPlayer()); event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        } 
    }
}