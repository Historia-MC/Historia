package dev.boooiil.historia.core.handlers.player.playerInteractEntity;

import org.bukkit.event.player.PlayerInteractEntityEvent;

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
            case END_CRYSTAL:
            case ARMOR_STAND:
            case ITEM_FRAME:
            case PAINTING:
            case LEASH_KNOT:
            case MINECART:
            case CHEST_MINECART:
            case FURNACE_MINECART:
            case TNT_MINECART:
            case HOPPER_MINECART:
            case SPAWNER_MINECART:
            case COMMAND_BLOCK_MINECART:
            case ITEM:
            case EXPERIENCE_ORB:
            case AREA_EFFECT_CLOUD:
            default:
                break;

        }

    }

    private void doDetermineAnimalInteraction() {

        switch (getEntity().getType()) {

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

}
