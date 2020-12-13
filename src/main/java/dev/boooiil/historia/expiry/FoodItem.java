package dev.boooiil.historia.expiry;

import java.util.Date;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import dev.boooiil.historia.Config;

public class FoodItem {
    
    Date date;

    Long itemDate;

    Integer itemExpiry;

    List<PotionEffect> effectList;

    FoodItem(ItemStack item) {

        Config config = new Config("foods");

        

    }
}
