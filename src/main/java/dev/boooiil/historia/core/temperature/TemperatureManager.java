package dev.boooiil.historia.core.temperature;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.configuration.specific.TemperatureConfig;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.UUID;

public class TemperatureManager {

    private final TemperatureConfig temperatureConfig;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final UUID uuid;
    private GradualTemperature gradualTemp;
    private static final double DEFAULT_RATE = 0.1;
    private double extremeTemperatureCount = 0;

    // Environmental temperature smoothing
    private double lastEnvironmentTemp = Double.NaN;
    private static final double ENV_TEMP_SMOOTHING = 0.3; // How much new temp influences (30% new, 70% old)

    // Maximum temperature change per step for smooth transitions
    private static final double MAX_TEMP_CHANGE_PER_STEP = 2.0; // Maximum 2°C change per second


    public TemperatureManager(Player player) {
        this.uuid = player.getUniqueId();
        // this.temperatureConfig = ConfigurationLoader.getTemperatureConfig();
        this.temperatureConfig = ConfigurationLoader.getTemperatureConfig();
    }

    public TemperatureManager(UUID uuid) {
        this.uuid = uuid;
        // this.temperatureConfig = ConfigurationLoader.getTemperatureConfig();
        this.temperatureConfig = ConfigurationLoader.getTemperatureConfig();
    }

    public double getTemperature(Player player) {
        // Get external environment temperature in Celsius
        double rawEnvironmentTemp = getExternalTemperature(player);

        // Apply environmental temperature smoothing to prevent wild swings
        double environmentTemp;
        if (Double.isNaN(lastEnvironmentTemp)) {
            // First time - use raw temperature
            environmentTemp = rawEnvironmentTemp;
        } else {
            // Smooth the environmental temperature change
            environmentTemp = lastEnvironmentTemp * (1 - ENV_TEMP_SMOOTHING) + rawEnvironmentTemp * ENV_TEMP_SMOOTHING;
        }
        lastEnvironmentTemp = environmentTemp;

        // Get internal body temperature based on environmental factors
        double internalTemp = getInternalTemperature(player);

        // Create actionbar for incoming temperatures
        player.sendActionBar(Component.text(
                "External: " + df.format(environmentTemp) + "°C | Internal: " + df.format(internalTemp) + "°C"));

        // Format to 2 decimal places
        df.setRoundingMode(RoundingMode.DOWN);

        // Calculate intended temperature
        double intendedTemp = environmentTemp + internalTemp;
        double currentTemp = PlayerStorage.getPlayer(uuid).getCurrentTemperature();
        intendedTemp = Double.valueOf(df.format(intendedTemp));

        // Initialize gradual temperature if null
        if (gradualTemp == null) {
            gradualTemp = new GradualTemperature(
                    intendedTemp,     // max (target temp)
                    currentTemp,       // min (current temp)
                    DEFAULT_RATE
            );
        }
        // Update target temperature if it has changed
        double newTemp;

        if (Math.abs(intendedTemp - gradualTemp.max()) > 0.1) { // Only update for changes > 0.1°C
            // Get the current progress temperature before changing target
            double currentProgressTemp = gradualTemp.getCurrentTemperature();

            // Apply thermal memory for large temperature changes only
            double tempDiff = Math.abs(intendedTemp - gradualTemp.max());
            if (tempDiff > 10.0) { // Increased threshold to prevent cascading
                // Large temperature change - apply thermal memory to smooth the transition
                double memoryFactor = Math.max(0.3, Math.min(0.6, 1.0 / (tempDiff / 20.0))); // Less aggressive
                double smoothedTarget = intendedTemp * (1 - memoryFactor) + gradualTemp.max() * memoryFactor;
                CoreLogger.infoToConsole("Thermal memory: " + String.format("%.1f", intendedTemp) + "°C → " + String.format("%.1f", smoothedTarget) + "°C");
                intendedTemp = smoothedTarget;
            }

            // Create completely new gradual temperature object for clean transition
            gradualTemp = new GradualTemperature(intendedTemp, currentProgressTemp, DEFAULT_RATE);
            newTemp = gradualTemp.doStep(); // Take the first step towards new target
        } else if (intendedTemp == currentTemp) {
            // Reset the gradual temperature if the intended temp is the same as the current temp
            gradualTemp = new GradualTemperature(intendedTemp, currentTemp, DEFAULT_RATE);
            newTemp = gradualTemp.doStep();
        } else {
            // Normal progression
            newTemp = gradualTemp.doStep();
        }

        double finalTemp = Double.valueOf(df.format(newTemp));

        // Apply maximum temperature change per step for smooth transitions
        double storedTemp = PlayerStorage.getPlayer(uuid).getCurrentTemperature();
        double tempChange = finalTemp - storedTemp;

        if (Math.abs(tempChange) > MAX_TEMP_CHANGE_PER_STEP) {
            // Limit the temperature change to maximum allowed per step
            double limitedChange = Math.signum(tempChange) * MAX_TEMP_CHANGE_PER_STEP;
            finalTemp = storedTemp + limitedChange;
            CoreLogger.infoToConsole("Temp change limited: " + String.format("%.1f", tempChange) + "°C → " + String.format("%.1f", limitedChange) + "°C");
        }

        // Debug CoreLogger for temperature progression
        CoreLogger.infoToConsole("Player: " + String.format("%.1f", storedTemp) + "°C → " + String.format("%.1f", finalTemp) + "°C | Target: " + String.format("%.1f", intendedTemp) + "°C");

        return finalTemp;
    }

    private double getInternalTemperature(Player player) {
        double bodyTempModifier = 0.0;

        // Armor insulation/weight effects
        bodyTempModifier += getArmorTemperature(player);

        // Activity-based heat generation
        bodyTempModifier += getActivityTemperature(player);

        // Potion effects
        bodyTempModifier += getPotionTemperature(player);

        return bodyTempModifier;
    }

    private double getExternalTemperature(Player player) {
        Block block = player.getLocation().getBlock();
        World world = player.getWorld();

        // Start with base biome temperature
        double baseTemp = getBiomeBaseTemperature(block);

        // Add heat source temperatures
        double heatSourceTemp = getHeatSourceTemperature(player.getLocation());

        // Apply weather effects
        double weatherTemp = getWeatherTemperature(player, world);
        // player.sendMessage("Base: " + Math.round(baseTemp) + " Heat Source: " + Math.round(heatSourceTemp)
        //         + " Weather: " + Math.round(weatherTemp));

        // Combine temperature sources more realistically
        // Calculate ambient temperature (biome + weather)
        double ambientTemp = baseTemp + weatherTemp;

        // Blend heat sources with ambient temperature
        double finalTemp;
        if (heatSourceTemp > 0.1) {
            // Heat sources should have strong influence, especially when close
            double baseHeatInfluence = Math.min(heatSourceTemp / 25.0, 1.0); // 25°C = full influence
            double heatInfluence = Math.pow(baseHeatInfluence, 0.6); // Power curve
            double minInfluence = Math.max(0.3, Math.min(heatSourceTemp / 15.0, 0.8));
            heatInfluence = Math.max(heatInfluence, minInfluence);

            finalTemp = ambientTemp * (1 - heatInfluence) + heatSourceTemp * heatInfluence;
        } else {
            finalTemp = ambientTemp;
        }

        // // Log which source was used
        // if (Math.abs(finalTemp) == Math.abs(baseTemp)) {
        //     player.sendMessage("Using base biome temperature: " + Math.round(finalTemp));
        // } else if (Math.abs(finalTemp) == Math.abs(heatSourceTemp)) {
        //     player.sendMessage("Using heat source temperature: " + Math.round(finalTemp));
        // } else if (Math.abs(finalTemp) == Math.abs(weatherTemp)) {
        //     player.sendMessage("Using weather temperature: " + Math.round(finalTemp));
        // }
        return finalTemp;
    }

    private double getBiomeBaseTemperature(Block block) {
        World world = block.getWorld();

        // Sample biomes in a small radius for blending
        double totalTemp = 0.0;
        double totalWeight = 0.0;
        int sampleRadius = 2; // Sample 5x5 area around player (less micro-fluctuations)

        for (int x = -sampleRadius; x <= sampleRadius; x++) {
            for (int z = -sampleRadius; z <= sampleRadius; z++) {
                Block sampleBlock = world.getBlockAt(block.getX() + x, block.getY(), block.getZ() + z);
                Biome sampleBiome = sampleBlock.getBiome();

                // Get biome temperature from config
                double sampleBiomeTemp = temperatureConfig.getBiomeValue(sampleBiome);
                if (sampleBiomeTemp == 0.0) {
                    sampleBiomeTemp = temperatureConfig.getRoomTemp();
                }

                // Calculate weight based on distance (closer = more influence)
                double distance = Math.sqrt(x * x + z * z);
                double weight = 1.0 / (1.0 + distance); // Inverse distance weighting

                totalTemp += sampleBiomeTemp * weight;
                totalWeight += weight;
            }
        }

        // Calculate weighted average biome temperature
        double biomeTemp = totalTemp / totalWeight;

        // Underground check (Y level and skylight)
        boolean isUnderground = isUnderground(block);
        if (isUnderground) {
            // Get underground configuration values
            double caveTemp = temperatureConfig.getUndergroundBaseTemp();
            double biomeInfluence = temperatureConfig.getUndergroundBiomeInfluence();

            return caveTemp + ((biomeTemp - caveTemp) * biomeInfluence);
        }

        // Light level modification
        double lightModifier = getLightModifier(block);
        // CoreLogger.infoToPlayer("Light modifier: " + lightModifier, uuid);
        // Time of day affects surface temperature
        double timeModifier = getTimeModifier(world);
        // CoreLogger.infoToPlayer("Time modifier: " + timeModifier, uuid);
        return biomeTemp * lightModifier * timeModifier;
    }

    private boolean isUnderground(Block block) {
        // Check if block has skylight and is below surface

        boolean hasSkylight = block.getLightFromSky() != 0;
        boolean isUnderSeaLevel = block.getY() < block.getWorld().getSeaLevel();
        boolean isUnderWater = block.getWorld().getBlockAt(block.getLocation().add(0, 1, 0)).isLiquid();

        // does not have skylight, or is below sea level and not under water
        // not sure if this is the best way to approach this calc
        // players could be in homes that are below the sea level
        // TODO: needs more testing
        return !hasSkylight || (isUnderSeaLevel && !isUnderWater);

    }

    private double getLightModifier(Block block) {
        double skyLight = block.getLightFromSky();

        // Get light modifier values from config
        double minModifier = temperatureConfig.getLightMinModifier();
        double maxModifier = temperatureConfig.getLightMaxModifier();

        double range = maxModifier - minModifier;

        if (skyLight == 0) {
            return minModifier;
        }

        return minModifier + ((skyLight / 15.0) * range);
    }

    private double getTimeModifier(World world) {
        long time = world.getTime();
        // Get time modifier values from config
        double nightMod = temperatureConfig.getTimeNightModifier();
        double noonMod = temperatureConfig.getTimeNoonModifier();
        double range = noonMod - nightMod;

        if (time < 6000) {
            // Morning warming
            return nightMod + (range * (time / 6000.0));
        } else if (time < 18000) {
            // Afternoon cooling
            return noonMod - (range * ((time - 6000) / 12000.0));
        } else {
            // Night
            return nightMod;
        }
    }

    private double getHeatSourceTemperature(Location location) {
        HeatSourceCalculator calculator = new HeatSourceCalculator(temperatureConfig);
        Player player = Bukkit.getPlayer(uuid);
        if (player == null)
            return 0.0;

        // Use the calculator's getHeatSourceEffect method
        // CoreLogger.infoToConsole("Heat Source Temperature: " +
        // calculator.getHeatSourceEffect(player));
        return calculator.getHeatSourceEffect(player);
    }

    private double getWeatherTemperature(Player player, World world) {
        if (!world.hasStorm())
            return 0.0;

        // Get weather temperature from config
        double rainTemp = temperatureConfig.getWeatherValue(WeatherType.DOWNFALL);

        // If player is exposed to sky
        if (isExposedToSky(player.getLocation().getBlock())) {
            return rainTemp;
        }

        return 0.0;
    }

    private boolean isExposedToSky(Block block) {
        if (block.getType().isSolid()) {
            return false;
        }
        // Check the blocks above the player to determine if they are exposed to the sky
        for (int y = block.getY() + 1; y <= 256; y++) { // Adjust the upper limit based on world height
            Block aboveBlock = block.getWorld().getBlockAt(block.getX(), y, block.getZ());

            if (aboveBlock.getType().isAir()) {
                continue; // Air blocks don't prevent exposure to the sky
            }

            if (aboveBlock.getBlockData() instanceof Waterlogged) {
                continue; // Waterlogged blocks don't prevent exposure to the sky
            }

            if (aboveBlock.getBlockData() instanceof TrapDoor) {
                continue; // Trapdoors don't prevent exposure to the sky
            }
            // CoreLogger.infoToPlayer("Block above: " + aboveBlock.getType(), uuid);
            return false; // Found a non-transparent block, not exposed to the sky
        }
        // CoreLogger.infoToPlayer("Clear", uuid);
        return true; // No solid blocks above, exposed to the sky
    }

    // This is a temporary holder until PlayerStorage weight is implemented
    private double getArmorTemperature(Player player) {
        if (PlayerStorage.getPlayer(player.getUniqueId()) == null)
            return 0.0;

        double temp = 0.0;
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item == null)
                continue;

            String itemType = item.getType().toString();
            if (itemType.contains("HELMET") ||
                    itemType.contains("CHESTPLATE") ||
                    itemType.contains("LEGGINGS") ||
                    itemType.contains("BOOTS")) {
                temp += 0.5;
            }
        }
        // // Get player's armor weight from storage
        // double armorWeight =
        // PlayerStorage.getPlayer(player.getUniqueId()).getArmorWeight();

        // // Add temperature modifier based on armor weight tiers
        // temp += switch ((int)armorWeight) {
        // case 20 -> 1.0;
        // case 30 -> 2.0;
        // case 40 -> 3.0;
        // case 50 -> 4.0;
        // default -> 0.0;
        // };
        // CoreLogger.infoToConsole("Armor temperature modifier: " + temp);
        return temp;
    }

    private double getActivityTemperature(Player player) {
        double temp = 0.0;

        // Physical activity increases body temperature
        if (player.isSprinting())
            temp += 2.0;
        if (player.isSwimming())
            temp += 1.5;

        if (player.isBlocking())
            temp += 1.0;
        if (player.isInWater())
            temp += ConfigurationLoader.getTemperatureConfig().getWaterTemperature();
        return temp;
    }

    private double getPotionTemperature(Player player) {
        double temp = 0.0;

        for (PotionEffect effect : player.getActivePotionEffects()) {
            temp += temperatureConfig.getPotionValue(effect.getType().toString());
        }

        return temp;
    }

    public void updateTemperature() {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            //CoreLogger.errorToConsole("Player is null");
            return;
        }
        TemperatureConfig temperatureConfig = ConfigurationLoader.getTemperatureConfig();
        double temperature = getTemperature(player);
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(uuid);
        df.setRoundingMode(RoundingMode.DOWN);
        historiaPlayer.getTemperature().setMin(Double.valueOf(df.format(temperature)));

        applyDebuff(player, temperature, temperatureConfig.getMinimum(), temperatureConfig.getMaximum());

    }

    public void applyDebuff(Player player, double temperature, double min, double max) {
        // CoreLogger.infoToConsole("Temperature: " + temperature + " " + min + " " + max);
        if (temperature > max) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 10, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 20 * 10, 1));
            CoreLogger.warnToPlayer("You are experiencing fatigue.", uuid);
        }
        // if they are cold
        if (temperature < min) {
            extremeTemperatureCount--;

            // else if they are hot
        } else if (temperature > max) {
            extremeTemperatureCount++;
            // else if they are in normal range
        } else {

            // if they were previously cold
            if (extremeTemperatureCount < 0)
                extremeTemperatureCount++;

                // if they were previously hot
            else if (extremeTemperatureCount > 0)
                extremeTemperatureCount--;
        }

        if (extremeTemperatureCount >= 10) {
            // HOT
            CoreLogger.warnToPlayer("You are extremely hot!", uuid);
            player.damage(extremeTemperatureCount * 0.1);
            Location playerLoc = player.getLocation(); // Player's feet location
            Location abovePlayerLoc = player.getLocation().add(0, 4, 0); // 3 blocks above player's feet

            // Calculate distance and direction between player and the location above
            double distance = playerLoc.distance(abovePlayerLoc);
            Vector direction = abovePlayerLoc.subtract(playerLoc).toVector().normalize();

            // Spawn particles along the line
            for (double i = 0; i < distance; i += 0.1) {
                Location particleLoc = playerLoc.clone().add(direction.clone().multiply(i));
                player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleLoc, 1);
            }
        } else if (extremeTemperatureCount <= -10) {
            // COLD
            CoreLogger.warnToPlayer("You are freezing!", uuid);
            player.setFreezeTicks(40);
        }

    }

}