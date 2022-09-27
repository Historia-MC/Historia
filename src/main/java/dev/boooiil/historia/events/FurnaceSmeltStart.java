package dev.boooiil.historia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

import dev.boooiil.historia.configuration.IngotConfig;
import dev.boooiil.historia.classes.Ingot;

public class FurnaceSmeltStart implements Listener {

    @EventHandler
    public void onPlayerKill(FurnaceStartSmeltEvent event) {

        String localizedName = event.getSource().getItemMeta().getLocalizedName();

        if (IngotConfig.isValidIngot(localizedName)) {

            Ingot ingot = IngotConfig.getIngot(localizedName);

            event.setTotalCookTime(ingot.getSmeltTime());
            
        }

    }

}
