package dev.boooiil.historia.util;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import dev.boooiil.historia.Main;

public class ConfigUtil {
    
    private static List<String> configFileNames = new ArrayList<>();

    static {
        configFileNames.add("armor.yml");
        configFileNames.add("classes.yml");
        configFileNames.add("expiry.yml");
        configFileNames.add("ingots.yml");
        configFileNames.add("ores.yml");
        configFileNames.add("weapons.yml");
        
    }

    public static void checkFiles() {

        Logging.infoToConsole("Checking existance and version of config files.");

        for (String fileName : configFileNames) {
            File diskFile = new File(Main.plugin().getDataFolder(), fileName);

            if (!diskFile.exists()) {
                Logging.infoToConsole("Missing config file: " + fileName + " has been saved to disk from resources.");
                Main.plugin().saveResource(fileName, false);
                continue;
            }

            YamlConfiguration diskConfig = yamlFromSource(diskFile);
            YamlConfiguration jarConfig = yamlFromSource(Main.plugin().getResource(fileName));

            int diskVersion = diskConfig.getInt("version");
            int jarVersion = jarConfig.getInt("version");

            if (diskVersion < jarVersion) { 
                Logging.infoToConsole("Outdated config file (" + diskVersion + "): " + fileName + " has been replaced on disk by the newer version " + jarVersion + ".");
                Main.plugin().saveResource(fileName, true);
                continue;
            }
        }

        Logging.infoToConsole("Completed checks of existance and version of config files.");

    }

    private static YamlConfiguration yamlFromSource(InputStream is) {
        Reader reader = new InputStreamReader(is);
        return YamlConfiguration.loadConfiguration(reader);
    }

    private static YamlConfiguration yamlFromSource(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

}
