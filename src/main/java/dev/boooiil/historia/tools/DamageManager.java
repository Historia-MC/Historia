package dev.boooiil.historia.tools;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import dev.boooiil.historia.Config;

public class DamageManager {

    private boolean baseAttack;
    private boolean sweepAttack;
    private boolean arrowAttack;

    public void initiate(EntityDamageEvent damageEvent) {

        DamageCause cause = damageEvent.getCause();
        Entity entity = damageEvent.getEntity();
        EntityType entityType = damageEvent.getEntityType();
        HumanEntity humanEntity;

        baseAttack = (cause == DamageCause.ENTITY_ATTACK && entity instanceof HumanEntity);
        sweepAttack = (cause == DamageCause.ENTITY_SWEEP_ATTACK && entity instanceof HumanEntity);
        arrowAttack = (cause == DamageCause.PROJECTILE && entityType == EntityType.ARROW
                && entity instanceof HumanEntity);

        if (baseAttack || sweepAttack || arrowAttack)
            humanEntity = (HumanEntity) entity;
        else
            return;

        calculateDamage(humanEntity);

    }

    private void calculateDamage(HumanEntity humanEntity) {

        PlayerInventory playerInventory = humanEntity.getInventory();

        ItemStack hand = playerInventory.getItemInMainHand();
        ItemStack offHand = playerInventory.getItemInOffHand();
        ItemStack helmet = playerInventory.getHelmet();
        ItemStack chestplate = playerInventory.getChestplate();
        ItemStack leggings = playerInventory.getLeggings();
        ItemStack boots = playerInventory.getBoots();

        String classType;
        String handWeaponType = hand.getItemMeta().getLocalizedName();
        String offHandWeaponType = offHand.getItemMeta().getLocalizedName();
        String helmetType = helmet.getItemMeta().getLocalizedName();
        String chestplateType = chestplate.getItemMeta().getLocalizedName();
        String leggingsType = leggings.getItemMeta().getLocalizedName();
        String bootsType = boots.getItemMeta().getLocalizedName();

        Config classConfig;
        Config handWeaponConfig = getConfigType(handWeaponType);
        Config offHandWeaponConfig = getConfigType(offHandWeaponType);
        Config helmetConfig = getConfigType(helmetType);
        Config chestplateConfig = getConfigType(chestplateType);
        Config leggingsConfig = getConfigType(leggingsType);
        Config bootsConfig = getConfigType(bootsType);

        Integer classBaseArmor;
        Integer classBaseHealth;
        Integer handWeaponDamage = handWeaponConfig.weaponDamage;
        Integer offHandWeaponDamage = offHandWeaponConfig.weaponDamage;
        Integer helmetArmor = helmetConfig.armorValue;
        Integer chestplateArmor = chestplateConfig.armorValue;
        Integer leggingsArmor = leggingsConfig.armorValue;
        Integer bootsArmor = bootsConfig.armorValue;

        Integer finalDamage;

        if (baseAttack && hand.getType() != Material.BOW) {

            finalDamage = handWeaponDamage;

            //C
            //
            //Do base attack damage
        }

        if (sweepAttack) {
            //do sweep attack damage
        }

        if (arrowAttack) {
            //Do arrow damage
        }

        

    }

    private Config getConfigType(String query) {

        return new Config(query);

    }

}
