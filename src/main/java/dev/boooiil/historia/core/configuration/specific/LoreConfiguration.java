package dev.boooiil.historia.core.configuration.specific;

import dev.boooiil.historia.core.file.FileIO;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;

public final class LoreConfiguration {

    private static final YamlConfiguration loreConfiguration;

    private static final HashMap<String, HashMap<String, List<String>>> loreMap = new HashMap<>();

    static {
        loreConfiguration = FileIO.findYamlConfiguration("component-lore.yml");
    }

    @Generated(value = "Static Utility")
    private LoreConfiguration() {
    }

    public static void initLoreMap() {
        for (String key : loreConfiguration.getKeys(false)) {
            HashMap<String, List<String>> innerMap = new HashMap<>();

            innerMap.put("head", loreConfiguration.getStringList(key + ".head"));
            innerMap.put("attribute", loreConfiguration.getStringList(key + ".attribute"));

            loreMap.put(key, innerMap);

        }
    }

    public static HashMap<String, List<String>> get(String key) {
        if (loreMap.containsKey(key)) {
            return loreMap.get(key);

        }
        return null;
    }

    public static boolean contains(String key) {
        return loreMap.containsKey(key);
    }

}
