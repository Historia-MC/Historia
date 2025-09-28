package dev.boooiil.historia.core.configuration.specific;

import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.file.FileKeys;
import dev.boooiil.historia.core.util.CoreLogger;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class TemperatureConfig {

    /**
     * ALL TEMPERATURES ARE GOING TO BE IN C
     */

    public static final double INITIAL_CONSTANT_TEMP = 37d;

    /**
     * Enum for the different modifiers that can be applied to the temperature.
     */
    public enum Modifier {
        BIOME,
        LIGHT,
        WATER,
        WEATHER,
        HEAT
    }

    public enum Time {
        NOON,
        MIDNIGHT,
        DAWN,
        NIGHT
    }

    public enum StatusEffect {
        ON_FIRE
    }

    private final Map<StatusEffect, Double> statusEffects = new EnumMap<>(StatusEffect.class);
    private final Map<Modifier, Double> modifiers = new EnumMap<>(Modifier.class);
    private final Map<Biome, Double> biome = new HashMap<>();
    private final Map<Material, Double> heatSource = new EnumMap<>(Material.class);
    private final Map<WeatherType, Double> weather = new EnumMap<>(WeatherType.class);
    private final Map<PotionType, Double> potions = new EnumMap<>(PotionType.class);
    private final Map<Integer, Double> time = new HashMap<>();
    private final Map<String, Map<Integer, Double>> armor = new HashMap<>();
    private final double searchDistance;
    private final double diminishDistance;
    private double minimum;
    private double maximum;
    private final double roomTemp;
    private final double undergroundBaseTemp;
    private final double undergroundBiomeInfluence;
    private final double lightMinModifier;
    private final double lightMaxModifier;
    private final double timeNightModifier;
    private final double timeNoonModifier;
    private final double waterTemperature;

    public TemperatureConfig() {

        FileConfiguration configuration = FileIO.get(FileKeys.TEMPERATURE);
        searchDistance = configuration.getDouble("search_distance");
        diminishDistance = configuration.getDouble("diminish_distance");
        minimum = configuration.getDouble("minimum");
        maximum = configuration.getDouble("maximum");
        minimum = configuration.getDouble("minimum");
        maximum = configuration.getDouble("maximum");
        roomTemp = configuration.getDouble("room_temp");
        waterTemperature = configuration.getDouble("water");
        // Underground settings
        undergroundBaseTemp = configuration.getDouble("underground.base_temp");
        undergroundBiomeInfluence = configuration.getDouble("underground.biome_influence");

        // // Light settings
        lightMinModifier = configuration.getDouble("light.min_modifier");
        lightMaxModifier = configuration.getDouble("light.max_modifier");

        // // Time settings
        timeNightModifier = configuration.getDouble("time.night_modifier");
        CoreLogger.verboseToConsole("Time night modifier: " + timeNightModifier);
        timeNoonModifier = configuration.getDouble("time.noon_modifier");
        CoreLogger.verboseToConsole("Time noon modifier: " + timeNoonModifier);
        // Load time modifiers
        double nightModifier = configuration.getDouble("time.night_modifier");
        CoreLogger.verboseToConsole("Time night modifier: " + nightModifier);
        double noonModifier = configuration.getDouble("time.noon_modifier");
        CoreLogger.verboseToConsole("Time noon modifier: " + noonModifier);
        for (String statusEffect : configuration.getConfigurationSection("status_effects").getKeys(false)) {

            StatusEffect mod = StatusEffect.valueOf(statusEffect.toUpperCase());
            Double value = configuration.getDouble("status_effects." + statusEffect);

            statusEffects.put(mod, value);

            CoreLogger.verboseToConsole(
                    "Adding playerstatus ",
                    statusEffect,
                    " as playerstatus ",
                    mod.toString(),
                    " with value ",
                    value.toString());

        }

        ConfigurationSection armorSection = configuration.getConfigurationSection("armor");
        if (armorSection != null) {
            for (String key : armorSection.getKeys(false)) {
                ConfigurationSection tierSection = armorSection.getConfigurationSection(key);
                if (tierSection != null) {
                    int weight = tierSection.getInt("WEIGHT");
                    double temp = tierSection.getDouble("TEMP");
                    armor.put(key, Map.of(weight, temp));
                }
            }
        }

        for (String modifier : configuration.getConfigurationSection("modifier").getKeys(false)) {

            Modifier mod = Modifier.valueOf(modifier.toUpperCase());

            modifiers.put(mod, configuration.getDouble("modifier." + modifier));

            CoreLogger.verboseToConsole(
                    "Adding modifier ",
                    modifier,
                    " as modifier ",
                    mod.toString(),
                    " with value ",
                    configuration.getDouble("modifier." + modifier) + "");

        }

        for (String biome : configuration.getConfigurationSection("biome").getKeys(false)) {
            CoreLogger.verboseToConsole("Biome: " + biome);
            Biome b = RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).get(Key.key(biome.toLowerCase()));

            this.biome.put(b, configuration.getDouble("biome." + biome));

            CoreLogger.verboseToConsole(
                    "Adding biome ",
                    biome,
                    " as modifier ",
                    b.toString(),
                    " with value ",
                    configuration.getDouble("biome." + biome) + "");

        }

        for (String heatSource : configuration.getConfigurationSection("heat_sources").getKeys(false)) {

            Material m = Material.valueOf(heatSource.toUpperCase());

            this.heatSource.put(m, configuration.getDouble("heat_sources." + heatSource));

            CoreLogger.verboseToConsole(
                    "Adding material ",
                    heatSource,
                    " as modifier ",
                    m.toString(),
                    " with value ",
                    configuration.getDouble("heat-source." + heatSource) + "");

        }

        for (String weather : configuration.getConfigurationSection("weather").getKeys(false)) {

            WeatherType w = WeatherType.valueOf(weather.toUpperCase());

            this.weather.put(w, configuration.getDouble("weather." + weather));

            CoreLogger.verboseToConsole(
                    "Adding weather ",
                    weather,
                    " as modifier ",
                    w.toString(),
                    " with value ",
                    configuration.getDouble("weather." + weather) + "");

        }

        for (String potion : configuration.getConfigurationSection("potions").getKeys(false)) {

            //PotionEffectType m = PotionEffectType.getByName(heatSource);
            PotionType p = RegistryAccess.registryAccess().getRegistry(RegistryKey.POTION).get(Key.key(potion.toLowerCase()));
            this.potions.put(p, configuration.getDouble("potions." + potion.toLowerCase()));

            CoreLogger.verboseToConsole(
                    "Adding material ",
                    potion,
                    " as modifier ",
                    p.toString(),
                    " with value ",
                    configuration.getDouble("potions." + potion) + "");
        }

    }

    public double getArmorTemperature(int weight) {
        for (Map.Entry<String, Map<Integer, Double>> entry : armor.entrySet()) {
            Map<Integer, Double> weightTempMap = entry.getValue();
            for (Map.Entry<Integer, Double> weightTempEntry : weightTempMap.entrySet()) {
                if (weight <= weightTempEntry.getKey()) {
                    return weightTempEntry.getValue();
                }
            }
        }
        return 0;
    }

    public Map<Modifier, Double> getModifiers() {
        return modifiers;
    }

    public Double getModifierValue(Modifier modifier) {
        return modifiers.get(modifier);
    }

    public Map<Biome, Double> getBiome() {
        return biome;
    }

    public Double getBiomeValue(Biome biome) {
        return this.biome.get(biome);
    }

    public Map<Material, Double> getHeatSource() {
        return heatSource;
    }

    public Double getHeatSourceValue(Material material) {
        return heatSource.get(material);
    }

    public Map<WeatherType, Double> getWeather() {
        return weather;
    }

    public Double getWeatherValue(WeatherType weather) {
        return this.weather.get(weather);
    }

    public Map<Integer, Double> getTime() {
        return time;
    }

    public Double getTimeValue(Integer time) {
        return this.time.get(time);
    }

    public double getSearchDistance() {
        return searchDistance;
    }

    public double getDiminishDistance() {
        return diminishDistance;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getRoomTemp() {
        return roomTemp;
    }

    public double getUndergroundBaseTemp() {
        return undergroundBaseTemp;
    }

    public double getUndergroundBiomeInfluence() {
        return undergroundBiomeInfluence;
    }

    public double getLightMinModifier() {
        return lightMinModifier;
    }

    public double getLightMaxModifier() {
        return lightMaxModifier;
    }

    public double getTimeNightModifier() {
        return timeNightModifier;
    }

    public double getTimeNoonModifier() {
        return timeNoonModifier;
    }

    public double getPotionValue(String effectName) {
        for (Map.Entry<PotionType, Double> entry : potions.entrySet()) {
            if (effectName.toUpperCase().contains(entry.getKey().toString())) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public double getWaterTemperature() {
        return waterTemperature;
    }

}
