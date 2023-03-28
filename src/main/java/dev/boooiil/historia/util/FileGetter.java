package dev.boooiil.historia.util;

import dev.boooiil.historia.Main;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


/**
 * It checks if a file exists in the plugin's data folder, and if it does, it loads it from there. If
 * it doesn't, it loads it from the jar.
 */
public class FileGetter {

    /**
     * It takes an array of files and a string, and returns true if the string is the name of one of
     * the files in the array
     * 
     * @param files The array of files to check
     * @param check The name of the file you want to check for.
     * @return A boolean value.
     */
    public static boolean find(File[] files, String check) {

        for (File file : files) {

            if (file.getName().equals(check)) return true;

        }

        return false;

    }

    /**
     * If the file exists in the external directory, load it from there. If it doesn't, load it from
     * the internal directory
     * 
     * @param check The file name to check for.
     * @return A FileConfiguration object.
     */
    public static FileConfiguration get(String check) {

        FileConfiguration config;

        if (find(Main.plugin().getDataFolder().listFiles(), check)) {

            Logging.infoToConsole("Obtained file from external directory: " + check);

            File file = new File(Main.plugin().getDataFolder().getPath(), check);

            config = YamlConfiguration.loadConfiguration(file);
        }

        else {

            Logging.errorToConsole("Obtained file from internal directory: " + check);

            InputStream is = Main.plugin().getClass().getClassLoader().getResourceAsStream(check);

            Reader reader = new InputStreamReader(is);

            config = YamlConfiguration.loadConfiguration(reader);

        }

        return config;

    }
    
}
