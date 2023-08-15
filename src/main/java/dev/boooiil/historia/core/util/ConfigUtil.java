package dev.boooiil.historia.core.util;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.classes.enums.FileMap.ResourceKeys;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtil {
    
    private static List<String> configFileNames = new ArrayList<>();

    static {

        for (ResourceKeys key : ResourceKeys.values()) {
            configFileNames.add(key.getKey());
        }
       
    }

    public static void checkFiles() {

        Logging.infoToConsole("Checking existance and version of config files.");

        for (String fileName : configFileNames) {
            File diskFile = new File(Main.plugin().getDataFolder(), fileName);

            if (!diskFile.exists()) {
                Logging.infoToConsole("Missing config file: " + fileName + " has been saved to disk from resources.");
                Logging.infoToConsole("Location: " + diskFile.getAbsolutePath());
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

    public static YamlConfiguration yamlFromSource(InputStream is) {
        Reader reader = new InputStreamReader(is, Charset.defaultCharset());
        return YamlConfiguration.loadConfiguration(reader);
    }

    public static YamlConfiguration yamlFromSource(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

}
