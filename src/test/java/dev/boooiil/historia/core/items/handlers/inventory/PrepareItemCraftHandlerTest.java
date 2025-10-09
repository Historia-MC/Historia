package dev.boooiil.historia.core.items.handlers.inventory;

import dev.boooiil.historia.core.BaseTest;
import dev.boooiil.historia.core.items.HistoriaItem;
import dev.boooiil.historia.core.items.component.ExecutorComponent;
import dev.boooiil.historia.core.items.data.ArmorData;
import dev.boooiil.historia.core.items.data.ExecutorData;
import dev.boooiil.historia.core.items.data.ToolData;
import dev.boooiil.historia.core.items.data.WeaponData;
import dev.boooiil.historia.core.items.executor.ItemExecutable;
import dev.boooiil.historia.core.items.types.Triggers;
import dev.boooiil.historia.core.registry.RegistryHolder;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.*;

public class PrepareItemCraftHandlerTest extends BaseTest {

    @Test
    public void validateItems() {
        for (HistoriaItem historiaItem : RegistryHolder.ITEM_REGISTRY.values()) {

            CoreLogger.debugToConsole("item:", historiaItem.toString());

            ItemStack item = historiaItem.createItemStack(); // logs "setting into container..."
            CoreLogger.debugToConsole(item.getItemMeta().getPersistentDataContainer().getKeys() + "");

            assertTrue(item.hasItemMeta());

            ItemMeta meta = item.getItemMeta();

            CoreLogger.debugToConsole(historiaItem.getConfigurationId().getKey(), "components:",
                    historiaItem.getComponentHolder().toString());

            for (NamespacedKey key : historiaItem.getComponentHolder().keySet()) {

                switch (key.getKey()) {
                    case "tool":
                        ToolData td = ToolData.fromStack(item);

                        AttributeModifier damageAttr = meta.getAttributeModifiers(Attribute.ATTACK_DAMAGE).iterator()
                                .next();

                        AttributeModifier speedAttr = meta.getAttributeModifiers(Attribute.ATTACK_SPEED).iterator()
                                .next();

                        AttributeModifier knockbackAttr = meta.getAttributeModifiers(Attribute.ATTACK_KNOCKBACK)
                                .iterator().next();

                        Damageable toolDamageable = (Damageable) item.getItemMeta();
                        float damage = NumberUtils.roundFloat((float) damageAttr.getAmount(), 2);
                        float speed = NumberUtils.roundFloat((float) speedAttr.getAmount(), 2);
                        float knockback = NumberUtils.roundFloat((float) knockbackAttr.getAmount(), 2);

                        CoreLogger.debugToConsole("Data:", td.toString());

                        assertEquals(td.damage(), damage);
                        assertEquals(td.speed(), speed);
                        assertEquals(td.knockback(), knockback);
                        assertEquals(td.maxDurability(), toolDamageable.getMaxDamage());
                        break;

                    case "weapon":
                        WeaponData wd = WeaponData.fromStack(item);

                        AttributeModifier sweepingAttr = meta.getAttributeModifiers(Attribute.SWEEPING_DAMAGE_RATIO)
                                .iterator()
                                .next();

                        float sweeping = NumberUtils.roundFloat((float) sweepingAttr.getAmount(), 2);

                        CoreLogger.debugToConsole("Data:", wd.toString());
                        assertEquals(wd.sweeping(), sweeping);

                        break;

                    case "armor":
                        ArmorData ad = ArmorData.fromStack(item);

                        AttributeModifier defenseAttr = meta.getAttributeModifiers(Attribute.ARMOR).iterator()
                                .next();

                        Damageable armorDamageable = (Damageable) item.getItemMeta();
                        float defense = NumberUtils.roundFloat((float) defenseAttr.getAmount(), 2);

                        CoreLogger.debugToConsole("Data:", ad.toString());

                        assertEquals(ad.defense(), defense);
                        assertEquals(ad.maxDurability(), armorDamageable.getMaxDamage());
                        break;

                    case "executor":
                        ExecutorComponent ec = (ExecutorComponent) historiaItem.getComponentHolder().get(key);
                        ExecutorData ed = ExecutorData.fromStack(item);
                        PlayerMock player = server.addPlayer();

                        for (Triggers trigger : ec.getExecutables().keySet()) {

                            ItemExecutable executable = ec.getExecutables().get(trigger);

                            CoreLogger.debugToConsole("Executable:", executable.toString());

                            assertNotSame(trigger, Triggers.UNKNOWN);
                            assertTrue(ec.getExecutables().containsKey(trigger));

                            assertEquals(ed.executables().get(trigger).uses(), executable.uses());

                            assertEquals(ed.executables().get(trigger).cooldown(), executable.cooldown());

                            assertEquals(ed.executables().get(trigger).commands(), executable.commands());

                            ed.execute(player, 0, item, trigger);

                            if (ec.getExecutables().size() > ed.executables().size()) {
                                CoreLogger.debugToConsole("Executables size changed: " + ec.getExecutables().size() + " -> "
                                        + ed.executables().size(), "on trigger", trigger.getLowercase());
                            } else {
                                for (Triggers executedTrigger : ec.getExecutables().keySet()) {
                                    assertTrue(ed.executables().get(executedTrigger).uses() < ec.getExecutables().get(trigger)
                                            .uses());
                                }
                            }

                        }

                        break;

                }
            }
        }
    }

}
