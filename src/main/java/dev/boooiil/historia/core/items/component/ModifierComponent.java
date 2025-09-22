package dev.boooiil.historia.core.items.component;

import dev.boooiil.historia.core.items.ItemComponent;
import dev.boooiil.historia.core.items.data.ModifierData;
import dev.boooiil.historia.core.items.types.Qualities;
import dev.boooiil.historia.core.items.types.Weights;
import dev.boooiil.historia.core.util.JSONUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record ModifierComponent(Weights weight, Qualities quality) implements ItemComponent {

    public static ModifierComponent fromConfig(ConfigurationSection section) {

        Weights weight = Weights.fromString(section.getString("weight"));
        Qualities quality = Qualities.fromString(section.getString("quality"));

        return new ModifierComponent(
                weight,
                quality);
    }

    @Override
    public ModifierData data() {
        return new ModifierData(weight, quality);
    }

    @Override
    public ModifierData data(float qualityModifier) {
        return data();
    }

    @Override
    public String getKey() {
        return "modifier";
    }

    @Override
    public String toString() {

        String sb = "ModifierComponent" +
                toJSON();

        return sb;
    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromValue("weight", weight.lowercase()) +
                JSONUtils.fromValue("quality", quality.lowercase()) +
                "}";

        return sb;
    }

}
