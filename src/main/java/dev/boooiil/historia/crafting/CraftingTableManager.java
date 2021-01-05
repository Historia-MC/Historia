package dev.boooiil.historia.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.Config;

public class CraftingTableManager {
    
    Logger log = Bukkit.getLogger();
    String prefix = "[CTM.java] ";
    String replace = "LOW_|MEDIUM_|HIGH_";


    public void craftItem(CraftingInventory inventory) {

        Map<Integer, ItemStack> input = inspectTable(inventory.getMatrix());
        Map<String, Object> pattern = constructPattern(input);

        @SuppressWarnings("unchecked")
        List<String> shape = (List<String>) pattern.get("PATTERN");
        
        @SuppressWarnings("unchecked")
        List<String> materials = (List<String>) pattern.get("MATERIALS");

        log.info(prefix + "[craftItem] " + shape);
        log.info(prefix + "[craftItem] " + materials);

        if (hasRecipe(shape)) {

            Map<String, Object> itemsBasedOnPattern = getItemsBasedOnPattern(shape);

            inventory.setResult(getItemBasedOnIngot(itemsBasedOnPattern, materials));

        }
    }

    private Map<Integer, ItemStack> inspectTable(ItemStack[] contents) {

        Map<Integer, ItemStack> found = new HashMap<>();
        
        Integer slot = 0;

        for (ItemStack item : contents) {

            if (item != null) found.put(slot, item);
            else found.put(slot, new ItemStack(Material.AIR));
            slot++;

        }

        log.info(prefix + "[inspectTable] " + found);

        return found;

    }

    private Integer getQualityModifier() {

        return 0;

    }

    private Map<String, Object> constructPattern(Map<Integer, ItemStack> input) {

        Map<String, Object> map = new HashMap<>();
        List<String> pattern = new ArrayList<>();
        List<String> knownItems = new ArrayList<>();

        String[] options = {"A", "B", "C", "D", "E", "F", "G", "H", "I" };

        String first = "";
        String second = "";
        String third = "";

        for (Entry<Integer, ItemStack> slot : input.entrySet()) {

            Integer key = slot.getKey();
            ItemStack item = slot.getValue();
            ItemMeta meta = item.getItemMeta();

            String type = item.getType() != Material.AIR && meta.hasLocalizedName() ? meta.getLocalizedName() : item.getType().toString();

            if (key >= 0 && slot.getKey() <= 2) {

                if (item.getType() == Material.AIR) first += " ";

                else if (knownItems.contains(type)) {

                    first += options[knownItems.indexOf(type)];

                }

                else {

                    knownItems.add(type);
                    first += options[knownItems.size() - 1];

                }

            }

            if (key >= 3 && key <= 5) {

                if (item.getType() == Material.AIR) second += " ";

                else if (knownItems.contains(type)) {

                    second += options[knownItems.indexOf(type)];

                }

                else {

                    knownItems.add(type);
                    second += options[knownItems.size() - 1];

                }

            }

            if (key >= 6 && key <= 8) {

                if (item.getType() == Material.AIR) third += " ";

                else if (knownItems.contains(type)) {

                    third += options[knownItems.indexOf(type)];

                }

                else {

                    knownItems.add(type);
                    third += options[knownItems.size() - 1];

                }

            }

            log.info(prefix + "[constructPattern] Type: " + type);
        }

        pattern.add(first);
        pattern.add(second);
        pattern.add(third);

        map.put("PATTERN", pattern);
        map.put("MATERIALS", knownItems);

        log.info(prefix + "[constructPattern] " + map);
        
        return map;

    }

    private boolean hasRecipe(List<String> pattern) {

        Map<String, List<String>> patterns = Config.getPatterns();

        log.info(prefix + "[hasRecipe] " + patterns.containsValue(pattern));

        return patterns.containsValue(pattern);
    }

    private Map<String, Object> getItemsBasedOnPattern(List<String> pattern) {

        Map<String, Object> itemList = new HashMap<>();

        for (Entry<String, List<String>> item : Config.getPatterns().entrySet()) {

            String itemName = item.getKey();
            List<String> itemPattern = item.getValue();

            if (itemPattern.equals(pattern)) {

                Config config = new Config();

                //PROBLEM IS HERE.
                //LAST KEY PUT INTO THE LIST IS OVERWRITING THE WEAPON INFO.
                //IT IS 8AM AND HAVE BEEN UP ALL NIGHT.
                //NO IDEA WHAT TO DO.
                
                if (Config.getWeaponSet().contains(itemName)) itemList.put(itemName, config.iWeaponInfo(itemName));
                else itemList.put(itemName, config.iArmorInfo(itemName));

                log.severe(prefix + "[getItemsBasedOnPattern] Map:" + itemList);

            }
        }

        //log.info(prefix + "[getItemsBasedOnPattern] " + itemList);
        
        log.info(prefix + "[getItemsBasedOnPattern] Item List:" + itemList);

        return itemList;
    }

    private ItemStack getItemBasedOnIngot(Map<String, Object> items, List<String> materials) {

        for (Entry<String, Object> item : items.entrySet()) {

            String itemName = item.getKey();

            @SuppressWarnings("unchecked")
            Map<String, Object> itemInfo = (Map<String, Object>) item.getValue();

            @SuppressWarnings("unchecked")
            List<String> itemMaterials = (List<String>) itemInfo.get("RECIPE");
            int need = materials.size();
            int matched = 0;
            
            log.warning(prefix + "[getItemBasedOnIngot] Item: " + itemInfo);
            log.info(prefix + "[getItemBasedOnIngot] Item Name: " + itemName);
            log.info(prefix + "[getItemBasedOnIngot] Materials: " + itemMaterials);
            log.info(prefix + "[getItemBasedOnIngot] need: " + need);
            log.info(prefix + "[getItemBasedOnIngot] matched: " + matched);

            if (materials.size() == itemMaterials.size()) {

                for (String material : materials) {

                    if (itemMaterials.contains(material.replaceFirst(replace, ""))) matched ++;

                }

                log.info(prefix + "[getItemBasedOnIngot] " + item);

                if (matched == need) return (ItemStack) itemInfo.get("ITEM");

            }
        }

        return new ItemStack(Material.AIR);

    }
    
}
