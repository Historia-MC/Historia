package dev.boooiil.historia.core.handlers.playerInteraction;

import dev.boooiil.historia.core.util.Logging;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RightClickStonecutter extends BaseInteractionEventBlock {

    public RightClickStonecutter(PlayerInteractEvent event) {
        super(event);
    }

    @Override
    public void doInteraction() {

        if (!this.blockIsType(Material.STONECUTTER))
            return;
        if (this.getHeldItem() == null)
            return;

        Pattern pattern = Pattern.compile(".*_sword|.*_axe", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.getHeldItem().getType().toString());

        Logging.debugToConsole(
                "[RightClickStonecutter] Player " + this.getPlayer().getName() + " right clicked a stonecutter.");
        Logging.debugToConsole("[RightClickStonecutter] Player " + this.getPlayer().getName() + " has proficiency "
                + this.getHistoriaPlayer().getProficiency().getName() + ".");
        Logging.debugToConsole(
                "[RightClickStonecutter] Held Item: " + this.getHeldItem().getType().toString().toLowerCase() + ".");
        Logging.debugToConsole("[RightClickStonecutter] Held Item Enchantments: "
                + this.getHeldItem().getEnchantments().toString() + ".");
        Logging.debugToConsole("[RightClickStonecutter] Held Item Matches: " + matcher.matches());

        // if not sword or axe
        if (!matcher.matches()) return;

        if (!this.getHistoriaPlayer().getProficiency().getSkills().canApplySharpness()) {
            Logging.infoToPlayer("You don't know how to sharpen this item.", this.getPlayer().getUniqueId());
            return;
        } else {

            event.setCancelled(true);
            if (increaseSharpness()) {
                Logging.infoToPlayer(
                        "You sharpened your " + this.getHeldItem().getItemMeta().getLocalizedName() + "!",
                        this.getPlayer().getUniqueId());
            } else {
                Logging.infoToPlayer("Your " + this.getHeldItem().getItemMeta().getDisplayName()
                        + " is already as sharp as it can be!", this.getPlayer().getUniqueId());
            }

        }

    }

    private boolean increaseSharpness() {

        if (!this.isMaxSharpness()) {

            int currentSharpnessLevel = this.getHeldItem().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            int increasedSharpnessLevel = currentSharpnessLevel + 1;
            int adjustedSharpnessUses = (int) Math.round(increasedSharpnessLevel * 1.5);

            Logging.debugToConsole("[RightClickStonecutter] Current Sharpness Level: " + currentSharpnessLevel);
            Logging.debugToConsole("[RightClickStonecutter] Increased Sharpness Level: " + increasedSharpnessLevel);
            Logging.debugToConsole("[RightClickStonecutter] Adjusted Sharpness Uses: " + adjustedSharpnessUses);

            ItemMeta itemMeta = this.getHeldItem().getItemMeta();
            List<String> currentLore = itemMeta.getLore();

            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, increasedSharpnessLevel, true);
            
            if (currentSharpnessLevel > 0) {

                currentLore.set(currentLore.size() - 1, "Sharpness (" + "I".repeat(increasedSharpnessLevel) + "): " + adjustedSharpnessUses
                    + "/" + adjustedSharpnessUses);

            }

            else {

                currentLore.add("");
                currentLore.add("Sharpness (" + "I".repeat(increasedSharpnessLevel) + "): " + adjustedSharpnessUses
                    + "/" + adjustedSharpnessUses);

            }

            itemMeta.setLore(currentLore);
            this.getHeldItem().setItemMeta(itemMeta);

            return true;

        }

        else
            return false;
    }

    private boolean isMaxSharpness() {
        return this.getHeldItem().getEnchantmentLevel(Enchantment.DAMAGE_ALL) >= 3;
    }
}
