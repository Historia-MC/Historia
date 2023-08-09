package dev.boooiil.historia.events.crafting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.handlers.crafting.CraftingItemManager;
import dev.boooiil.historia.handlers.crafting.CraftingTableInspector;
import dev.boooiil.historia.util.Logging;

public class PrepareItemCraftListener implements Listener {

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {

        CraftingTableInspector inspector = new CraftingTableInspector(event.getInventory().getContents());
        ItemStack resultItem = null;

        if (event.getRecipe() != null) {

            String itemName = event.getRecipe().getResult().getType().toString().replaceFirst(".*_", "");

            String weaponMatch = "(BOW|CROSSBOW|SWORD|AXE)";
            String toolMatch = "(PICKAXE|SHOVEL|HOE)";
            String armorMatch = "(BOOTS|CHESTPLATE|HELMET|LEGGINGS)";

            if (itemName.matches(weaponMatch)) {

                Logging.debugToConsole(
                        "[PICE] Recipe: " + event.getRecipe().getResult().getType().toString() + " matches weapon");

                resultItem = ConfigurationLoader.getWeaponConfig()
                        .getWeaponMatchingMaterials(itemName, inspector.getMaterials()).getItemStack();

            } else if (itemName.matches(toolMatch)) {

                Logging.debugToConsole(
                        "[PICE] Recipe: " + event.getRecipe().getResult().getType().toString() + " matches tool");

                resultItem = ConfigurationLoader.getToolConfig()
                        .getToolMatchingMaterials(itemName, inspector.getMaterials()).getItemStack();

            } else if (itemName.matches(armorMatch)) {

                Logging.debugToConsole(
                        "[PICE] Recipe: " + event.getRecipe().getResult().getType().toString() + " matches armor");

                resultItem = ConfigurationLoader.getArmorConfig()
                        .getArmorMatchingMaterials(itemName, inspector.getMaterials()).getItemStack();

            } else {

                Logging.debugToConsole("[PICE] Recipe: " + event.getRecipe().getResult().getType().toString()
                        + " did not have a match");

            }

        }

        Logging.debugToConsole("[PICE] Vanilla Match: " + (resultItem != null));
        Logging.debugToConsole("[PICE] Pattern: " + inspector.getPattern());
        Logging.debugToConsole("[PICE] Materials: " + inspector.getMaterials());
        Logging.debugToConsole("[PICE] Full Materials: " + inspector.getFullMaterials());

        if (resultItem == null) {

            Logging.debugToConsole("[PICE] No match found, trying to match with custom recipes");

            // Match with custom recipes (if any
            CraftingItemManager cim = new CraftingItemManager(inspector);
            cim.doMatch();

            if (cim.getResult() != null)
                resultItem = cim.getResult();
            else if (event.getRecipe() != null)
                resultItem = event.getRecipe().getResult();
            else
                resultItem = null;

            if (resultItem != null) {
                if (resultItem.getItemMeta() != null) {

                    Logging.debugToConsole(
                            "Result: " + resultItem.getItemMeta().getLocalizedName() + " " + resultItem.getAmount());

                } else {

                    Logging.debugToConsole("Result: " + resultItem.getType().toString() + " " + resultItem.getAmount());

                }
            } else
                Logging.debugToConsole("Result: null");


        }
        
        event.getInventory().setResult(resultItem);
    }

}
