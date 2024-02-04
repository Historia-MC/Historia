package dev.boooiil.historia.core.handlers.player.playerInteract;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.core.proficiency.skills.SkillType;
import dev.boooiil.historia.core.util.Logging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class PlayerInteractHandler extends BasePlayerInteract {

    public PlayerInteractHandler(PlayerInteractEvent event) {
        super(event);
    }

    public void doDetermineActionTypeAndRunInteraction() {

        switch (getAction()) {
            case LEFT_CLICK_AIR:
                doAirInteraction();
                break;

            case LEFT_CLICK_BLOCK:
                doBlockInteraction();
                break;
            default:
                break;
        }
    }

    private void doAirInteraction() {

        // if arrow in main hand and flint and steel in offhand, ignite arrow
        if (getHeldItem().getType() == Material.ARROW && getOffHandItem().getType() == Material.FLINT_AND_STEEL) {
            Logging.debugToConsole("[PIH#doAirInteraction] Player: " + getPlayer().getName()
                    + " is igniting an arrow with a flint and steel.");
            doIgniteArrow();
            return;
        }

    }

    /**
     * Makes an arrow "ignited" when held in the offhand and a flint and steel is
     * held in the main hand.
     */
    private void doIgniteArrow() {
        ItemMeta arrowMeta = this.getHeldItem().getItemMeta();
        ItemMeta flintSteelMeta = this.getOffHandItem().getItemMeta();
        Damageable flintSteelDamageable = (Damageable) flintSteelMeta;

        arrowMeta.lore(List.of(
                Component.text("Ignited - 1/1", TextColor.color(255, 0, 0))));

        arrowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);

        this.getHeldItem().setItemMeta(arrowMeta);

        flintSteelDamageable.setDamage(flintSteelDamageable.getDamage() + 1);

        if (flintSteelDamageable.getDamage() >= getHeldItem().getType().getMaxDurability()) {
            getHeldItem().setAmount(0);
            getPlayer().playSound(getPlayer().getLocation(), "entity.item.break", 1, 1);
        }

        else {
            this.getOffHandItem().setItemMeta(flintSteelMeta);
        }
    }

    private void doBlockInteraction() {

        switch (getBlock().getType()) {
            case ANVIL:
                doAnvilInteraction();
                break;

            case STONECUTTER:
                doStonecutterInteraction();
                break;
            default:
                break;
        }

    }

    private void doAnvilInteraction() {
        if (!this.getHistoriaPlayer().getProficiency().getSkills().hasSkill(SkillType.CHANCE_NO_ANVIL_DAMAGE)) {

            event.setCancelled(true);
            Logging.infoToPlayer("Maybe there is someone more skilled that can do this...",
                    this.getPlayer().getUniqueId());

        }
    }

    private void doStonecutterInteraction() {

        Pattern pattern = Pattern.compile(".*_sword|.*_axe", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.getHeldItem().getType().toString());

        if (!matcher.matches()) {
            Logging.debugToConsole("[PIH#doStonecutterInteraction] Player " + this.getPlayer().getName()
                    + " right clicked a stonecutter with an invalid item " + this.getHeldItem().getType().toString());
            return;
        }

        if (!this.getHistoriaPlayer().getProficiency().getSkills().hasSkill(SkillType.APPLY_SHARPNESS)) {
            Logging.infoToPlayer("You don't know how to sharpen this item.", this.getPlayer().getUniqueId());
            Logging.debugToConsole("[PIH#doStonecutterInteraction] Player " + this.getPlayer().getName()
                    + " right clicked a stonecutter without the required skill.");
            return;
        }

        increaseSharpness();

    }

    private void increaseSharpness() {

        int currentSharpnessLevel = this.getHeldItem().getEnchantmentLevel(Enchantment.DAMAGE_ALL);

        if (currentSharpnessLevel >= 3) {
            Logging.infoToPlayer("Your " + this.getHeldItem().displayName().examinableName()
                    + " already has the max level of sharpness.", this.getPlayer().getUniqueId());
            return;
        }

        ItemMeta heldItemMeta = this.getHeldItem().getItemMeta();
        int increasedSharpnessLevel = currentSharpnessLevel + 1;
        int adjustedSharpnessUses = (int) Math.round(increasedSharpnessLevel * 1.5);

        heldItemMeta.addEnchant(Enchantment.DAMAGE_ALL, increasedSharpnessLevel, true);
        heldItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<Component> lore = new ArrayList<>();
        Component sharpnessLore = Component.text(
                "Sharpness (" + "I".repeat(increasedSharpnessLevel) + "): " + adjustedSharpnessUses
                        + "/" + adjustedSharpnessUses);

        for (Component loreLine : heldItemMeta.lore()) {
            if (!loreLine.examinableName().contains("Sharpness")) {
                lore.add(loreLine);
            }
        }

        lore.add(sharpnessLore);

        heldItemMeta.lore(lore);
        getHeldItem().setItemMeta(heldItemMeta);

        Logging.infoToPlayer("You sharpened your " + this.getHeldItem().displayName().examinableName() + "!",
                this.getPlayer().getUniqueId());
        Logging.debugToConsole("[PIH#increaseSharpness] Player " + this.getPlayer().getName()
                + " sharpened their " + this.getHeldItem().displayName().examinableName() + " to level "
                + increasedSharpnessLevel + ".");
    }

}
