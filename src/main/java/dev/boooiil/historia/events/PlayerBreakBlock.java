package dev.boooiil.historia.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.configuration.OreConfig;
import dev.boooiil.historia.classes.OreDrop;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void onPlayerKill(BlockBreakEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        Block block = event.getBlock();
        Material material = block.getType();

        if (OreConfig.isValidOre(material.toString())) {

            OreDrop drop = OreConfig.getDropFromChance(material.toString(), historiaPlayer.getClassName());

            if (drop != null) {
                
                event.setCancelled(true);
                block.getWorld().dropItemNaturally(block.getLocation(), drop.getItemStack());
                block.setType(Material.AIR);

            }

        }

    }

    


}
