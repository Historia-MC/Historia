package dev.boooiil.historia.handlers.crafting;

import java.util.List;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.classes.items.craftable.Armor;
import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.classes.items.craftable.Weapon;
import dev.boooiil.historia.util.Logging;

public class CraftingResult {

    private Inventory inventory;
    private ItemStack result;
    private CraftedItem craftedItem;
    private HistoriaPlayer historiaPlayer;

    public CraftingResult(Inventory inventory, ItemStack result, CraftedItem craftedItem,
            HistoriaPlayer historiaPlayer) {
        this.inventory = inventory;
        this.result = result;
        this.historiaPlayer = historiaPlayer;
        this.craftedItem = craftedItem;
    }

    public void generateRandomModifiers() {

        if (craftedItem instanceof Armor) {

            Logging.debugToConsole("[generateRandomModifiers] Generating Armor Modifiers");

            generateArmorModifiers();

        } else if (craftedItem instanceof Weapon) {

            Logging.debugToConsole("[generateRandomModifiers] Generating Weapon Modifiers");

            generateWeaponModifiers();

        }

        else {
        }
        ;

    }

    private void generateArmorModifiers() {

        TableInspector inspector = new TableInspector(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        Armor armor = (Armor) craftedItem;

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());
        int rolledDurability = armor.getMaxDurabilityValue();
        double rolledArmor = armor.getRandomDefenseValue();

        ItemMeta meta = result.getItemMeta();
        Damageable damageable = (Damageable) meta;
        List<String> lore = List.of(
                "Armor - " + rolledArmor,
                "Class - " + armor.getWeightClass(),
                "Weight - " + armor.getWeight());

        damageable.setDamage(rolledDurability);
        meta.setLore(lore);

        result.setItemMeta(meta);

    }

    private void generateWeaponModifiers() {

        TableInspector inspector = new TableInspector(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        Weapon weapon = (Weapon) craftedItem;

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());
        int rolledDurability = weapon.getRandomDurabilityValue();
        double rolledDamage = weapon.getDamageRandomValue();
        double rolledAttackSpeed = weapon.getSpeedRandomValue();
        double rolledKnockback = weapon.getKnockbackRandomValue();
        double rolledSweeping = weapon.getSweepRandomValue();

        ItemMeta meta = result.getItemMeta();
        Damageable damageable = (Damageable) meta;
        List<String> lore = List.of(
                "Damage - " + rolledDamage,
                "Attack Speed - " + rolledAttackSpeed,
                "Knockback - " + rolledKnockback,
                "Sweeping - " + rolledSweeping);

        damageable.setDamage(rolledDurability);
        meta.setLore(lore);

        result.setItemMeta(meta);

    }

    public float getQualityBonus(List<String> materials) {

        int high = 0;
        int medium = 0;
        int low = 0;
        int complexity = materials.size();
        float mediumWeight = 50 / (float) complexity;
        float highWeight = 100 / (float) complexity;

        for (String material : materials) {

            if (material.contains("HIGH")) {

                high++;

            } else if (material.contains("MEDIUM")) {

                medium++;

            } else if (material.contains("LOW")) {

                low++;

            }

        }

        Logging.debugToConsole("[getQualityBonus] High: " + high);
        Logging.debugToConsole("[getQualityBonus] Medium: " + medium);
        Logging.debugToConsole("[getQualityBonus] Low: " + low);
        Logging.debugToConsole("[getQualityBonus] Medium Weight: " + mediumWeight);
        Logging.debugToConsole("[getQualityBonus] High Weight: " + highWeight);
        Logging.debugToConsole("[getQualityBonus] Quality Bonus: " + ((medium * mediumWeight) + (high * highWeight)));

        return (medium * mediumWeight) + (high * highWeight);

    }

    public float getLevelBonus(int level) {

        return (float) level / 100;

    }
}
