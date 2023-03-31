package dev.boooiil.historia.handlers.crafting;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.CraftingInventory;

import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.configuration.Config;

/**
 * It takes an inventory, and returns an item.
 */
public class CraftingTableManager {

    public static void craftItem(CraftingInventory inventory) {

        TableInspector inspector = new TableInspector(inventory.getContents());

        ArrayList<String> patterns = inspector.getPattern();
        ArrayList<String> materials = inspector.getMaterials();

        List<CraftedItem> matchingItems;

        // Getting all the items that match the pattern.
        matchingItems = Config.getArmorConfig().getAllMatchingShape(patterns);
        matchingItems.addAll(Config.getWeaponConfig().getAllMatchingShape(patterns));

        if (matchingItems.size() > 0) {

            inventory.setResult(new CustomItem(matchingItems, materials).getItem());

        }
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