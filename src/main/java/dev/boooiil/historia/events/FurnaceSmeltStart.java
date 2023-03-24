package dev.boooiil.historia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

import dev.boooiil.historia.classes.Ingot;

/**
 * This class is used to set the smelt time of an ingot when it is placed in a furnace.
 */
public class FurnaceSmeltStart implements Listener {

    @EventHandler
    // A method that is called when a furnace starts smelting an item.
    public void onSmeltStart(FurnaceStartSmeltEvent event) {

        String localizedName = event.getSource().getItemMeta().getLocalizedName();

        if (Ingot.ingotConfig.isValidIngot(localizedName)) {

            Ingot ingot = Ingot.ingotConfig.getIngot(localizedName);

            event.setTotalCookTime(ingot.getSmeltTime());
            
        }

    }

}
