package dev.boooiil.historia.handlers.crafting;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.Logging;

public class CraftingTableInspector {
    
    private ArrayList<String> pattern = new ArrayList<>();
    private ArrayList<String> materials = new ArrayList<>();
    private ArrayList<String> fullMaterials = new ArrayList<>();

    public CraftingTableInspector(ItemStack[] craftingTableInventory) {

        Logging.debugToConsole("[CCIM] Table Size: " + craftingTableInventory.length);

        String[] options = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

        String first = "";
        String second = "";
        String third = "";

        // result is now in slot 0
        // so matlab rules apply on this array
        for (int i = 1; i < craftingTableInventory.length; i++) {

            Logging.debugToConsole("[CCIM] Item: (" + i + ") " + craftingTableInventory[i]);

            boolean invalidItem = (craftingTableInventory[i] == null || craftingTableInventory[i].getItemMeta() == null);

            if (!invalidItem) {

                if (craftingTableInventory[i].getItemMeta().getLocalizedName() != "") {

                    fullMaterials.add(craftingTableInventory[i].getItemMeta().getLocalizedName());

                }

                else {
                    
                    fullMaterials.add(craftingTableInventory[i].getType().toString());

                }
            }

            if (i >= 1 && i <= 3) {

                if (invalidItem) first += " ";

                else if (materials.contains(craftingTableInventory[i].getItemMeta().getLocalizedName())) {

                    first += options[materials.indexOf(craftingTableInventory[i].getItemMeta().getLocalizedName())];

                }

                else if (craftingTableInventory[i].getItemMeta().getLocalizedName() != "") {

                    materials.add(craftingTableInventory[i].getItemMeta().getLocalizedName());
                    first += options[materials.size() - 1];

                }

                else {

                    materials.add(craftingTableInventory[i].getType().toString());
                    first += options[materials.size() - 1];

                }

            }

            if (i >= 4 && i <= 6) {

                if (invalidItem) second += " ";

                else if (materials.contains(craftingTableInventory[i].getItemMeta().getLocalizedName())) {

                    second += options[materials.indexOf(craftingTableInventory[i].getItemMeta().getLocalizedName())];

                }

                else if (craftingTableInventory[i].getItemMeta().getLocalizedName() != "") {

                    materials.add(craftingTableInventory[i].getItemMeta().getLocalizedName());
                    second += options[materials.size() - 1];

                }

                else {

                    materials.add(craftingTableInventory[i].getType().toString());
                    second += options[materials.size() - 1];

                }

            }

            if (i >= 7 && i <= 9) {

                if (invalidItem) third += " ";

                else if (materials.contains(craftingTableInventory[i].getItemMeta().getLocalizedName())) {

                    third += options[materials.indexOf(craftingTableInventory[i].getItemMeta().getLocalizedName())];

                }

                else if (craftingTableInventory[i].getItemMeta().getLocalizedName() != "") {

                    materials.add(craftingTableInventory[i].getItemMeta().getLocalizedName());
                    third += options[materials.size() - 1];

                }

                else {

                    materials.add(craftingTableInventory[i].getType().toString());
                    third += options[materials.size() - 1];

                }

            }

        }

        pattern.add(first);
        pattern.add(second);
        pattern.add(third);

        Logging.infoToConsole("Pattern: " + pattern.toString());
        Logging.infoToConsole("Materials: " + materials.toString());

    }

    public ArrayList<String> getPattern() {
        return pattern;
    }

    public ArrayList<String> getMaterials() {
        return materials;
    }

    public ArrayList<String> getFullMaterials() {
        return fullMaterials;
    }

}
