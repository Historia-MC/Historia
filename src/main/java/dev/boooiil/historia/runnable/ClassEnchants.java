package dev.boooiil.historia.runnable;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;

public class ClassEnchants {

    private HistoriaPlayer historiaPlayer;
    private Inventory inventory;

    public ClassEnchants(HistoriaPlayer historiaPlayer, Inventory inventory) {
        this.historiaPlayer = historiaPlayer;
        this.inventory = inventory;
    }

    public void doApplyEnchants() {

        inventory.iterator().forEachRemaining(item -> {

            if (item.getType().toString().contains("PICKAXE")) {

                if (historiaPlayer.getProficiency().getSkills().hasEfficiencyPickaxe()) {

                    if (!item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

                        item.getItemMeta().addEnchant(Enchantment.DIG_SPEED, 1, true);

                    }

                } else {

                    if (item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

                        item.getItemMeta().removeEnchant(Enchantment.DIG_SPEED);

                    }

                }

            }

            if (item.getType().toString().contains("AXE")) {
                if (historiaPlayer.getProficiency().getSkills().hasEfficiencyAxe()) {

                    if (!item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

                        item.getItemMeta().addEnchant(Enchantment.DIG_SPEED, 1, true);

                    }

                } else {

                    if (item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

                        item.getItemMeta().removeEnchant(Enchantment.DIG_SPEED);

                    }

                }
            }

        });

    }

}
