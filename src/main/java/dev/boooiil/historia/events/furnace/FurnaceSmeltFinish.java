package dev.boooiil.historia.events.furnace;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

import dev.boooiil.historia.classes.items.generic.Ingot;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.IngotConfig;

/**
 * I'm trying to get the FurnaceSmeltEvent to fire when a player smelts an item
 * in a furnace.
 * 
 */
public class FurnaceSmeltFinish implements Listener {

    IngotConfig ingotConfig = Config.getIngotConfig();

    // It's a method that is called when the FurnaceSmeltEvent is fired.
    @EventHandler
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {

        // Not being fired, ??

        String localizedName = event.getSource().getItemMeta().getLocalizedName();

        if (ingotConfig.isValid(localizedName)) {

            Ingot ingot = ingotConfig.getObject(localizedName);

            if (ingot.hasProgression()) {

                event.setResult(ingot.getProgression().getItemStack());

            }

        }

    }

}
