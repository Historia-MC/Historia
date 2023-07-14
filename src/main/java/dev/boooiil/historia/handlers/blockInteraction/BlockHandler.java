package dev.boooiil.historia.handlers.blockInteraction;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.classes.items.generic.OreDrop;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.OreConfig;
import dev.boooiil.historia.util.Logging;

public class BlockHandler extends BaseBlockHandler {

    private OreConfig oreConfig = Config.getOreConfig();

    public BlockHandler(BlockBreakEvent event, HistoriaPlayer historiaPlayer) {

        super(event, historiaPlayer);

    }

    public BlockHandler(BlockPlaceEvent event, HistoriaPlayer historiaPlayer) {

        super(event, historiaPlayer);

    }

    public void doBreak() {

        if (oreConfig.isValidOre(breakEvent.getBlock().getType().toString())) {

            Float doulbeDropChance = 0.05f;
            Float dropChanceRoll = (float) Math.round((Math.random() * 100)) / 100;

            boolean didDouble = historiaPlayer.getProficiency().getSkills().hasChanceExtraOre() ? dropChanceRoll <= doulbeDropChance : false;

            OreDrop drop = oreConfig.getDropFromChance(breakEvent.getBlock().getType().toString(), historiaPlayer.getProficiency().getName());

            if (drop != null) {

                breakEvent.setCancelled(true);
                breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(), drop.getItemStack());
                if (didDouble) {
                    breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(), drop.getItemStack());
                    Logging.infoToPlayer("You have doubled your ore drop!", historiaPlayer.getUUID());
                
                }
                
                breakEvent.getBlock().setType(Material.AIR);

                //TODO: set experience for player

            }

        }

    }

    public void doPlace() {

    }

    
}
