package dev.boooiil.historia.core.proficiency.runnable;

import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ApplyProficiencySkillEnchants {

    private final HistoriaPlayer historiaPlayer;
    private final PlayerInventory inventory;

    public ApplyProficiencySkillEnchants(HistoriaPlayer historiaPlayer, PlayerInventory inventory) {
        this.historiaPlayer = historiaPlayer;
        this.inventory = inventory;
    }

    public void doApplyEnchants() {

        inventory.iterator().forEachRemaining(item -> {

            if (item == null || item.getItemMeta() == null)
                return;

            if (historiaPlayer.getProficiency().getSkills().hasSkillEnchants()) {

                Enchantment enchant = historiaPlayer.getProficiency().getSkills().getSkillEnchantment(item.getType());
                ItemMeta itemMeta = item.getItemMeta();

                if (enchant != null && !itemMeta.hasEnchant(enchant)) {

                    add(item, enchant, historiaPlayer);

                }

                else if (enchant == null && itemMeta.hasEnchants()) {

                    remove(item, historiaPlayer);

                }

            } else {

                if (item.getItemMeta().hasEnchants()) {

                    Logging.debugToConsole(item.getEnchantments().toString());

                    remove(item, historiaPlayer);

                }

            }

        });

    }

    private void add(ItemStack item, Enchantment enchant, HistoriaPlayer historiaPlayer) {

        if (item != null && item.getItemMeta() != null) {

            ItemMeta itemMeta = item.getItemMeta();

            Logging.debugToConsole(
                    historiaPlayer.getUsername() + " had an item in their inventory that wasn't enchanted.");

            itemMeta.addEnchant(enchant, 1, true);
            item.setItemMeta(itemMeta);

        }

    }

    private void remove(ItemStack item, HistoriaPlayer historiaPlayer) {

        if (item != null && item.getItemMeta() != null && !item.getItemMeta().hasEnchants()) {

            ItemMeta itemMeta = item.getItemMeta();

            Logging.debugToConsole(
                    historiaPlayer.getUsername() + " had an item in their inventory with an illegal enchant.");

            item.getItemMeta().getEnchants().forEach((enchant, level) -> {

                itemMeta.removeEnchant(enchant);

            });

            item.setItemMeta(itemMeta);

        }

    }

}
