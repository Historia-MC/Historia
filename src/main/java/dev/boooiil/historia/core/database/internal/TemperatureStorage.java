package dev.boooiil.historia.core.database.internal;


import dev.boooiil.historia.core.temperature.TemperatureManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TemperatureStorage {

    private static final Map<UUID, TemperatureManager> temperatureManagers = new HashMap<>();

    public static TemperatureManager getTemperatureManager(UUID uuid) {
        if (!temperatureManagers.containsKey(uuid)) {
            //Logging.infoToConsole("Creating new temperature manager for " + uuid);
            temperatureManagers.put(uuid, new TemperatureManager(uuid));
        }
        return temperatureManagers.get(uuid);
    }

    public static void setTemperatureManager(UUID uuid, TemperatureManager temperatureManager) {

        if (!temperatureManagers.containsKey(uuid)) {
            temperatureManagers.put(uuid, temperatureManager);
        }

    }

    public static void removeTemperatureManager(UUID uuid) {
        temperatureManagers.remove(uuid);
    }

    // Create a function to get the list of all temperature managers.
    public static Map<UUID, TemperatureManager> getTemperatureManagers() {
        return temperatureManagers;
    }
}
