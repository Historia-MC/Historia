package dev.boooiil.historia.handlers.pve;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.configuration.specific.IngotConfig;
import dev.boooiil.historia.util.NumberUtils;

public class EntityDeathHandler {

    private EntityDeathEvent event;
    private HistoriaPlayer historiaPlayer;

    public EntityDeathHandler(EntityDeathEvent event, HistoriaPlayer historiaPlayer) {
        this.event = event;
        this.historiaPlayer = historiaPlayer;
    }

    public void doDeath() {

        List<ItemStack> drops = event.getDrops();
        LivingEntity entity = event.getEntity();
        // boolean dropContainsLeather = drops.stream().anyMatch(itemStack ->
        // itemStack.getType() == Material.LEATHER);

        switch (entity.getType()) {

            case SHEEP: {
                IngotConfig ingotConfig = ConfigurationLoader.getIngotConfig();
                ItemStack leather = ingotConfig.getObject("LOW_LIGHT_SHEEP_LEATHER").getItemStack();
                drops.add(leather);
                break;
            }
            case COW: {
                List<ItemStack> newDrops = drops.stream().filter(itemStack -> itemStack.getType() != Material.LEATHER)
                        .toList();

                IngotConfig ingotConfig = ConfigurationLoader.getIngotConfig();
                ItemStack leather = ingotConfig.getObject("LOW_LIGHT_COW_LEATHER").getItemStack();
                newDrops.add(leather);
                break;
            }
            case PIG: {
                IngotConfig ingotConfig = ConfigurationLoader.getIngotConfig();
                ItemStack leather = ingotConfig.getObject("LOW_LIGHT_PIG_LEATHER").getItemStack();
                drops.add(leather);
                break;
            }
            case HORSE: {
                IngotConfig ingotConfig = ConfigurationLoader.getIngotConfig();
                ItemStack leather = ingotConfig.getObject("LOW_LIGHT_HORSE_LEATHER").getItemStack();
                drops.add(leather);
                break;
            }
            case CHICKEN: {

                if (historiaPlayer.getProficiency().getSkills().hasChanceExtraFeathers()) {
                    ItemStack feather = new ItemStack(Material.FEATHER, 1);
                    drops.add(feather);
                }

            }
            default: {
            }

        }

        if (historiaPlayer.getProficiency().getSkills().canHarvestBones()) {

            ItemStack bones = new ItemStack(Material.BONE, 1 * NumberUtils.randomInt(1, 4));
            drops.add(bones);

        }

        event.getDrops().clear();
        event.getDrops().addAll(drops);

    }

}
