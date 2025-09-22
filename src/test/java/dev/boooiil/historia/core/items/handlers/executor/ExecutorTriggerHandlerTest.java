package dev.boooiil.historia.core.items.handlers.executor;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.HistoriaItem;
import dev.boooiil.historia.core.items.HistoriaItemData;
import dev.boooiil.historia.core.items.component.ExecutorComponent;
import dev.boooiil.historia.core.items.data.ExecutorData;
import dev.boooiil.historia.core.items.executor.ItemExecutable;
import dev.boooiil.historia.core.items.types.Triggers;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.entity.LookAnchor;
import io.papermc.paper.entity.TeleportFlag;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.TriState;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExecutorTriggerHandlerTest {

    static ServerMock server;
    static HistoriaCore plugin;
    static Player player;

    final NamespacedKey EXECUTOR_KEY = HistoriaCore.getNamespacedKey("executor");

    HistoriaItem historiaItem1 = HistoriaCore.Companion.getITEM_REGISTRY().get(HistoriaCore.getNamespacedKey("Light_Bronze_Boots"));
    HistoriaItem historiaItem2 = HistoriaCore.Companion.getITEM_REGISTRY().get(HistoriaCore.getNamespacedKey("Light_Tin_Sword"));
    ExecutorComponent component1 = new ExecutorComponent(new HashMap<>());
    ExecutorComponent component2 = new ExecutorComponent(new HashMap<>());

    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up mock...");
        server = MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            plugin = MockBukkit.load(HistoriaCore.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player = server.addPlayer();
        System.out.println("Finished setup.");

    }

    @BeforeEach
    public void clearExecutables() {
        component1.executables().clear();
        component2.executables().clear();
        historiaItem1.getComponentHolder().put(EXECUTOR_KEY, component1);
        historiaItem2.getComponentHolder().put(EXECUTOR_KEY, component2);

        assertTrue(historiaItem1.getComponentHolder().containsKey(EXECUTOR_KEY));
        assertTrue(historiaItem2.getComponentHolder().containsKey(EXECUTOR_KEY));
        assertTrue(((ExecutorComponent) historiaItem1.getComponentHolder().get(EXECUTOR_KEY)).executables().isEmpty());
        assertTrue(((ExecutorComponent) historiaItem2.getComponentHolder().get(EXECUTOR_KEY)).executables().isEmpty());
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
        // EntityDamageByEntityEvent
    void testExecuteAction() {
        Triggers trigger = Triggers.DAMAGE_ENTITY;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(player, player, DamageCause.ENTITY_ATTACK, 1.0);

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());

    }

    @Test
        // EntityDropItemEvent
    void testExecuteAction2() {

        Triggers trigger = Triggers.DROP;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityDropItemEvent event = new EntityDropItemEvent(player,
                player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ACACIA_BOAT)));

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // EntityInteractEvent
    void testExecuteAction3() {

        Triggers trigger = Triggers.INTERACT_ENTITY;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityInteractEvent event = new EntityInteractEvent(player, player.getWorld().getBlockAt(0, 0, 0));

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // EntityPickupItemEvent
    void testExecuteAction4() {

        Triggers trigger = Triggers.PICKUP;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityPickupItemEvent event = new EntityPickupItemEvent(player,
                player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ACACIA_BOAT)), 1);

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // EntityToggleSwimEvent
    void testExecuteAction5() {

        Triggers trigger = Triggers.SWIM;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityToggleSwimEvent event = new EntityToggleSwimEvent(player, true);

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // ProjectileLaunchEvent
    void testExecuteAction6() {

        Triggers trigger = Triggers.THROW;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        ProjectileLaunchEvent event = new ProjectileLaunchEvent(new FakeProjectile(player));

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());

    }

    @Test
        // InventoryCloseEvent
    void testExecuteAction7() {

        Triggers trigger = Triggers.CLOSE_INVENTORY;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        InventoryCloseEvent event = new InventoryCloseEvent(player.getOpenInventory());

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // InventoryOpenEvent
    void testExecuteAction8() {

        Triggers trigger = Triggers.OPEN_INVENTORY;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        InventoryOpenEvent event = new InventoryOpenEvent(player.getOpenInventory());

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // PlayerInteractEvent
    void testExecuteAction9() {

        // TODO: add other triggers
        Triggers trigger = Triggers.RIGHT_CLICK;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, null, null, null);

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // PlayerItemConsumeEvent
    void testExecuteAction10() {

        Triggers trigger = Triggers.EAT;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        PlayerItemConsumeEvent event = new PlayerItemConsumeEvent(player, new ItemStack(Material.APPLE));

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // PlayerSwapHandItemsEvent
    void testExecuteAction11() {

        // TODO: add other triggers
        Triggers trigger = Triggers.SWAP_TO_OFFHAND;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        PlayerSwapHandItemsEvent event = new PlayerSwapHandItemsEvent(player, player.getInventory().getItemInMainHand(),
                player.getInventory().getItemInOffHand());

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // PlayerToggleSneakEvent
    void testExecuteAction12() {
        Triggers trigger = Triggers.CROUCH;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(player, true);

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // PlayerToggleSprintEvent
    void testExecuteAction13() {
        Triggers trigger = Triggers.SPRINT;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        PlayerToggleSprintEvent event = new PlayerToggleSprintEvent(player, true);

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // PlayerJumpEvent
    void testExecuteAction14() {
        Triggers trigger = Triggers.JUMP;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        PlayerJumpEvent event = new PlayerJumpEvent(player, new Location(player.getWorld(), 0, 0, 0),
                new Location(player.getWorld(), 0, 0, 1));

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    @Test
        // BlockPlaceEvent
    void testExecuteAction15() {
        Triggers trigger = Triggers.PLACE;

        component1.executables().put(trigger, new ItemExecutable(List.of("boot"), 200, 2, false, false));
        component2.executables().put(trigger, new ItemExecutable(List.of("sword"), 200, 2, false, false));

        ItemStack mhi = historiaItem1.createItemStack();
        ItemStack ohi = historiaItem2.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        Block block = player.getWorld().getBlockAt(0, 0, 0);
        BlockState state = block.getState();

        BlockPlaceEvent event = new BlockPlaceEvent(block, state, block, player.getInventory().getItemInMainHand(),
                player, true,
                null);

        ExecutorTriggerHandler.executeAction(event);

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(trigger).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(trigger).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(trigger).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(trigger).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(trigger).uses());
        assertEquals(1, ohd.executables().get(trigger).uses());
    }

    class FakeProjectile implements Projectile {

        private final Player player;

        FakeProjectile(Player player) {
            this.player = player;
        }

        @Override
        public boolean addPassenger(@NotNull Entity arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addPassenger'");
        }

        @Override
        public boolean addScoreboardTag(@NotNull String arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addScoreboardTag'");
        }

        @Override
        public void broadcastHurtAnimation(@NotNull Collection<Player> arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'broadcastHurtAnimation'");
        }

        @Override
        public boolean collidesAt(@NotNull Location arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'collidesAt'");
        }

        @Override
        public @NotNull Entity copy() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'copy'");
        }

        @Override
        public @NotNull Entity copy(@NotNull Location arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'copy'");
        }

        @Override
        public @Nullable EntitySnapshot createSnapshot() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'createSnapshot'");
        }

        @Override
        public boolean eject() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'eject'");
        }

        /**
         * Gets the {@link ItemStack} that a player would select / create (in creative mode)
         * when using the pick block action on this entity.
         *
         * @return item stack result or an empty item stack
         */
        @Override
        public @NotNull ItemStack getPickItemStack() {
            return null;
        }

        @Override
        public boolean fromMobSpawner() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'fromMobSpawner'");
        }

        @Override
        public @Nullable String getAsString() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getAsString'");
        }

        @Override
        public @NotNull BoundingBox getBoundingBox() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getBoundingBox'");
        }

        @Override
        public int getEntityId() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getEntityId'");
        }

        @Override
        public @NotNull SpawnReason getEntitySpawnReason() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getEntitySpawnReason'");
        }

        @Override
        public @NotNull BlockFace getFacing() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getFacing'");
        }

        @Override
        public float getFallDistance() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getFallDistance'");
        }

        @Override
        public int getFireTicks() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getFireTicks'");
        }

        @Override
        public int getFreezeTicks() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getFreezeTicks'");
        }

        @Override
        public double getHeight() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getHeight'");
        }

        @Override
        public @Nullable EntityDamageEvent getLastDamageCause() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLastDamageCause'");
        }

        @Override
        public @NotNull Location getLocation() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLocation'");
        }

        @Override
        public @Nullable Location getLocation(@Nullable Location arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLocation'");
        }

        @Override
        public int getMaxFireTicks() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getMaxFireTicks'");
        }

        @Override
        public int getMaxFreezeTicks() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getMaxFreezeTicks'");
        }

        @Override
        public @NotNull List<Entity> getNearbyEntities(double arg0, double arg1, double arg2) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getNearbyEntities'");
        }

        @Override
        public @Nullable Location getOrigin() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getOrigin'");
        }

        @Override
        public @Nullable Entity getPassenger() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getPassenger'");
        }

        @Override
        public @NotNull List<Entity> getPassengers() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getPassengers'");
        }

        @Override
        public @NotNull PistonMoveReaction getPistonMoveReaction() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getPistonMoveReaction'");
        }

        @Override
        public float getPitch() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getPitch'");
        }

        @Override
        public int getPortalCooldown() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getPortalCooldown'");
        }

        @Override
        public @NotNull Pose getPose() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getPose'");
        }

        @Override
        public @NotNull EntityScheduler getScheduler() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getScheduler'");
        }

        @Override
        public @NotNull String getScoreboardEntryName() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getScoreboardEntryName'");
        }

        @Override
        public @NotNull Set<String> getScoreboardTags() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getScoreboardTags'");
        }

        @Override
        public @NotNull Server getServer() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getServer'");
        }

        @Override
        public @NotNull SpawnCategory getSpawnCategory() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getSpawnCategory'");
        }

        @Override
        public @NotNull Sound getSwimHighSpeedSplashSound() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getSwimHighSpeedSplashSound'");
        }

        @Override
        public @NotNull Sound getSwimSound() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getSwimSound'");
        }

        @Override
        public @NotNull Sound getSwimSplashSound() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getSwimSplashSound'");
        }

        @Override
        public int getTicksLived() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getTicksLived'");
        }

        @Override
        public @NotNull Set<Player> getTrackedBy() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getTrackedBy'");
        }

        /**
         * Checks to see if a player is currently tracking this entity.
         *
         * @param player the player to check
         * @return if the player is currently tracking this entity
         * @see #getTrackedBy()
         */
        @Override
        public boolean isTrackedBy(@NotNull Player player) {
            return false;
        }

        @Override
        public @NotNull Set<Player> getTrackedPlayers() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getTrackedPlayers'");
        }

        @Override
        public @NotNull EntityType getType() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getType'");
        }

        @Override
        public @NotNull UUID getUniqueId() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getUniqueId'");
        }

        @Override
        public @Nullable Entity getVehicle() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getVehicle'");
        }

        @Override
        public @NotNull Vector getVelocity() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getVelocity'");
        }

        @Override
        public double getWidth() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getWidth'");
        }

        @Override
        public @NotNull World getWorld() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getWorld'");
        }

        @Override
        public double getX() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getX'");
        }

        @Override
        public double getY() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getY'");
        }

        @Override
        public float getYaw() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getYaw'");
        }

        @Override
        public double getZ() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getZ'");
        }

        @Override
        public boolean hasFixedPose() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasFixedPose'");
        }

        @Override
        public boolean hasGravity() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasGravity'");
        }

        @Override
        public boolean hasNoPhysics() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasNoPhysics'");
        }

        @Override
        public boolean isCustomNameVisible() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isCustomNameVisible'");
        }

        @Override
        public boolean isDead() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isDead'");
        }

        @Override
        public boolean isEmpty() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
        }

        @Override
        public boolean isFreezeTickingLocked() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isFreezeTickingLocked'");
        }

        @Override
        public boolean isFrozen() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isFrozen'");
        }

        @Override
        public boolean isGlowing() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isGlowing'");
        }

        @Override
        public boolean isInBubbleColumn() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInBubbleColumn'");
        }

        @Override
        public boolean isInLava() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInLava'");
        }

        @Override
        public boolean isInPowderedSnow() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInPowderedSnow'");
        }

        @Override
        public boolean isInRain() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInRain'");
        }

        @Override
        public boolean isInWater() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInWater'");
        }

        @Override
        public boolean isInWaterOrBubbleColumn() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInWaterOrBubbleColumn'");
        }

        @Override
        public boolean isInWaterOrRain() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInWaterOrRain'");
        }

        @Override
        public boolean isInWaterOrRainOrBubbleColumn() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInWaterOrRainOrBubbleColumn'");
        }

        @Override
        public boolean isInWorld() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInWorld'");
        }

        @Override
        public boolean isInsideVehicle() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInsideVehicle'");
        }

        @Override
        public boolean isInvisible() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInvisible'");
        }

        @Override
        public boolean isInvulnerable() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isInvulnerable'");
        }

        @Override
        public boolean isOnGround() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isOnGround'");
        }

        @Override
        public boolean isPersistent() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isPersistent'");
        }

        @Override
        public boolean isSilent() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isSilent'");
        }

        @Override
        public boolean isSneaking() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isSneaking'");
        }

        @Override
        public boolean isTicking() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isTicking'");
        }

        @Override
        public boolean isUnderWater() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isUnderWater'");
        }

        @Override
        public boolean isValid() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isValid'");
        }

        @Override
        public boolean isVisibleByDefault() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isVisibleByDefault'");
        }

        @Override
        public boolean isVisualFire() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isVisualFire'");
        }

        /**
         * Retrieves the visual fire state of the entity.
         *
         * @return A TriState indicating the current visual fire state.
         */
        @Override
        public @NotNull TriState getVisualFire() {
            return null;
        }

        @Override
        public boolean leaveVehicle() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'leaveVehicle'");
        }

        @Override
        public void lockFreezeTicks(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'lockFreezeTicks'");
        }

        @Override
        public void lookAt(double arg0, double arg1, double arg2, @NotNull LookAnchor arg3) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'lookAt'");
        }

        @Override
        public void playEffect(@NotNull EntityEffect arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'playEffect'");
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

        @Override
        public boolean removePassenger(@NotNull Entity arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removePassenger'");
        }

        @Override
        public boolean removeScoreboardTag(@NotNull String arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeScoreboardTag'");
        }

        @Override
        public void setCustomNameVisible(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setCustomNameVisible'");
        }

        @Override
        public void setFallDistance(float arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setFallDistance'");
        }

        @Override
        public void setFireTicks(int arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setFireTicks'");
        }

        @Override
        public void setFreezeTicks(int arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setFreezeTicks'");
        }

        @Override
        public void setGlowing(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setGlowing'");
        }

        @Override
        public void setGravity(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setGravity'");
        }

        @Override
        public void setInvisible(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setInvisible'");
        }

        @Override
        public void setInvulnerable(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setInvulnerable'");
        }

        @Override
        public void setLastDamageCause(@Nullable EntityDamageEvent arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setLastDamageCause'");
        }

        @Override
        public void setNoPhysics(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setNoPhysics'");
        }

        @Override
        public boolean setPassenger(@NotNull Entity arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setPassenger'");
        }

        @Override
        public void setPersistent(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setPersistent'");
        }

        @Override
        public void setPortalCooldown(int arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setPortalCooldown'");
        }

        @Override
        public void setPose(@NotNull Pose arg0, boolean arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setPose'");
        }

        @Override
        public void setRotation(float arg0, float arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setRotation'");
        }

        @Override
        public void setSilent(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setSilent'");
        }

        @Override
        public void setSneaking(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setSneaking'");
        }

        @Override
        public void setTicksLived(int arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setTicksLived'");
        }

        @Override
        public void setVelocity(@NotNull Vector arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setVelocity'");
        }

        @Override
        public void setVisibleByDefault(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setVisibleByDefault'");
        }

        @Override
        public void setVisualFire(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setVisualFire'");
        }

        /**
         * Sets if the entity has visual fire (it will always appear to be on fire).
         * <ul>
         *     <li>{@link TriState#NOT_SET} – will revert the entity's visual fire to default</li>
         *     <li>{@link TriState#TRUE} – will make the entity appear to be on fire</li>
         *     <li>{@link TriState#FALSE} – will make the entity appear to be not on fire</li>
         * </ul>
         *
         * @param fire a TriState value representing the state of the visual fire.
         */
        @Override
        public void setVisualFire(@NotNull TriState fire) {

        }

        @Override
        public boolean spawnAt(@NotNull Location arg0, @NotNull SpawnReason arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'spawnAt'");
        }

        @Override
        public @NotNull Spigot spigot() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'spigot'");
        }

        @Override
        public @NotNull Component teamDisplayName() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'teamDisplayName'");
        }

        @Override
        public boolean teleport(@NotNull Location arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'teleport'");
        }

        @Override
        public boolean teleport(@NotNull Entity arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'teleport'");
        }

        @Override
        public boolean teleport(@NotNull Location arg0, @NotNull TeleportCause arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'teleport'");
        }

        @Override
        public boolean teleport(@NotNull Entity arg0, @NotNull TeleportCause arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'teleport'");
        }

        @Override
        public boolean teleport(@NotNull Location arg0, @NotNull TeleportCause arg1,
                                @NotNull TeleportFlag @NotNull ... arg2) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'teleport'");
        }

        @Override
        public @NotNull CompletableFuture<Boolean> teleportAsync(@NotNull Location arg0, @NotNull TeleportCause arg1,
                                                                 @NotNull TeleportFlag @NotNull ... arg2) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'teleportAsync'");
        }

        @Override
        public boolean wouldCollideUsing(@NotNull BoundingBox arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'wouldCollideUsing'");
        }

        @Override
        public @NotNull List<MetadataValue> getMetadata(@NotNull String arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getMetadata'");
        }

        @Override
        public boolean hasMetadata(@NotNull String arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasMetadata'");
        }

        @Override
        public void removeMetadata(@NotNull String arg0, @NotNull Plugin arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeMetadata'");
        }

        @Override
        public void setMetadata(@NotNull String arg0, @NotNull MetadataValue arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setMetadata'");
        }

        @Override
        public @NotNull String getName() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getName'");
        }

        @Override
        public @NotNull Component name() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'name'");
        }

        @Override
        public void sendMessage(@NotNull String arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
        }

        @Override
        public void sendMessage(@NotNull String... arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
        }

        @Override
        public void sendMessage(@Nullable UUID arg0, @NotNull String arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
        }

        @Override
        public void sendMessage(@Nullable UUID arg0, @NotNull String... arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
        }

        @Override
        public @NotNull PermissionAttachment addAttachment(@NotNull Plugin arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addAttachment'");
        }

        @Override
        public @Nullable PermissionAttachment addAttachment(@NotNull Plugin arg0, int arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addAttachment'");
        }

        @Override
        public @NotNull PermissionAttachment addAttachment(@NotNull Plugin arg0, @NotNull String arg1, boolean arg2) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addAttachment'");
        }

        @Override
        public @Nullable PermissionAttachment addAttachment(@NotNull Plugin arg0, @NotNull String arg1, boolean arg2,
                                                            int arg3) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addAttachment'");
        }

        @Override
        public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getEffectivePermissions'");
        }

        @Override
        public boolean hasPermission(@NotNull String arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasPermission'");
        }

        @Override
        public boolean hasPermission(@NotNull Permission arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasPermission'");
        }

        @Override
        public boolean isPermissionSet(@NotNull String arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isPermissionSet'");
        }

        @Override
        public boolean isPermissionSet(@NotNull Permission arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isPermissionSet'");
        }

        @Override
        public void recalculatePermissions() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'recalculatePermissions'");
        }

        @Override
        public void removeAttachment(@NotNull PermissionAttachment arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeAttachment'");
        }

        @Override
        public boolean isOp() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isOp'");
        }

        @Override
        public void setOp(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setOp'");
        }

        @Override
        public @Nullable Component customName() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'customName'");
        }

        @Override
        public void customName(@Nullable Component arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'customName'");
        }

        @Override
        public @Nullable String getCustomName() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getCustomName'");
        }

        @Override
        public void setCustomName(@Nullable String arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setCustomName'");
        }

        @Override
        public @NotNull PersistentDataContainer getPersistentDataContainer() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getPersistentDataContainer'");
        }

        @Override
        public boolean canHitEntity(@NotNull Entity arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'canHitEntity'");
        }

        @Override
        public boolean doesBounce() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'doesBounce'");
        }

        @Override
        public @Nullable UUID getOwnerUniqueId() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getOwnerUniqueId'");
        }

        @Override
        public @Nullable ProjectileSource getShooter() {
            return this.player;
        }

        @Override
        public boolean hasBeenShot() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasBeenShot'");
        }

        @Override
        public boolean hasLeftShooter() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasLeftShooter'");
        }

        @Override
        public void hitEntity(@NotNull Entity arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hitEntity'");
        }

        @Override
        public void hitEntity(@NotNull Entity arg0, @NotNull Vector arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hitEntity'");
        }

        @Override
        public void setBounce(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setBounce'");
        }

        @Override
        public void setHasBeenShot(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setHasBeenShot'");
        }

        @Override
        public void setHasLeftShooter(boolean arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setHasLeftShooter'");
        }

        @Override
        public void setShooter(@Nullable ProjectileSource arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setShooter'");
        }

        /**
         * Gets the value for the data component type on this stack.
         *
         * @param type the data component type
         * @return the value for the data component type, or {@code null} if not set or marked as removed
         * @see #hasData(DataComponentType) for DataComponentType.NonValued
         */
        @Override
        public <T> @org.jspecify.annotations.Nullable T getData(DataComponentType.Valued<T> type) {
            return null;
        }

        /**
         * Gets the value for the data component type on this holder with
         * a fallback value.
         *
         * @param type     the data component type
         * @param fallback the fallback value if the value isn't present
         * @return the value for the data component type or the fallback value
         */
        @Override
        public <T> @org.jspecify.annotations.Nullable T getDataOrDefault(DataComponentType.Valued<? extends T> type, @org.jspecify.annotations.Nullable T fallback) {
            return null;
        }

        /**
         * Checks if the data component type is set on this holder.
         *
         * @param type the data component type
         * @return {@code true} if set, {@code false} otherwise
         */
        @Override
        public boolean hasData(DataComponentType type) {
            return false;
        }
    }
}
