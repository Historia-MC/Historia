package dev.boooiil.historia.misc;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReplaceBlocks {

    public static void doReplacement(Player player, Block brokenBlock, Material successBlock, Material failBlock, Material giveItem, Integer failChance, Integer experienceAmount, Integer itemAmount, Integer doubleChance, Integer blockAge, boolean reduceItem, Sound breakSound, String successMessage, String failMessage) {

        //Return if we don't get certain values.
        if (player == null) return;
        if (brokenBlock == null) return;
        if (successBlock == null) return;
        if (failChance < 0) return;
        if (itemAmount < 0) return;

        //Check to see if replacing the block can fail.
        boolean failed = failChance > 0 ? (int) (Math.random() * failChance) + 1 == failChance : false;

        //Check to see if we double the outcome.
        boolean doubleItems = doubleChance > 0 ? (int) (Math.random() * doubleChance) + 1 == doubleChance : false;

        //Check to see if the block is aged.
        boolean canAge = blockAge > 0 ? true : false;
        boolean fullAge = canAge ? ((Ageable) brokenBlock.getBlockData()).getAge() == blockAge : false;

        //If we specified that the player loses an item, do that now.
        if (reduceItem) player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        
        if (failed) {
            if (failBlock != null) brokenBlock.setType(failBlock);
            else brokenBlock.setType(successBlock);

            if (breakSound != null) player.getWorld().playSound(brokenBlock.getLocation(), breakSound, 15, 1);

            if (failMessage != null) player.sendMessage(failMessage);

        } else {

            //If the block is ageable (crops).
            if (canAge) {

                //If the crop is at its full age.
                if (fullAge) {

                    //Give the player the specified expereince.
                    player.giveExp(experienceAmount);

                    //Break the block naturally.
                    brokenBlock.breakNaturally();

                    //If the amount can be doubled, break the block again.
                    if (doubleItems) brokenBlock.breakNaturally();

                    //Set the block material to the given block.
                    brokenBlock.setType(successBlock);

                    //Play a sound if it was provided.
                    if (breakSound != null) player.getWorld().playSound(brokenBlock.getLocation(), breakSound, 15, 1);

                }
                
            } else {

                if (giveItem != null) {

                    //If the amount can be doubled, double the amount given.
                    if (doubleItems) itemAmount += itemAmount;

                    ItemStack dropItem = new ItemStack(giveItem);

                    dropItem.setAmount(itemAmount);

                    //Drop the item provided naturally.
                    player.getWorld().dropItemNaturally(brokenBlock.getLocation(), dropItem);

                }

                brokenBlock.setType(successBlock);

            }

        }
    }
}