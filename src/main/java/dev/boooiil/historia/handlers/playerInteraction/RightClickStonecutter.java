package dev.boooiil.historia.handlers.playerInteraction;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.util.Logging;

public class RightClickStonecutter extends BaseInteractionEventBlock {

    public RightClickStonecutter(PlayerInteractEvent event) {
        super(event);
    }

    @Override
    public void doInteraction() {

        if (this.blockIsType(Material.STONECUTTER)) {

            // if not sword or axe
            if (!this.getHeldItem().getType().toString().toLowerCase().matches("sword|axe")) {
                
                event.setCancelled(true);
                Logging.infoToPlayer("This item can't be sharpened.", this.getPlayer().getUniqueId());
                
            } 

            // if player can apply sharpness
            else if (this.getHistoriaPlayer().getProficiency().getSkills().canApplySharpness()) {

                event.setCancelled(true);
                if (increaseSharpness()) {
                    Logging.infoToPlayer("You sharpened your " + this.getHeldItem().getType().toString().toLowerCase() + "!", this.getPlayer().getUniqueId());
                } else {
                    Logging.infoToPlayer("Your " + this.getHeldItem().getType().toString().toLowerCase() + " is already as sharp as it can be!", this.getPlayer().getUniqueId());
                }

            }
            else {
                Logging.infoToPlayer("You don't know how to sharpen this item.", this.getPlayer().getUniqueId());
            }

            

        }

    }

    private boolean increaseSharpness() {


        if (!this.isMaxSharpness()) {

            int currentSharpnessLevel = this.getHeldItem().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            int increasedSharpnessLevel = currentSharpnessLevel + 1;
            int adjustedSharpnessUses = (int) Math.round(currentSharpnessLevel * 1.5);

            ItemMeta itemMeta = this.getHeldItem().getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); 
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, increasedSharpnessLevel, true);
            itemMeta.getLore().add("");
            itemMeta.getLore().add("Sharpness (" + "I".repeat(increasedSharpnessLevel) + "): " + adjustedSharpnessUses + "/" + adjustedSharpnessUses);
            
            return true;
            
        }

        else return false;
    }

    private boolean isMaxSharpness() {
        return this.getHeldItem().getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 3;    
    }
}
