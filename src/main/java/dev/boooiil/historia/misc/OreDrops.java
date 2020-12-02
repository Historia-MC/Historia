package dev.boooiil.historia.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class OreDrops {

    /**
     * Event is fired with the player being part of the event.
     * We pass the player into the current class by calling initiate().
     * 
     * initiate() will only accept a variable that contains Player since we specified that in the method.
     * 
     * If we try to pass Integer, String, Boolean, Etc. to initiate() it will return an error.
     * 
     */

     //int and Integer

    public static void doOreDrop(Player player, Block block, Material material, String itemName, Integer amount) {

        //if (class != "miner") return; 
        
        ItemStack droppedItem = new ItemStack(material, amount);

        ItemMeta droppedMeta = droppedItem.getItemMeta();

        droppedMeta.setLocalizedName(itemName);

        droppedMeta.setDisplayName(itemName);

        droppedItem.setItemMeta(droppedMeta);

        Location brokeBlock = block.getLocation();

        brokeBlock.getWorld().dropItemNaturally(brokeBlock, droppedItem);

    } 
    





    
    public static void initiate(BlockBreakEvent event) {

        Block block = event.getBlock();

        if (block.getType()== Material.COAL_BLOCK) {
            Player player = event.getPlayer(); 
            
            PlayerInventory inventory = player.getInventory();

            ItemStack mainHand = inventory.getItemInMainHand();

            


            ItemMeta mainHandMeta = mainHand.getItemMeta();

            if (mainHandMeta instanceof Damageable) { //Sees if item in mainhand is damageable

                Damageable mainHandDamageable = (Damageable) mainHandMeta; //Converts itemMeta into damageable

                int mainHandDamage = mainHandDamageable.getDamage() + 1; //Gets current damage for item and adds 1

                //int x = 1;
                //int y = x + 1
                //if (y >= x) do this 
                

                if (mainHandDamage >= mainHand.getType().getMaxDurability()) {

                    inventory.setItemInMainHand(new ItemStack(Material.AIR));

                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 15, 1);



                } else {

                    mainHandDamageable.setDamage(mainHandDamage);

                }


            

                //sword: 10/10 => #getDamage() => 0
                //sword: 9/10 => #getDamage() => 1 
                //sword: 10/10 => #getDamage() + 2 => sword: 8/10





                /**
                 * 
                 * Types of Entites:
                 *  Entity //All entities on the server.
                 *  LivingEntity //All living (hearts) entities on the server.
                 *  HumanEntity //NPC or Players.
                 *  Breedable //Any entity that can breed.
                 *  Creature //LivingEntity excluding Player
                 *  Damageable //Any entity that can be damaged.
                 *  ComplexLivingEntity //Ender Dragon.
                 *  Player //Players
                 *  Bat //Bats
                 *  Zombie //Zombies
                 *  Etc.
                 * 
                 * 
                 * 
                 * 
                 * 
                 * LivingEntity boooiil;
                 * 
                 * boooiil instanceof Player => true
                 * boooiil instanceof HumanEntity => true
                 * 
                 * if (boooiil instanceof Player) {
                 *      
                 *      //Since we checked if boooiil was a player we can cast Player (basically convert LivingEntity to a Player type) onto boooiil.
                 *      Player player = (Player) boooiil; //This converts (LivingEntity) boooiil into (Player) boooiil.
                 * }
                 * 
                 * LivingEntity bat;
                 * 
                 * bat instanceof Player => false
                 * bat instanceof HumanEntity => false
                 * 
                 * if (bat instanceof Player) {
                 *  
                 *      //Will never run because bat is not a player.
                 * 
                 * }
                 * 
                 * We do this check for sanity, else something like:
                 * 
                 * Player player = (Player) bat; //This would try to convert (LivingEntity) bat into (Player) bat but fails.
                 * 
                 * would create errors within the plugin.
                 * 
                 */



            }

            event.setCancelled(true);
            
            block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT));


        }


    }
}
