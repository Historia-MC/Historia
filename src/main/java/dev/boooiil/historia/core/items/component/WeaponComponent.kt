package dev.boooiil.historia.core.items.component;

import dev.boooiil.historia.core.items.ItemComponent;
import dev.boooiil.historia.core.items.data.WeaponData;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public record WeaponComponent(List<Float> sweepingRange) implements ItemComponent {

    public static WeaponComponent fromConfig(ConfigurationSection section) {
        return new WeaponComponent(section.getFloatList("sweeping"));
    }

    @Override
    public WeaponData data() {
        float sweeping = NumberUtils
                .roundFloat(NumberUtils.random(this.sweepingRange().get(0), this.sweepingRange().get(1)), 2);

        return new WeaponData(sweeping);
    }

    @Override
    public WeaponData data(float qualityModifier) {
        return data();
    }

    @Override
    public String getKey() {
        return "weapon";
    }

    @Override
    public String toString() {

        String sb = "WeaponComponent" +
                toJSON();

        return sb;
    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromList("sweepRange", sweepingRange) +
                "}";

        return sb;
    }

}
