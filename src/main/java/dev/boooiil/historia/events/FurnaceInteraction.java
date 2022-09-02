package dev.boooiil.historia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

import dev.boooiil.historia.configuration.IngotConfig;
import dev.boooiil.historia.configuration.IngotConfig.Ingot;
import dev.boooiil.historia.util.Logging;

public class FurnaceInteraction implements Listener {

    @EventHandler
    public void onPlayerKill(FurnaceStartSmeltEvent event) {

        String localizedName = event.getSource().getItemMeta().getLocalizedName();

        Logging.debugToConsole("Localized Name " + localizedName);
        Logging.debugToConsole("Is valid: " + IngotConfig.isValidIngot(localizedName));

        if (IngotConfig.isValidIngot(localizedName)) {

            Ingot ingot = IngotConfig.getIngot(localizedName);

            event.setTotalCookTime(ingot.getSmeltTime());
            
        }

    }

}
