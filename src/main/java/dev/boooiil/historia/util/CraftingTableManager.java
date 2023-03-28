package dev.boooiil.historia.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.classes.CraftedItem;
import dev.boooiil.historia.configuration.Config;

/**
 * It takes an inventory, and returns an item.
 */
public class CraftingTableManager {

    private static String prefix = "[CTM.java] ";
    private static String replace = "LOW_|MEDIUM_|HIGH_";

    public static void craftItem(CraftingInventory inventory) {

        Map<Integer, ItemStack> input = inspectTable(inventory.getMatrix());
        Map<String, List<String>> pattern = constructPattern(input);

        List<CraftedItem> matchingItems;

        // Getting all the items that match the pattern.
        matchingItems = Config.getArmorConfig().getAllMatchingShape(pattern.get("PATTERN"));
        matchingItems.addAll(Config.getWeaponConfig().getAllMatchingShape(pattern.get("PATTERN")));

        List<String> materials = pattern.get("MATERIALS");

        Logging.debugToConsole(prefix + "[craftItem] " + matchingItems.toString());
        Logging.debugToConsole(prefix + "[craftItem] " + materials);

        if (matchingItems.size() > 0) {

            inventory.setResult(getItemBasedOnIngot(matchingItems, materials));

        }
    }

    /**
     * It takes an array of ItemStacks and returns a map of the slot number and the item in that slot
     * 
     * @param contents The contents of the inventory
     * @return A map of integers and itemstacks.
     */
    private static Map<Integer, ItemStack> inspectTable(ItemStack[] contents) {

        Map<Integer, ItemStack> found = new HashMap<>();

        Integer slot = 0;

        for (ItemStack item : contents) {

            if (item != null)
                found.put(slot, item);
            else
                found.put(slot, new ItemStack(Material.AIR));
            slot++;

        }

        Logging.debugToConsole(prefix + "[inspectTable] " + found);

        return found;

    }

    /**
     * It takes a map of integers and itemstacks, and returns a map of strings and lists of strings
     * 
     * @param input The input map
     * @return A map with two keys, PATTERN and MATERIALS.
     */
    private static Map<String, List<String>> constructPattern(Map<Integer, ItemStack> input) {

        Map<String, List<String>> map = new HashMap<>();
        List<String> pattern = new ArrayList<>();
        List<String> knownItems = new ArrayList<>();

        String[] options = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

        String first = "";
        String second = "";
        String third = "";

        for (Entry<Integer, ItemStack> slot : input.entrySet()) {

            Integer key = slot.getKey();
            ItemStack item = slot.getValue();
            ItemMeta meta = item.getItemMeta();

            String type = item.getType() != Material.AIR && meta.hasLocalizedName() ? meta.getLocalizedName()
                    : item.getType().toString();

            if (key >= 0 && slot.getKey() <= 2) {

                if (item.getType() == Material.AIR)
                    first += " ";

                else if (knownItems.contains(type)) {

                    first += options[knownItems.indexOf(type)];

                }

                else {

                    knownItems.add(type);
                    first += options[knownItems.size() - 1];

                }

            }

            if (key >= 3 && key <= 5) {

                if (item.getType() == Material.AIR)
                    second += " ";

                else if (knownItems.contains(type)) {

                    second += options[knownItems.indexOf(type)];

                }

                else {

                    knownItems.add(type);
                    second += options[knownItems.size() - 1];

                }

            }

            if (key >= 6 && key <= 8) {

                if (item.getType() == Material.AIR)
                    third += " ";

                else if (knownItems.contains(type)) {

                    third += options[knownItems.indexOf(type)];

                }

                else {

                    knownItems.add(type);
                    third += options[knownItems.size() - 1];

                }

            }

            Logging.debugToConsole(prefix + "[constructPattern] Type: " + type);
        }

        pattern.add(first);
        pattern.add(second);
        pattern.add(third);

        map.put("PATTERN", pattern);
        map.put("MATERIALS", knownItems);

        Logging.debugToConsole(prefix + "[constructPattern] " + map);

        return map;

    }

    /**
     * It takes a number, and returns a number
     * 
     * @param complexity The number of questions in the survey
     * @return The return value is the sum of the medium and high values multiplied by their respective
     * weights.
     */
    private static float getQualityModifier(int complexity) {

        int medium = 0;
        int high = 0;
        float mediumWeight = 50 / (float) complexity;
        float highWeight = 100 / (float) complexity;

        return (medium * mediumWeight) + (high * highWeight);

    }

    /**
     * It loops through a list of items that match a pattern, and returns the item that matches the
     * pattern and has the same materials as the list of materials
     * 
     * @param items List of items that match the pattern.
     * @param materials Materials used in the 
     * @return A CraftedItem object.
     */
    private static ItemStack getItemBasedOnIngot(List<CraftedItem> items, List<String> materials) {

        // Looping through the list of items that match the pattern.
        for (CraftedItem item : items) {

            int need = materials.size();
            int matched = 0;

            Logging.debugToConsole(prefix + "[getItemBasedOnIngot] Item: " + item);
            Logging.debugToConsole(prefix + "[getItemBasedOnIngot] Item Name: " + item.getItemStack().getItemMeta().getLocalizedName());
            Logging.debugToConsole(prefix + "[getItemBasedOnIngot] Materials: " + item.getRecipeItems());
            Logging.debugToConsole(prefix + "[getItemBasedOnIngot] need: " + need);
            Logging.debugToConsole(prefix + "[getItemBasedOnIngot] matched: " + matched);

            if (materials.size() == item.getRecipeItems().size()) {

                for (String material : materials) {

                    Logging.debugToConsole("" + item.getRecipeItems().contains(material.replaceFirst(replace, "")));

                    if (item.getRecipeItems().contains(material.replaceFirst(replace, "")))
                        matched++;

                }

                Logging.debugToConsole(prefix + "[getItemBasedOnIngot] " + item);

                if (matched == need)
                    return item.getItemStack();

            }
        }

        return new CraftedItem().getItemStack();

    }

    public static String hideText(String text) {

        StringBuilder output = new StringBuilder();
        HexFormat hex = HexFormat.of();

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String conv = "";

        for (byte b : bytes) {

            conv += hex.toHexDigits(b);

        }

        for (char c : conv.toCharArray()) {
            output.append(ChatColor.COLOR_CHAR).append(c);
        }

        return output.toString();
    }
}