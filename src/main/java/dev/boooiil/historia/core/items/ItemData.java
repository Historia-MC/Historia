package dev.boooiil.historia.core.items;

import dev.boooiil.historia.core.util.JSONSerializable;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ItemData extends JSONSerializable {
    void apply(ItemStack stack);
}