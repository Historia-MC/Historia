package dev.boooiil.historia.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.Config;
import dev.boooiil.historia.mysql.UserData;

public class StoneCutterItem {

    Player player;

    ItemStack item;
    ItemMeta itemMeta;

    Config config = new Config();

    List<String> numList = Arrays.asList("I", "II", "III", "IV", "V");

    public StoneCutterItem(Player p, ItemStack i) {

        player = p;

        item = i;
        itemMeta = i.getItemMeta();

    }

    public boolean decrementSharpness() {

        if (hasSharpness()) {

            Map<String, Integer> uses = getSharpnessUse();

            String sharpnessLevel = numList.get(getSharpnessLevel() == 0 ? getSharpnessLevel() : getSharpnessLevel() - 1);

            Integer used = uses.get("USES");
            Integer total = uses.get("TOTAL");

            // Check if the item has one use left on its sharpness.
            if (used == 1) {

                // Check if the total value is divisible by 10.
                boolean downgrade = total != 10 && total % 10 == 0;

                if (downgrade) {

                    // Set the new total (10 more than the previous total)
                    total = total + 10;

                    // Set used to the new total since we are starting from a new sharpness level.
                    used = total;

                    // Set the new sharpness line.
                    String line = "Sharpness " + sharpnessLevel + ": " + used + "/" + total;

                    // Set the new lore for the item.
                    itemMeta.setLore(getLoreWithSharpness(line));

                    // Remove the old sharpness enchant.
                    itemMeta.removeEnchant(Enchantment.DAMAGE_ALL);

                    // Set our new sharpness enchant.
                    itemMeta.addEnchant(Enchantment.DAMAGE_ALL, getSharpnessLevel() - 1, false);

                    // Apply the new meta to the item.
                    item.setItemMeta(itemMeta);

                    return true;

                }

                // Else we remove the sharpness from the item.
                else {

                    // Add our new lore to the item.
                    itemMeta.setLore(getLoreWithoutSharpness());

                    // Remove the sharpness enchant from the item.
                    itemMeta.removeEnchant(Enchantment.DAMAGE_ALL);

                    // Apply the new item meta to the item.
                    item.setItemMeta(itemMeta);

                    return true;

                }

            }

            // Else, decrement the use total by one.
            else {

                if (total == 30) {

                    itemMeta.setLore(getLoreWithoutSharpness());

                    item.setItemMeta(itemMeta);

                    return true;

                } else {

                    // Decrement the use total.
                    used = used - 1;

                    // Set the new sharpness line.
                    String line = "Sharpness " + sharpnessLevel + ": " + used + "/" + total;

                    // Set the new lore to our item meta.
                    itemMeta.setLore(getLoreWithSharpness(line));

                    // Apply the new item meta to the item.
                    item.setItemMeta(itemMeta);

                    return true;

                }

                

            }

        } else {
            Bukkit.getLogger().warning("TRIED TO DECREMENT SHARPNESS FROM NON-SHARPENED ITEM.");
            return false;
        }

    }

    public boolean incrementSharpness() {

        if (getSharpnessLevel() < 3 && (hasClassSkill() || hasOverrideStatus()) && isConfiguredWeapon()) {

            Map<String, Integer> uses = getSharpnessUse();

            String sharpnessLevel = numList.get(getSharpnessLevel());

            Integer total = uses.get("TOTAL") - 10;

            String line = "Sharpness " + sharpnessLevel + ": " + total + "/" + total;

            itemMeta.setLore(getLoreWithSharpness(line));
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, getSharpnessLevel() + 1, false);

            item.setItemMeta(itemMeta);

            return true;

        } 
        
        else if (getSharpnessLevel() == 3) {

            Map<String, Integer> uses = getSharpnessUse();

            String sharpnessLevel = numList.get(getSharpnessLevel() - 1);

            String line = "Sharpness " + sharpnessLevel + ": " + uses.get("TOTAL") + "/" + uses.get("TOTAL");

            itemMeta.setLore(getLoreWithSharpness(line));

            item.setItemMeta(itemMeta);

            return true;

        }
        
        return false;

    }

    private boolean isConfiguredWeapon() {

        return config.validWeapon(itemMeta.getLocalizedName());

    }

    private boolean hasSharpness() {

        boolean match = false;

        if (itemMeta.hasLore()) {

            for (String lore : itemMeta.getLore()) {

                if (lore.contains("Sharpness"))
                    match = true;

            }

        }
        return match;

    }

    private boolean hasClassSkill() {

        UserData user = new UserData(player);

        return user.getClassName().equals("Blacksmith");

    }

    private boolean hasOverrideStatus() {

        if (player.getGameMode().equals(GameMode.CREATIVE))
            return true;

        if (player.hasPermission("historia.override"))
            return true;

        return player.isOp();
    }

    private int getSharpnessLevel() {

        return itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL);

    }

    private Map<String, Integer> getSharpnessUse() {

        Map<String, Integer> uses = new HashMap<>();

        if (hasSharpness()) {

            String loreLine = "";

            for (String lore : itemMeta.getLore()) {

                boolean sharp = lore.contains("Sharpness");

                if (sharp) {

                    loreLine = lore;

                }
            }

            //String found = Pattern.compile("([0-9]|[0-9][0-9])(.*)([0-9][0-9])").matcher(loreLine).replaceAll("");
            String[] found = Pattern.compile("Sharpness .*: ").matcher(loreLine).replaceAll("").split("/");

            uses.put("USES", Integer.parseInt(found[0]));
            uses.put("TOTAL", Integer.parseInt(found[1]));

        }

        else {

            uses.put("USES", 40);
            uses.put("TOTAL", 40);

        }

        return uses;

    }

    private List<String> getLoreWithoutSharpness() {

        List<String> list = new ArrayList<>();

        // Iterate throug the current item's lore and apply the lines to our new list.
        for (String lore : itemMeta.getLore()) {

            // Check if the line contains sharpness.
            boolean sharp = lore.contains("Sharpness");

            // If the line doesn't contain sharpness, add it to the list.
            if (!sharp)
                list.add(lore);

        }

        return list;

    }

    private List<String> getLoreWithSharpness(String line) {

        List<String> list = new ArrayList<>();

        if (itemMeta.hasLore()) {

            if (getSharpnessLevel() != 0) {
                
                //Iterate through the current item's lore so that we can insert the new lore line.
                for (String lore : itemMeta.getLore()) {
                    
                    //Check if the line contains sharpness.
                    boolean sharp = lore.contains("Sharpness");

                    //Apply the sharpness line where the old sharpness line was.
                    if (sharp) {
    
                        list.add(line);
    
                    }

                    //Apply the non-sharpness line to our list.
                    else {
    
                        list.add(lore);

                    }
                }
            }

            else {

                list = item.getItemMeta().getLore();
                list.add(line);

            }
        }

        else list.add(line);
        
        return list;

    }
}