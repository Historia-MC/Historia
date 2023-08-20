package dev.boooiil.historia.core.util;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.classes.enums.file.FileKeys;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


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
    public static boolean find(File[] files, FileKeys check) {

        for (File file : files) {

            if (file.getName().equals(check.getKey())) return true;

        }

        return false;

    }

    /**
     * If the file exists in the external directory, load it from there. If it doesn't, load it from
     * the internal directory
     * 
     * @param check The file name to check for.
     * @return A YamlConfiguration object.
     */
    public static YamlConfiguration get(FileKeys check) {

        YamlConfiguration config;

        if (find(Main.plugin().getDataFolder().listFiles(), check)) {

            Logging.debugToConsole("Obtained file from external directory: ", Main.plugin().getDataFolder().getPath() + "\\" + check.getKey());

            File file = new File(Main.plugin().getDataFolder().getPath(), check.getKey());

            config = YamlConfiguration.loadConfiguration(file);
        }

        else {

            Logging.debugToConsole("Obtained file from internal directory: " + check.getKey());

            InputStream is = FileGetter.class.getClassLoader().getResourceAsStream(check.getKey());

            Reader reader = new InputStreamReader(is);

            config = YamlConfiguration.loadConfiguration(reader);

        }
        
        return config;

    }
    
}
