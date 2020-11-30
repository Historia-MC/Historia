package dev.boooiil.historia.tools;

import com.sk89q.worldedit.antlr.ExpressionParser.AssignmentOperatorContext;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.Config;

public class DamageManager {

    private boolean baseAttack;
    private boolean sweepAttack;
    private boolean arrowAttack;

    private Double baseDamage;

    public void initiate(EntityDamageEvent damageEvent) {

        if (damageEvent instanceof EntityDamageByBlockEvent) return;
        if (!(damageEvent instanceof EntityDamageByEntityEvent)) return;

        EntityDamageByEntityEvent entityDamage = (EntityDamageByEntityEvent) damageEvent;

        DamageCause cause = damageEvent.getCause();
        Entity defender = damageEvent.getEntity();
        Entity attacker = entityDamage.getDamager();

        baseAttack = (cause == DamageCause.ENTITY_ATTACK);
        sweepAttack = (cause == DamageCause.ENTITY_SWEEP_ATTACK);
        arrowAttack = (cause == DamageCause.PROJECTILE);

        Bukkit.getLogger().info(cause.toString());
        Bukkit.getLogger().info(defender.toString());
        Bukkit.getLogger().info(attacker.toString());


        baseDamage = damageEvent.getDamage();

        if (!(defender instanceof LivingEntity && attacker instanceof LivingEntity)) {

            Bukkit.getLogger().warning("ENTITY WAS NOT LIVING, DEFENDER TYPE: " + defender.getType());
            Bukkit.getLogger().warning("ENTITY WAS NOT LIVING, ATTACKER TYPE: " + attacker.getType());
            
            return;
        }

        // Cancel vanilla damage event.
        //damageEvent.setCancelled(true);

        Bukkit.getLogger().info("Base Damage: " + damageEvent.getDamage());

        if (baseAttack || sweepAttack || arrowAttack) damageEvent.setDamage(calculateDamage(defender, attacker));
        else Bukkit.getLogger().warning("ENTITY DAMAGE WAS NOT CAUSED BY REGISTERED ATTACKS. ACTUAL DAMAGE CAUSE: " + cause);

        Bukkit.getLogger().info("Final Damage: " + damageEvent.getFinalDamage());

    }

    private double calculateDamage(Entity defender, Entity attacker) {

        //Apply entities to living entities

        LivingEntity defenderLiving = (LivingEntity) defender;
        LivingEntity attackerLiving = (LivingEntity) attacker;

        Config defenderClass;
        Config attackerClass;

        EntityEquipment defenderInventory = defenderLiving.getEquipment();
        EntityEquipment attackerInventory = attackerLiving.getEquipment();

        ItemStack[] armorContents = defenderInventory.getArmorContents();

        ItemStack defenderHelmet = armorContents[0] != null ? armorContents[0] : new ItemStack(Material.AIR);
        ItemStack defenderChestplate = armorContents[1] != null ? armorContents[1] : new ItemStack(Material.AIR);
        ItemStack defenderLeggings = armorContents[2] != null ? armorContents[2] : new ItemStack(Material.AIR);
        ItemStack defenderBoots = armorContents[3] != null ? armorContents[3] : new ItemStack(Material.AIR);

        Bukkit.getLogger().info(defenderHelmet.toString());
        Bukkit.getLogger().info(defenderChestplate.toString());
        Bukkit.getLogger().info(defenderLeggings.toString());
        Bukkit.getLogger().info(defenderBoots.toString());

        Double defenderHelmetArmor = defenderHelmet.getType() != Material.AIR ? getConfigType(defenderHelmet.getItemMeta().getLocalizedName()).armorValue : 0.0;
        Double defenderChestplateArmor = defenderChestplate.getType() != Material.AIR ? getConfigType(defenderChestplate.getItemMeta().getLocalizedName()).armorValue : 0.0;
        Double defenderLeggingsArmor = defenderLeggings.getType() != Material.AIR ? getConfigType(defenderLeggings.getItemMeta().getLocalizedName()).armorValue : 0.0;
        Double defenderBootsArmor = defenderBoots.getType() != Material.AIR ? getConfigType(defenderBoots.getItemMeta().getLocalizedName()).armorValue : 0.0;

        ItemStack attackerWeapon;

        if (arrowAttack) attackerWeapon = attackerInventory.getItemInMainHand().getType() == Material.BOW ? attackerInventory.getItemInMainHand() : attackerInventory.getItemInOffHand();
        else attackerWeapon = attackerInventory.getItemInMainHand().getType() != Material.AIR ? attackerInventory.getItemInMainHand() : new ItemStack(Material.AIR);

        Bukkit.getLogger().info(attackerWeapon.toString());

        Double attackerWeaponDamage = attackerWeapon.getType() != Material.AIR ? getConfigType(attackerWeapon.getItemMeta().getLocalizedName()).weaponDamage : baseDamage;
        Double totalArmor = defenderHelmetArmor + defenderChestplateArmor + defenderLeggingsArmor + defenderBootsArmor;
        Double finalDamage;

        if (baseAttack) {

            if (attacker instanceof HumanEntity) finalDamage = totalArmor > 0 ? attackerWeaponDamage % totalArmor : attackerWeaponDamage;
            else finalDamage = totalArmor > 0 ? baseDamage % totalArmor : baseDamage;

        }

        else if (sweepAttack) {

            if (attacker instanceof HumanEntity) finalDamage = totalArmor > 0 ? attackerWeaponDamage % totalArmor : attackerWeaponDamage;
            else finalDamage = totalArmor > 0 ? baseDamage % totalArmor : baseDamage;

        }

        else if (arrowAttack) {

            if (attacker instanceof HumanEntity) finalDamage = totalArmor > 0 ? attackerWeaponDamage % totalArmor : attackerWeaponDamage;
            else finalDamage = totalArmor > 0 ? baseDamage % totalArmor : baseDamage;

        }

        else return baseDamage;
        
        String log = "Final Damage: " + finalDamage + " Helmet Armor: " + defenderHelmetArmor + " Chestplate Armor: "
                + defenderChestplateArmor + " Leggings Armor: " + defenderLeggingsArmor + " Boots Armor: "
                + defenderBootsArmor + "Total Armor: " + totalArmor;

        Bukkit.getLogger().info(log);

        //defenderLiving.damage(finalDamage);
        return finalDamage;
    }

    private Config getConfigType(String query) {

        return new Config(query);

    }

}
