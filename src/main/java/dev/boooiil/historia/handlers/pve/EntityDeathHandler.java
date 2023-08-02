package dev.boooiil.historia.handlers.pve;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.configuration.specific.IngotConfig;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.NumberUtils;

public class EntityDeathHandler {

    private EntityDeathEvent event;
    private HistoriaPlayer historiaPlayer;

    public EntityDeathHandler(EntityDeathEvent event, HistoriaPlayer historiaPlayer) {
        this.event = event;
        this.historiaPlayer = historiaPlayer;
    }

    public void doDeath() {

        LivingEntity entity = event.getEntity();

        switch (entity.getType()) {

            case SHEEP: {
                Logging.debugToConsole("Sheep died.");

                if (historiaPlayer.getProficiency().getSkills().canHarvestLeather()) {
                    IngotConfig ingotConfig = ConfigurationLoader.getIngotConfig();
                    ItemStack leather = ingotConfig.getObject("LOW_LIGHT_SHEEP_LEATHER").getItemStack();
                    event.getDrops().add(leather);
                }

                break;
            }
            case COW: {
                Logging.debugToConsole("Cow died.");

                if (historiaPlayer.getProficiency().getSkills().canHarvestLeather()) {
                    IngotConfig ingotConfig = ConfigurationLoader.getIngotConfig();

                ItemStack leather = ingotConfig.getObject("LOW_LIGHT_COW_LEATHER").getItemStack();
                event.getDrops().clear();
                event.getDrops().add(leather);
                event.getDrops().add(new ItemStack(Material.BEEF, 1 * NumberUtils.randomInt(1, 3)));
                }
                
                break;
            }
            case PIG: {
                Logging.debugToConsole("Pig died.");

                if (historiaPlayer.getProficiency().getSkills().canHarvestLeather()) {
                    IngotConfig ingotConfig = ConfigurationLoader.getIngotConfig();
                    ItemStack leather = ingotConfig.getObject("LOW_LIGHT_PIG_LEATHER").getItemStack();
                    event.getDrops().add(leather);
                }

                break;
            }
            case HORSE: {
                Logging.debugToConsole("Horse died.");

                if (historiaPlayer.getProficiency().getSkills().canHarvestLeather()) {
                    IngotConfig ingotConfig = ConfigurationLoader.getIngotConfig();
                    ItemStack leather = ingotConfig.getObject("LOW_LIGHT_HORSE_LEATHER").getItemStack();
                    event.getDrops().add(leather);
                }

                break;
            }
            case CHICKEN: {
                Logging.debugToConsole("Chicken died.");

                if (historiaPlayer.getProficiency().getSkills().hasChanceExtraFeathers()) {
                    ItemStack feather = new ItemStack(Material.FEATHER, 1);
                    event.getDrops().add(feather);
                }

                break;

            }
            default: {
                Logging.debugToConsole(entity.getType().toString() + " died.");
            }

        }

        if (historiaPlayer.getProficiency().getSkills().canHarvestBones()) {

            ItemStack bones = new ItemStack(Material.BONE, 1 * NumberUtils.randomInt(1, 4));
            event.getDrops().add(bones);

        }

    }

}
