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

import dev.boooiil.historia.Config;

public class CraftingTableManager {
    
    static Logger log = Bukkit.getLogger();
    static String prefix = "[CTM.java] ";


    public static void craftItem(CraftingInventory inventory) {

        Map<Integer, ItemStack> input = inspectTable(inventory.getMatrix());
        Map<String, Object> pattern = constructPattern(input);

        List<String> shape = (List<String>) pattern.get("PATTERN");
        List<String> materials = (List<String>) pattern.get("MATERIALS");

        log.info(prefix + "[craftItem] " + shape);
        log.info(prefix + "[craftItem] " + materials);

        if (hasRecipe(shape)) {

            List<Map<String, Object>> itemsBasedOnPattern = getItemsBasedOnPattern(shape);

            inventory.setResult(getItemBasedOnIngot(itemsBasedOnPattern, materials));

        }

        //inventory.setResult(getRecipe(inventory.getMatrix()));

    }

    private static Map<Integer, ItemStack> inspectTable(ItemStack[] contents) {

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

    private static Integer getQualityModifier() {



    }

    private static Map<String, Object> constructPattern(Map<Integer, ItemStack> input) {

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

            if (key >= 0 && slot.getKey() <= 2) {

                if (item.getType() == Material.AIR) first += " ";

                else if (knownItems.contains(item.getType().toString())) {

                    first += options[knownItems.indexOf(item.getType().toString())];

                }

                else {

                    knownItems.add(item.getType().toString());
                    first += options[knownItems.size() - 1];

                }

            }

            if (key >= 3 && key <= 5) {

                if (item.getType() == Material.AIR) second += " ";

                else if (knownItems.contains(item.getType().toString())) {

                    second += options[knownItems.indexOf(item.getType().toString())];

                }

                else {

                    knownItems.add(item.getType().toString());
                    second += options[knownItems.size() - 1];

                }

            }

            if (key >= 6 && key <= 8) {

                if (item.getType() == Material.AIR) third += " ";

                else if (knownItems.contains(item.getType().toString())) {

                    third += options[knownItems.indexOf(item.getType().toString())];

                }

                else {

                    knownItems.add(item.getType().toString());
                    third += options[knownItems.size() - 1];

                }

            }

        }

        pattern.add(first);
        pattern.add(second);
        pattern.add(third);

        map.put("PATTERN", pattern);
        map.put("MATERIALS", knownItems);

        log.info(prefix + "[constructPattern] " + map);
        
        return map;

    }

    private static boolean hasRecipe(List<String> pattern) {

        Map<String, List<String>> patterns = Config.getPatterns();

        log.info(prefix + "[hasRecipe] " + patterns.containsValue(pattern));

        return patterns.containsValue(pattern);
    }

    private static List<Map<String, Object>> getItemsBasedOnPattern(List<String> pattern) {

        List<Map<String, Object>> itemList = new ArrayList<>();

        for (Entry<String, List<String>> item : Config.getPatterns().entrySet()) {

            String itemName = item.getKey();
            List<String> itemPattern = item.getValue();
            
            log.info(prefix + "[getItemsBasedOnPattern] " + itemName + " " + itemPattern + " == " + pattern);
            log.info(prefix + "[getItemsBasedOnPattern] " + (itemPattern == pattern));

            if (itemPattern.equals(pattern)) {

                itemList.add(Config.getWeaponInfo(itemName));

            }
        }

        log.info(prefix + "[getItemsBasedOnPattern] " + itemList);

        return itemList;
    }

    private static ItemStack getItemBasedOnIngot(List<Map<String, Object>> items, List<String> materials) {

        for (Map<String, Object> item : items) {

            List<String> itemMaterials = (List<String>) item.get("RECIPE");

            if (materials.size() == itemMaterials.size()) {

                log.info(prefix + "[getItemBasedOnIngot] " + item);

                return (ItemStack) item.get("ITEM");

            }

        }


        return new ItemStack(Material.AIR);

    }
    
}
