package dev.boooiil.historia.core.items.component;

import dev.boooiil.historia.core.items.ItemComponent;
import dev.boooiil.historia.core.items.data.ToolData;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public record ToolComponent(List<Float> damageRange, List<Float> speedRange, List<Float> knockbackRange,
                            List<Integer> durabilityRange) implements ItemComponent {

    public static ToolComponent fromConfig(ConfigurationSection section) {
        return new ToolComponent(
                section.getFloatList("damage"),
                section.getFloatList("speed"),
                section.getFloatList("knockback"),
                section.getIntegerList("durability"));
    }

    @Override
    public ToolData data() {

        float damage = NumberUtils
                .roundFloat(NumberUtils.random(this.damageRange().get(0), this.damageRange().get(1)), 2);
        float speed = NumberUtils
                .roundFloat(NumberUtils.random(this.speedRange().get(0), this.speedRange().get(1)), 2);
        float knockback = NumberUtils
                .roundFloat(NumberUtils.random(this.knockbackRange().get(0), this.knockbackRange().get(1)), 2);
        int durability = NumberUtils.randomInt(this.durabilityRange().get(0), this.durabilityRange().get(1));

        return new ToolData(damage, speed, knockback, durability);
    }

    @Override
    public ToolData data(float qualityModifier) {
        return data();
    }

    @Override
    public String getKey() {
        return "tool";
    }

    @Override
    public String toString() {

        String sb = "ToolComponent" +
                toJSON();

        return sb;
    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromList("damageRange", damageRange) + ", " +
                JSONUtils.fromList("speedRange", speedRange) + ", " +
                JSONUtils.fromList("knockbackRange", knockbackRange) + ", " +
                JSONUtils.fromList("durabilityRange", durabilityRange) +
                "}";

        return sb;
    }

}
