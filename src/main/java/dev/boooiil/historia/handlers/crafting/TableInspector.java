package dev.boooiil.historia.handlers.crafting;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.Logging;

public class TableInspector {
    
    private ArrayList<String> pattern = new ArrayList<>();
    private ArrayList<String> materials = new ArrayList<>();

    public TableInspector(ItemStack[] craftingTableInventory) {

        String[] options = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

        String first = "";
        String second = "";
        String third = "";

        for (int i = 0; i < craftingTableInventory.length; i++) {

            if (i >= 0 && i <= 2) {

                if (craftingTableInventory[i] == null)
                    first += " ";

                else if (materials.contains(craftingTableInventory[i].getType().toString())) {

                    first += options[materials.indexOf(craftingTableInventory[i].getType().toString())];

                }

                else {

                    materials.add(craftingTableInventory[i].getType().toString());
                    first += options[materials.size() - 1];

                }

            }

            if (i >= 3 && i <= 5) {

                if (craftingTableInventory[i] == null)
                    second += " ";

                else if (materials.contains(craftingTableInventory[i].getType().toString())) {

                    second += options[materials.indexOf(craftingTableInventory[i].getType().toString())];

                }

                else {

                    materials.add(craftingTableInventory[i].getType().toString());
                    second += options[materials.size() - 1];

                }

            }

            if (i >= 6 && i <= 8) {

                if (craftingTableInventory[i] == null)
                    third += " ";

                else if (materials.contains(craftingTableInventory[i].getType().toString())) {

                    third += options[materials.indexOf(craftingTableInventory[i].getType().toString())];

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

}
