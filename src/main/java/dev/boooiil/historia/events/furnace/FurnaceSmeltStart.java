package dev.boooiil.historia.events.furnace;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

import dev.boooiil.historia.classes.items.generic.Ingot;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.specific.IngotConfig;

/**
 * This class is used to set the smelt time of an ingot when it is placed in a
 * furnace.
 */
public class FurnaceSmeltStart implements Listener {

    IngotConfig ingotConfig = Config.getIngotConfig();

    // A method that is called when a furnace starts smelting an item.
    @EventHandler
    public void onSmeltStart(FurnaceStartSmeltEvent event) {

        String localizedName = event.getSource().getItemMeta().getLocalizedName();

        if (ingotConfig.isValidIngot(localizedName)) {

            Ingot ingot = ingotConfig.getObject(localizedName);

            event.setTotalCookTime(ingot.getSmeltTime());

        }

    }

}
