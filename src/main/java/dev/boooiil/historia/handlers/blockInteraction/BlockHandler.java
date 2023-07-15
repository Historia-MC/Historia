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

        CropHandler.safetyCheckBlockBrokenHoldsCrop(breakEvent.getBlock());

        if (oreConfig.isValidOre(breakEvent.getBlock().getType().toString())) {

            Float doulbeDropChance = 0.05f;
            Float dropChanceRoll = (float) Math.round((Math.random() * 100)) / 100;

            boolean didDouble = historiaPlayer.getProficiency().getSkills().hasChanceExtraOre()
                    ? dropChanceRoll <= doulbeDropChance
                    : false;

            OreDrop drop = oreConfig.getDropFromChance(breakEvent.getBlock().getType().toString(),
                    historiaPlayer.getProficiency().getName());

            if (drop != null) {

                breakEvent.setCancelled(true);
                breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(),
                        drop.getItemStack());
                if (didDouble) {
                    breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(),
                            drop.getItemStack());
                    Logging.infoToPlayer("You have doubled your ore drop!", historiaPlayer.getUUID());

                }

                breakEvent.getBlock().setType(Material.AIR);

                // TODO: set experience for player

            }

        }

        else if (breakEvent.getBlock().getType().toString().contains("LOG")) {

            if (historiaPlayer.getProficiency().getSkills().hasChanceExtraWood()) {

                Float doulbeDropChance = 0.05f;
                Float dropChanceRoll = (float) Math.round((Math.random() * 100)) / 100;

                boolean didDouble = dropChanceRoll <= doulbeDropChance;

                if (didDouble) {

                    breakEvent.setCancelled(true);
                    breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(),
                            breakEvent.getBlock().getDrops().iterator().next());
                    breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(),
                            breakEvent.getBlock().getDrops().iterator().next());
                    Logging.infoToPlayer("You have doubled your log drop!", historiaPlayer.getUUID());
                    historiaPlayer.increaseExperience();

                }

            }

        }

        else if (breakEvent.getBlock().getType().toString().contains("BEEHIVE")) {

            if (!historiaPlayer.getProficiency().getSkills().canBreakBeehive()) {

                breakEvent.setCancelled(true);
                Logging.infoToPlayer("You have no idea what to do with this thing!", historiaPlayer.getUUID());

            }

        }

        else {
        }

    }

    public void doPlace() {

        // TODO: check for ores

        if (historiaPlayer.getProficiency().getSkills().hasChanceNoConsumeBlock()) {

            placeEvent.setCancelled(true);
            placeEvent.getBlockPlaced().setType(placeEvent.getBlockPlaced().getType());
            Logging.infoToPlayer("You did not consume any resources for this block!", historiaPlayer.getUUID());

        }

    }

}
