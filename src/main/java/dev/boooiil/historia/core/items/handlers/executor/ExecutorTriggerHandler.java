package dev.boooiil.historia.core.items.handlers.executor;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.HistoriaItemData;
import dev.boooiil.historia.core.items.data.ExecutorData;
import dev.boooiil.historia.core.items.types.Triggers;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map.Entry;

public class ExecutorTriggerHandler {

    private static final NamespacedKey EXECUTOR_KEY = HistoriaCore.getNamespacedKey("executor");

    public static void executeAction(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();

        if (event.isSneaking()) {
            execute(player, Triggers.CROUCH);
        } else {
            execute(player, Triggers.UNCROUCH);
        }
    }

    public static void executeAction(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof HumanEntity humanEntity)) {
            return;
        }

        execute(humanEntity, Triggers.THROW);
    }

    public static void executeAction(EntityToggleSwimEvent event) {

        CoreLogger.debugToConsole("" + (event.getEntity() instanceof HumanEntity),
                " " + ((HumanEntity) event.getEntity()).isSwimming());

        if (!(event.getEntity() instanceof HumanEntity humanEntity)) {
            return;
        }

        if (!event.isSwimming()) {
            return;
        }

        execute(humanEntity, Triggers.SWIM);
    }

    public static void executeAction(PlayerToggleSprintEvent event) {
        if (event.isSprinting()) {
            Player player = event.getPlayer();

            execute(player, Triggers.SPRINT);
        }
    }

    public static void executeAction(PlayerJumpEvent event) {

        Player player = event.getPlayer();

        execute(player, Triggers.JUMP);
    }

    public static void executeAction(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        // if bottle
        if (event.getItem().getType() == Material.GLASS_BOTTLE) {
            execute(player, Triggers.DRINK);
        }

        // else some other consumable
        else {
            execute(player, Triggers.EAT);
        }

    }

    public static void executeAction(EntityDropItemEvent event) {
        if (!(event.getEntity() instanceof HumanEntity humanEntity)) {
            return;
        }

        execute(humanEntity, Triggers.DROP);
    }

    public static void executeAction(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof HumanEntity humanEntity)) {
            return;
        }

        execute(humanEntity, Triggers.PICKUP);

    }

    public static void executeAction(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (player == null) {
            return;
        }

        execute(player, Triggers.PLACE);

    }

    public static void executeAction(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();

        execute(player, Triggers.SWAP_TO_OFFHAND);
    }

    public static void executeAction(InventoryOpenEvent event) {

        HumanEntity humanEntity = event.getPlayer();

        execute(humanEntity, Triggers.OPEN_INVENTORY);
    }

    public static void executeAction(InventoryCloseEvent event) {

        HumanEntity humanEntity = event.getPlayer();

        execute(humanEntity, Triggers.CLOSE_INVENTORY);
    }

    public static void executeAction(EntityDamageByEntityEvent event) {

        boolean isAttackerHuman = event.getDamager() instanceof HumanEntity;
        boolean isVictimLiving = event.getEntity() instanceof LivingEntity;
        boolean isBothLiving = isAttackerHuman && isVictimLiving;
        boolean isAttackerLivingVictimNot = isAttackerHuman && !isVictimLiving;

        if (!isBothLiving || isAttackerLivingVictimNot) {
            return;
        }

        HumanEntity damager = (HumanEntity) event.getDamager();

        execute(damager, Triggers.DAMAGE_ENTITY);

    }

    public static void executeAction(EntityInteractEvent event) {

        // TODO: This will probably fire PlayerInteractEvent as well.

        if (!(event.getEntity() instanceof HumanEntity humanEntity)) {
            return;
        }

        execute(humanEntity, Triggers.INTERACT_ENTITY);

    }

    public static void executeAction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // if block is not air
        if (event.getClickedBlock() != null) {
            if (player.isSneaking()) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    execute(player, Triggers.SNEAK_RIGHT_CLICK_BLOCK);
                } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    execute(player, Triggers.SNEAK_LEFT_CLICK_BLOCK);
                }
            } else {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    execute(player, Triggers.RIGHT_CLICK_BLOCK);
                } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    execute(player, Triggers.LEFT_CLICK_BLOCK);
                }
            }

        }

        // if block is air
        else {
            if (player.isSneaking()) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    execute(player, Triggers.SNEAK_RIGHT_CLICK);
                } else if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    execute(player, Triggers.SNEAK_LEFT_CLICK);
                }
            } else {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    execute(player, Triggers.RIGHT_CLICK);
                } else if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    execute(player, Triggers.LEFT_CLICK);
                }
            }
        }

    }

    public static void execute(HumanEntity humanEntity, Triggers trigger) {

        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, humanEntity.getInventory().getItem(0)); // hb 0
        items.put(1, humanEntity.getInventory().getItem(1)); // hb 1
        items.put(2, humanEntity.getInventory().getItem(2)); // hb 2
        items.put(3, humanEntity.getInventory().getItem(3)); // hb 3
        items.put(4, humanEntity.getInventory().getItem(4)); // hb 4
        items.put(5, humanEntity.getInventory().getItem(5)); // hb 5
        items.put(6, humanEntity.getInventory().getItem(6)); // hb 6
        items.put(7, humanEntity.getInventory().getItem(7)); // hb 7
        items.put(8, humanEntity.getInventory().getItem(8)); // hb 8
        items.put(36, humanEntity.getInventory().getItem(36)); // boots
        items.put(37, humanEntity.getInventory().getItem(37)); // leggings
        items.put(38, humanEntity.getInventory().getItem(38)); // chestplate
        items.put(39, humanEntity.getInventory().getItem(39)); // helmet
        items.put(40, humanEntity.getInventory().getItem(40)); // offhand

        for (Entry<Integer, ItemStack> item : items.entrySet()) {
            if (item != null) {
                HistoriaItemData historiaItemData = HistoriaItemData.fromStack(item.getValue());

                boolean hasExecutor = historiaItemData.hasData(EXECUTOR_KEY);

                CoreLogger.debugToConsole("Checking item in slot " + item.getKey() + " for trigger " + trigger);

                if (!hasExecutor) {
                    continue;
                } else {
                    ExecutorData executorData = historiaItemData.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

                    executorData.execute(humanEntity, item.getKey(), item.getValue(), trigger);
                }
            }

        }
    }
}
