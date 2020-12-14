package dev.boooiil.historia.expiry;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import dev.boooiil.historia.Config;

public class FoodItem {
    
    Date date;

    long foodMilliseconds;
    long itemMilliseconds;

    int foodDays;

    List<PotionEffect> effectList;

    FoodItem(ItemStack item) {

        Config config = new Config();

        effectList.add((PotionEffect) config.getFoodInfo("None").get("POISON"));
        effectList.add((PotionEffect) config.getFoodInfo("None").get("HUNGER"));

        foodDays = (int) config.getFoodInfo(item.getType().toString()).get("EXPIRY");
        foodMilliseconds = (long) foodDays * 86400000;

    }

    public long getSetExpiry() {

        return itemMilliseconds;

    }

    public long getFoodExpiry() {

        return foodMilliseconds;

    }



}
