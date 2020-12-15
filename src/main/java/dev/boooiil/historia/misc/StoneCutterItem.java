package dev.boooiil.historia.misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.Config;

public class StoneCutterItem {

    ItemStack item;
    ItemMeta itemMeta;
    
    Config config = new Config();

    Integer loreIndex;
    List<String> newLore;
    List<String> numList = Arrays.asList("0", "I", "II", "III", "IV", "V");

    public StoneCutterItem(ItemStack weapon) {

        item = weapon;
        itemMeta = weapon.getItemMeta();

    }

    public boolean isConfiguredWeapon() {

        return config.validWeapon(itemMeta.getLocalizedName());

    }

    public boolean hasSharpness() {

        boolean match = false;

        for (String lore : itemMeta.getLore()) {

            if (lore.contains("Sharpness:")) match = true;

        }

        return match;

    }

    public boolean decrementSharpness() {

        if (hasSharpness()) {

            Map<Enchantment, Integer> enchantments = itemMeta.getEnchants();
            Map<String, Integer> uses = getSharpnessUse();

            String sharpnessLevel = numList.get(enchantments.get(Enchantment.DAMAGE_ALL));

            Integer used = uses.get("USES");
            Integer total = uses.get("TOTAL");

            //Check if the item has one use left on its sharpness.
            if (used == 1) {

                //Check if the total value is divisible by 10.
                boolean downgrade = total != 10 && total % 10 == 0;

                if (downgrade) {

                    //Set the new total (10 less than the previous total)
                    total = total - 10;

                    //Set used to the new total since we are starting from a new sharpness level.
                    used = total;

                    //Set the new sharpness line.
                    String line = "Sharpness " + sharpnessLevel + ": " + used + "/" + total;

                    //Iterate through the current item's lore so that we can insert the new lore line.
                    for (String lore : itemMeta.getLore()) {
                    
                        //Check if the line contains sharpness.
                        boolean sharp = lore.contains("Sharpness");

                        //Apply the sharpness line where the old sharpness line was.
                        if (sharp) {
                        
                            newLore.add(line);
                        
                        }

                        //Apply the non-sharpness line to our list.
                        else {
                        
                            newLore.add(lore);
                    
                        }

                    }

                    //Set the new lore for the item.
                    itemMeta.setLore(newLore);

                    //Remove the old sharpness enchant.
                    itemMeta.removeEnchant(Enchantment.DAMAGE_ALL);

                    //Set our new sharpness enchant.
                    itemMeta.addEnchant(Enchantment.DAMAGE_ALL, enchantments.get(Enchantment.DAMAGE_ALL) - 1, false);

                    //Apply the new meta to the item.
                    item.setItemMeta(itemMeta);

                    return true;

                }

                //Else we remove the sharpness from the item.
                else {

                    //Iterate throug the current item's lore and apply the lines to our new list.
                    for (String lore : itemMeta.getLore()) {

                        //Check if the line contains sharpness.
                        boolean sharp = lore.contains("Sharpness");

                        //If the line doesn't contain sharpness, add it to the list.
                        if (!sharp) newLore.add(lore);

                    }

                    //Add our new lore to the item.
                    itemMeta.setLore(newLore);

                    //Remove the sharpness enchant from the item.
                    itemMeta.removeEnchant(Enchantment.DAMAGE_ALL);

                    //Apply the new item meta to the item.
                    item.setItemMeta(itemMeta);

                    return true;

                }

            } 
            
            //Else, decrement the use total by one. 
            else {

                //Decrement the use total.
                used = used - 1;

                //Set the new sharpness line.
                String line = "Sharpness " + sharpnessLevel + ": " + used + "/" + total;

                //Iterate through the current item's lore so that we can apply the new sharpness line.
                for (String lore : itemMeta.getLore()) {

                    //Check if the line contains sharpness.
                    boolean sharp = lore.contains("Sharpness");

                    //If the line contains sharpness, add our new sharpness line ot the list.
                    if (sharp) {
                        
                        newLore.add(line);
                        
                    }

                    //Else, add the non-sharpness line to our list.
                    else {
                        
                        newLore.add(lore);
                    
                    }
                }
                
                //Set the new lore to our item meta.
                itemMeta.setLore(newLore);

                //Apply the new item meta to the item.
                item.setItemMeta(itemMeta);

                return true;

            }

        } else { Bukkit.getLogger().warning("TRIED TO DECREMENT SHARPNESS FROM NON-SHARPENED ITEM."); return false; }
    }

    private Map<String, Integer> getSharpnessUse() {

        String loreLine = "";

        Matcher loreLineMatch;

        Map<String, Integer> uses = new HashMap<>();

        for (String lore : itemMeta.getLore()) {

            boolean sharp = lore.contains("Sharpness");

            if (sharp) {

                loreIndex = itemMeta.getLore().indexOf(lore);
                loreLine = lore;

            }
        }

        loreLineMatch = Pattern.compile("([0-9]|[0-9][0-9])(.*)([0-9][0-9])").matcher(loreLine);

        uses.put("USES", Integer.getInteger(loreLineMatch.group(1)));
        uses.put("TOTAL", Integer.getInteger(loreLineMatch.group(3)));

        return uses;

    }

    public static void onRightClick(ItemStack item) {

        if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
            
            int enchLevel = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            
            if (enchLevel == 1) { item.addEnchantment(Enchantment.DAMAGE_ALL, 2); item.getItemMeta().setLore(Arrays.asList("Sharpness II: 20/20")); }
            if (enchLevel == 2) { item.addEnchantment(Enchantment.DAMAGE_ALL, 3); item.getItemMeta().setLore(Arrays.asList("Sharpness III: 10/10")); }
            if (enchLevel == 3) { item.addEnchantment(Enchantment.DAMAGE_ALL, 3); item.getItemMeta().setLore(Arrays.asList("Sharpness III: 10/10")); }

        } else { item.addEnchantment(Enchantment.DAMAGE_ALL, 1); item.getItemMeta().setLore(Arrays.asList("Sharpness I: 30/30")); }
    }
}