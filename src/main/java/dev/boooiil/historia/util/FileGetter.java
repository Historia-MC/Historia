package dev.boooiil.historia.util;

import dev.boooiil.historia.Main;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class FileGetter {

    public static boolean find(File[] files, String check) {

        for (File file : files) {

            if (file.getName().equals(check)) return true;

        }

        return false;

    }

    public static FileConfiguration get(String check) {

        Logging.infoToConsole("Obtained file from external directory: " + check);

        FileConfiguration config;

        if (find(Main.plugin().getDataFolder().listFiles(), check)) {

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
