package dev.boooiil.historia.handlers.crafting;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.Logging;

public class CraftingTableInspector {
    
    private ArrayList<String> pattern = new ArrayList<>();
    private ArrayList<String> materials = new ArrayList<>();
    private ArrayList<String> fullMaterials = new ArrayList<>();

    public CraftingTableInspector(ItemStack[] craftingTableInventory) {

        Logging.debugToConsole("[CTI] Table Size: " + craftingTableInventory.length);

        String[] options = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

        String first = "";
        String second = "";
        String third = "";

        // result is now in slot 0
        // so matlab rules apply on this array
        for (int i = 1; i < craftingTableInventory.length; i++) {

            ItemStack item = (craftingTableInventory[i] == null || craftingTableInventory[i].getItemMeta() == null) ? null : craftingTableInventory[i];
            String materialName = null;
            
            if (item != null && item.getItemMeta() != null) {

                if (item.getItemMeta().getLocalizedName() != "") materialName = item.getItemMeta().getLocalizedName();
                else materialName = item.getType().toString();

            }

            if (item != null) {

                Logging.debugToConsole("[CTI] Item: (" + i + ") " + materialName);

                fullMaterials.add(materialName);
                
            }

            if (i >= 1 && i <= 3) {

                if (item == null) first += " ";

                else if (materials.contains(materialName)) {

                    first += options[materials.indexOf(materialName)];

                }
                else {

                    materials.add(materialName);
                    first += options[materials.size() - 1];

                }

            }

            if (i >= 4 && i <= 6) {

                if (item == null) second += " ";

                else if (materials.contains(materialName)) {

                    second += options[materials.indexOf(materialName)];

                }
                else {

                    materials.add(materialName);
                    second += options[materials.size() - 1];

                }

            }

            if (i >= 7 && i <= 9) {

                if (item == null) third += " ";

                else if (materials.contains(materialName)) {

                    third += options[materials.indexOf(materialName)];

                }
                else {

                    materials.add(materialName);
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
