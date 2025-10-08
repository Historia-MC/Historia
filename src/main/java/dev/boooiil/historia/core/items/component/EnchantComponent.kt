package dev.boooiil.historia.core.items.component;

import dev.boooiil.historia.core.items.ItemComponent;
import dev.boooiil.historia.core.items.data.EnchantData;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map.Entry;

public record EnchantComponent(HashMap<Enchantment, Integer> enchantments) implements ItemComponent {

    public static EnchantComponent fromConfig(ConfigurationSection section) {

        HashMap<Enchantment, Integer> map = new HashMap<>();

        for (String enchant : section.getKeys(false)) {
            Enchantment enchantment = Enchantment.getByName(enchant);

            if (enchantment == null) {
                CoreLogger.errorToConsole("Tried to get enchantment",
                        enchant, "from enchantment component but it does not exist.");
                continue;

            }

            map.put(enchantment, section.getInt(enchant));

        }

        return new EnchantComponent(map);

    }

    @Override
    public EnchantData data() {
        return new EnchantData(this.enchantments);
    }

    @Override
    public EnchantData data(float qualityModifier) {
        return data();
    }

    @Override
    public String getKey() {
        return "enchant";
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("EnchantComponent");
        sb.append("{");
        sb.append("\"enchantments\":");
        sb.append("{");

        for (Entry<Enchantment, Integer> enchants : enchantments.entrySet()) {

            sb.append(JSONUtils.fromValue(enchants.getKey().getKey().getKey(), enchants.getValue()));
            sb.append(", ");

        }
        sb.setLength(sb.length() - 2);
        sb.append("}");
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("\"enchantments\":");
        sb.append("{");

        for (Entry<Enchantment, Integer> enchants : enchantments.entrySet()) {

            sb.append(JSONUtils.fromValue(enchants.getKey().getKey().getKey(), enchants.getValue()));
            sb.append(", ");

        }
        sb.setLength(sb.length() - 2);
        sb.append("}");
        sb.append("}");

        return sb.toString();

    }

}
