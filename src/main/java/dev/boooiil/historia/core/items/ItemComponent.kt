package dev.boooiil.historia.core.items;

import dev.boooiil.historia.core.util.JSONSerializable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ItemComponent extends JSONSerializable {
    ItemData data();

    ItemData data(float qualityModifier);

    String getKey();
}