package dev.boooiil.historia.core.items.component;

import dev.boooiil.historia.core.items.ItemComponent;
import dev.boooiil.historia.core.items.data.ArmorData;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public record ArmorComponent(List<Float> defenseRange, List<Integer> durabilityRange) implements ItemComponent {

    public static ArmorComponent fromConfig(ConfigurationSection section) {
        return new ArmorComponent(
                section.getFloatList("defense"),
                section.getIntegerList("durability"));
    }

    @Override
    public ArmorData data() {

        float defense = NumberUtils
                .roundFloat(NumberUtils.random(this.defenseRange().get(0), this.defenseRange().get(1)), 2);
        int durability = NumberUtils.randomInt(this.durabilityRange().get(0), this.durabilityRange().get(1));

        return new ArmorData(defense, durability);
    }

    @Override
    public ArmorData data(float qualityModifier) {
        return data();
    }

    @Override
    public String getKey() {
        return "armor";
    }

    @Override
    public String toString() {

        String sb = "ArmorComponent" +
                toJSON();

        return sb;
    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromList("defenseRange", defenseRange) + ", " +
                JSONUtils.fromList("durabilityRange", durabilityRange) +
                "}";

        return sb;
    }
}
