package dev.boooiil.historia.core.handlers.player.playerInteractEntity;

import dev.boooiil.historia.core.proficiency.skills.SkillType;
import dev.boooiil.historia.core.util.Logging;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractEntityHandler extends BasePlayerInteractEntity {

    public PlayerInteractEntityHandler(PlayerInteractEntityEvent event) {
        super(event);
    }

    public void doDetermineEntityTypeAndRunInteraction() {

        switch (getEntity().getType()) {
            case PLAYER:
                doDetermineHumanInteraction();
                break;
            case CHICKEN:
            case COW:
            case PIG:
            case SHEEP:
            case TURTLE:
            case RABBIT:
            case FOX:
            case WOLF:
            case OCELOT:
            case CAT:
            case HORSE:
            case DONKEY:
            case MULE:
            case LLAMA:
            case PARROT:
            case POLAR_BEAR:
            case PANDA:
            case BEE:
            case DOLPHIN:
            case SQUID:
            case TROPICAL_FISH:
            case COD:
            case SALMON:
            case PUFFERFISH:
                doDetermineAnimalInteraction();
                break;
            case GUARDIAN:
            case ELDER_GUARDIAN:
            case DROWNED:
            case STRAY:
            case HUSK:
            case ZOMBIE_VILLAGER:
            case ZOMBIE_HORSE:
            case ZOMBIE:
            case SKELETON_HORSE:
            case SKELETON:
            case SPIDER:
            case CAVE_SPIDER:
            case SILVERFISH:
            case ENDERMITE:
            case ENDERMAN:
            case ENDER_DRAGON:
            case WITHER:
            case WITHER_SKELETON:
            case BLAZE:
            case GHAST:
            case MAGMA_CUBE:
            case SLIME:
            case ZOMBIFIED_PIGLIN:
            case PIGLIN:
            case PIGLIN_BRUTE:
            case STRIDER:
            case HOGLIN:
            case ZOGLIN:
            case VEX:
            case EVOKER:
            case VINDICATOR:
            case PILLAGER:
            case RAVAGER:
            case ILLUSIONER:
            case WITCH:
            case CREEPER:
                doDetermineMobInteraction();
                break;
            case ENDER_CRYSTAL:
            case ARMOR_STAND:
            case ITEM_FRAME:
            case PAINTING:
            case LEASH_HITCH:
            case MINECART:
            case MINECART_CHEST:
            case MINECART_FURNACE:
            case MINECART_TNT:
            case MINECART_HOPPER:
            case MINECART_MOB_SPAWNER:
            case MINECART_COMMAND:
            case DROPPED_ITEM:
            case EXPERIENCE_ORB:
            case AREA_EFFECT_CLOUD:
            default:
                break;

        }

    }

    private void doDetermineAnimalInteraction() {

        switch (getEntity().getType()) {
            case CHICKEN:
                doShearChicken();
                break;

            default:
                break;
        }

    }

    private void doDetermineMobInteraction() {

        switch (getEntity().getType()) {
            case ZOMBIE:
                break;

            default:
                break;
        }

    }

    private void doDetermineHumanInteraction() {

        // always gonna be player

    }

    private void doShearChicken() {

        // guard against player not having skill
        if (!this.getHistoriaPlayer().getProficiency().getSkills().hasSkill(SkillType.SHEAR_CHICKEN)) {
            Logging.debugToConsole(
                    "[PIEH#doShearChicken] Player " + this.getPlayer().getName()
                            + " does not have the skill to shear chickens.");
            return;
        }

        // guard against player not holding shears
        if (getHeldItem().getType() != Material.SHEARS) {
            Logging.debugToConsole(
                    "[PIEH#doShearChicken] Player " + this.getPlayer().getName() + " is not holding shears.");
            return;
        }

        Ageable ageableEntity = (Ageable) getEntity();

        // guard against entity not being an adult
        if (!ageableEntity.isAdult()) {
            Logging.debugToConsole(
                    "[PIEH#doShearChicken] Entity is not an adult.");
            return;
        }

        Damageable damageableItem = (Damageable) getHeldItem().getItemMeta();

        damageableItem.setDamage(damageableItem.getDamage() + 16);

        if (damageableItem.getDamage() >= getHeldItem().getType().getMaxDurability()) {
            getHeldItem().setAmount(0);
            getPlayer().playSound(getLocation(), "entity.item.break", 1, 1);
        }

        getHeldItem().setItemMeta((ItemMeta) damageableItem);
        ageableEntity.setBaby();
        getWorld().dropItemNaturally(getLocation(), new ItemStack(Material.FEATHER, NumberUtils.randomInt(1, 3)));

        Logging.debugToConsole(
                "[PIEH#doShearChicken] Player " + this.getPlayer().getName() + " sheared a chicken.");
    }

}
