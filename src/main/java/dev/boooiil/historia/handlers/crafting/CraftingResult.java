package dev.boooiil.historia.handlers.crafting;

import java.util.List;
import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.classes.enums.ExperienceTypes.CraftingSources;
import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.classes.items.craftable.Armor;
import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.classes.items.craftable.Weapon;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.NumberUtils;

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

            historiaPlayer.increaseExperience(CraftingSources.ARMOR_CRAFT.getKey());

        } else if (craftedItem instanceof Weapon) {

            Logging.debugToConsole("[generateRandomModifiers] Generating Weapon Modifiers");

            generateWeaponModifiers();
            
            historiaPlayer.increaseExperience(CraftingSources.WEAPON_CRAFT.getKey());

        }

        else {

            Logging.debugToConsole("[generateRandomModifiers] Was other item.");

            historiaPlayer.increaseExperience(CraftingSources.OTHER_CRAFT.getKey());

        };

    }

    private void generateArmorModifiers() {

        CraftingTableInspector inspector = new CraftingTableInspector(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        Armor armor = (Armor) craftedItem;

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());
        int rolledDurability = armor.getMaxDurabilityValue();
        double rolledArmor = armor.getRandomDefenseValue();

        ItemMeta meta = result.getItemMeta();
        Damageable damageable = (Damageable) meta;
        AttributeModifier armorModifier = new AttributeModifier(
                UUID.randomUUID(), "Attack Speed", rolledArmor, AttributeModifier.Operation.ADD_NUMBER,
                result.getType().getEquipmentSlot());

        int adjustedDurability = result.getType().getMaxDurability() - rolledDurability;

        List<String> lore = List.of(
                "",
                "§7Class - " + armor.getWeightClass(),
                "",
                "§7Armor - " + NumberUtils.roundDouble(rolledArmor, 2),
                "§7Weight - " + armor.getWeight());

        damageable.setDamage(adjustedDurability);
        meta.setLore(lore);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);

        Logging.debugToConsole("[generateArmorModifiers] Durability: " + rolledDurability);
        Logging.debugToConsole("[generateArmorModifiers] Durability: " + adjustedDurability);

        result.setItemMeta(meta);

    }

    private void generateWeaponModifiers() {

        CraftingTableInspector inspector = new CraftingTableInspector(inventory.getContents());
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
        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "Damage", rolledDamage,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier attackSpeedModifier = new AttributeModifier(UUID.randomUUID(), "Attack Speed",
                rolledAttackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        int adjustedDurability = result.getType().getMaxDurability() - rolledDurability;

        List<String> lore = List.of(
                "",
                "§7Class - " + weapon.getWeightClass(),
                "",
                "§7Damage - " + NumberUtils.roundDouble( rolledDamage, 2),
                "§7Attack Speed - " + NumberUtils.roundDouble( rolledAttackSpeed, 2),
                "§7Knockback - " + NumberUtils.roundDouble( rolledKnockback, 2),
                "§7Sweeping - " + NumberUtils.roundDouble( rolledSweeping, 2),
                "§7Weight - " + weapon.getWeight());

        damageable.setDamage(adjustedDurability);
        meta.setLore(lore);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);

        Logging.debugToConsole("[generateArmorModifiers] Durability: " + rolledDurability);
        Logging.debugToConsole("[generateArmorModifiers] Adjusted Durability: " + adjustedDurability);

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
