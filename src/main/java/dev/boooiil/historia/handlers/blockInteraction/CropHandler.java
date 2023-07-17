package dev.boooiil.historia.handlers.blockInteraction;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.configuration.specific.CropConfig;
import dev.boooiil.historia.util.Logging;

/**
 * This class handles the interaction with crop blocks, including breaking and
 * placing.
 * It extends the BaseBlockHandler class.
 */
public class CropHandler extends BaseBlockHandler {

    /**
     * Constructor for CropHandler when a crop block is broken.
     * 
     * @param event          The BlockBreakEvent that triggered this handler.
     * @param historiaPlayer The HistoriaPlayer associated with this handler.
     */
    public CropHandler(BlockBreakEvent event, HistoriaPlayer historiaPlayer) {

        super(event, historiaPlayer);

    }

    /**
     * Constructor for CropHandler when a crop block is placed.
     * 
     * @param event          The BlockPlaceEvent that triggered this handler.
     * @param historiaPlayer The HistoriaPlayer associated with this handler.
     */
    public CropHandler(BlockPlaceEvent event, HistoriaPlayer historiaPlayer) {

        super(event, historiaPlayer);

    }

    /**
     * Method to handle the breaking of a crop.
     * Determines if the crop is successfully harvested, and if the harvest is
     * doubled.
     */
    public void doBreak() {

        // TODO: add farmer XP gain

        CropConfig cropConfig = ConfigurationLoader.getCropConfig();
        double harvestChance = historiaPlayer.getProficiency().getStats().getHarvestChance();
        double doubleHarvestChance = historiaPlayer.getProficiency().getStats().getDoubleHarvestChance();
        double harvestChanceRoll = (double) Math.round((Math.random() * 100)) / 100;

        Logging.debugToConsole("Harvest Chance: " + harvestChance);
        Logging.debugToConsole("Harvest Chance Roll: " + harvestChanceRoll);
        Logging.debugToConsole("Double Harvest Chance: " + doubleHarvestChance);

        // if the player has failed to harvest the crop
        if (harvestChanceRoll >= harvestChance) {

            breakEvent.setCancelled(true);

            if (cropConfig.isTallCrop(getBlock().getType())) {

                Logging.debugToConsole("Block broken is a tall crop.");

                int cropHeight = getCropHeight(getBlock(), getBlock().getType());

                destroyCropFromTop(getBlock(), cropHeight);

            }

            else {

                Logging.debugToConsole("Block broken is not a tall crop.");

                getBlock().setType(Material.AIR);

            }

            Logging.infoToPlayer("You failed to harvest the crop!", historiaPlayer.getUUID());

        }

        // if the player is able to double the harvest
        else if (harvestChanceRoll <= doubleHarvestChance) {

            getBlock().getDrops().forEach(item -> {

                getBlock().getWorld().dropItemNaturally(getBlock().getLocation(), item);

            });

            Logging.infoToPlayer("You harvested the crop and doubled the harvest!", historiaPlayer.getUUID());
            
            historiaPlayer.increaseExperience();

        }

        historiaPlayer.increaseExperience();

    }

    /**
     * Method to handle the placing of a crop.
     * Determines if the crop is instantly grown.
     */
    public void doPlace() {

        double instantGrowthChance = historiaPlayer.getProficiency().getStats().getInstantGrowthChance();
        double harvestChanceRoll = (double) Math.round((Math.random() * 100)) / 100;

        if (instantGrowthChance >= harvestChanceRoll) {

            Ageable crop = (Ageable) placeEvent.getBlock().getBlockData();

            crop.setAge(crop.getMaximumAge());
            Logging.infoToPlayer("You instantly grew the crop!", historiaPlayer.getUUID());

        }

    }

    /**
     * Checks if the block broken holds a crop.
     * 
     * @param block the block to check
     */
    public static void safetyCheckBlockBrokenHoldsCrop(Block block) {

        CropConfig cropConfig = ConfigurationLoader.getCropConfig();

        Block blockAbove = block.getLocation().add(0, 1, 0).getBlock();

        Logging.debugToConsole("[Crop Safety] Block Above: " + blockAbove.getType());
        Logging.debugToConsole("[Crop Safety] Block Above Has Gravity: " + blockAbove.getType().hasGravity());

        // check above block so that we can skip the rest of the checks if it's not a
        // crop
        if (!blockAbove.getType().hasGravity() && !cropConfig.isCrop(blockAbove.getType()))
            return;

        // so now we know that the block above has gravity or is a crop

        // if the block above is a crop
        if (cropConfig.isCrop(blockAbove.getType())) {

            Logging.debugToConsole("[Crop Safety] Block Above Is Crop");

            // if it's a tall crop, check the blocks above it and set them to air
            if (cropConfig.isTallCrop(blockAbove.getType())) {

                Logging.debugToConsole("[Crop Safety] Block Above Is Tall Crop");

                int cropHeight = getCropHeight(blockAbove, blockAbove.getType());

                destroyCropFromTop(blockAbove, cropHeight);

            }

            // if it's not a tall crop, set the block above to air
            else {

                Logging.debugToConsole("[Crop Safety] Block Above Is Not Tall Crop");

                blockAbove.setType(Material.AIR);

            }

        }

        // if the block above has gravity, check the blocks above it and set them to air
        if (blockAbove.getType().hasGravity()) {

            Block gravityHoldsCrop = checkGravityBlockTrailBreaksCrop(blockAbove);

            Logging.debugToConsole("[Crop Safety] Gravity Holds Crop: " + (gravityHoldsCrop != null));

            if (gravityHoldsCrop != null) {

                Logging.debugToConsole("[Crop Safety] Block Above Has Gravity And Holds Crop");

                int cropHeight = getCropHeight(gravityHoldsCrop, gravityHoldsCrop.getType());

                destroyCropFromTop(gravityHoldsCrop, cropHeight);

            }

        }

    }

    /**
     * Checks if the trail of gravity blocks above the given source block breaks a
     * crop.
     * 
     * @param source the source block to check
     * @return true if the trail of gravity blocks breaks a crop, false otherwise
     */
    private static Block checkGravityBlockTrailBreaksCrop(Block source) {

        CropConfig cropConfig = ConfigurationLoader.getCropConfig();
        Block gravityBlock = null;

        // check world height for block so since players can just keep placing the block
        for (double y = 0; y <= 316; y++) {

            if (source.getLocation().getY() + y > 316) {
                y = 316 - source.getLocation().getY();
            }

            Block newBlock = source.getWorld().getBlockAt(source.getLocation().add(0, y, 0));

            Logging.debugToConsole("[Crop Gravity] Checking Block (" + newBlock.getType() + ")");

            if (newBlock.getType().hasGravity()) {

                Logging.debugToConsole("[Crop Gravity] Gravity Block (" + newBlock.getType() + ") Found");

            }
            else if (cropConfig.isCrop(newBlock.getType())) {

                Logging.debugToConsole("[Crop Gravity] Gravity Block (" + newBlock.getType() + ") Breaks Crop");

                gravityBlock = newBlock;
                break;

            } else {

                break;

            }

        }

        return gravityBlock;

    }

    /**
     * Gets the height of a crop starting from the given source block.
     * 
     * @param source the source block to start from
     * @param crop the crop material to check for
     * @return the height of the crop
     */
    private static int getCropHeight(Block source, Material crop) {

        int height = 0;

        for (double y = 1; y <= 316; y++) {

            if (source.getLocation().getY() + y > 316) {
                y = 316 - source.getLocation().getY();
            }

            Block newBlock = source.getWorld().getBlockAt(source.getLocation().add(0, y, 0));

            if (newBlock.getType() == crop) {

                Logging.debugToConsole("[Crop Height] Crop (" + crop + ") Height: " + height);

                height++;
            }

            else {
                break;
            }

        }

        return height;

    }

    /**
     * Destroys a crop from the top down, starting from the given source block and going down to the specified height.
     * 
     * @param source the source block to start from
     * @param height the height to destroy the crop down to
     */
    private static void destroyCropFromTop(Block source, double height) {

        Logging.debugToConsole("[Crop Destroy From Top] Crop Height: " + height);

        for (double y = height; y >= 0; y--) {

            if (source.getLocation().getY() + y > 316) {
                y = 316 - source.getLocation().getY();
            }

            Block newBlock = source.getWorld().getBlockAt(source.getLocation().add(0, y, 0));

            Logging.debugToConsole("[Crop Destroy From Top] Checking Block: " + newBlock.getType() + " Location: "
                    + newBlock.getLocation().toVector());

            if (newBlock.getType() == Material.AIR) {
                break;
            }

            else {

                Logging.debugToConsole("[Crop Destroy From Top] Destroying Crop: " + newBlock.getType() + " Location: "
                        + newBlock.getLocation().toVector());
                newBlock.setType(Material.AIR);
            }

        }

    }

}