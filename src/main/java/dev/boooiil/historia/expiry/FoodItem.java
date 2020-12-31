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

        effectList.add((PotionEffect) Config.getFoodInfo("None").get("POISON"));
        effectList.add((PotionEffect) Config.getFoodInfo("None").get("HUNGER"));

        foodDays = (int) Config.getFoodInfo(item.getType().toString()).get("EXPIRY");
        foodMilliseconds = (long) foodDays * 86400000;

    }

    public long getSetExpiry() {

        return itemMilliseconds;

    }

    public long getFoodExpiry() {

        return foodMilliseconds;

    }



}
