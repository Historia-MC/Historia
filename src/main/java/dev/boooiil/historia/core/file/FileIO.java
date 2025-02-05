package dev.boooiil.historia.core.file;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.util.Logging;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    private static final List<String> configFileNames = new ArrayList<>();

    static {

        for (FileKeys key : FileKeys.values()) {
            configFileNames.add(key.getKey());
        }

    }

    public static void checkFiles() {

        Logging.infoToConsole("Checking existence and version of config files.");

        for (String fileName : configFileNames) {
            File diskFile = new File(HistoriaCore.plugin().getDataFolder(), fileName);

            if (!diskFile.exists()) {
                Logging.infoToConsole("Missing config file: " + fileName + " has been saved to disk from resources.");
                Logging.infoToConsole("Location: " + diskFile.getAbsolutePath());
                HistoriaCore.plugin().saveResource(fileName, false);
                continue;
            }

            YamlConfiguration diskConfig = yamlFromSource(diskFile);
            YamlConfiguration jarConfig = yamlFromSource(HistoriaCore.plugin().getResource(fileName));

            int diskVersion = diskConfig.getInt("version");
            int jarVersion = jarConfig.getInt("version");

            if (diskVersion < jarVersion) {
                Logging.infoToConsole("Outdated config file (" + diskVersion + "): " + fileName
                        + " has been replaced on disk by the newer version " + jarVersion + ".");
                HistoriaCore.plugin().saveResource(fileName, true);
            }
        }

        Logging.infoToConsole("Completed checks of existence and version of config files.");

    }

    public static YamlConfiguration yamlFromSource(InputStream is) {
        Reader reader = new InputStreamReader(is, Charset.defaultCharset());
        return YamlConfiguration.loadConfiguration(reader);
    }

    public static YamlConfiguration yamlFromSource(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * It takes an array of files and a string, and returns true if the string is
     * the name of one of
     * the files in the array
     *
     * @param files The array of files to check
     * @param check The name of the file you want to check for.
     * @return A boolean value.
     */
    public static boolean find(File[] files, FileKeys check) {

        boolean found = false;

        for (File file : files) {

            if (file.getName().equals(check.getKey())) {
                found = true;
                break;
            }

        }

        return found;

    }

    /**
     * If the file exists in the external directory, load it from there. If it
     * doesn't, load it from
     * the internal directory
     *
     * @param check The file name to check for.
     * @return A YamlConfiguration object.
     */
    public static YamlConfiguration get(FileKeys check) {

        YamlConfiguration config;

        if (find(HistoriaCore.plugin().getDataFolder().listFiles(), check)) {

            Logging.debugToConsole("Obtained file from external directory: ",
                    HistoriaCore.plugin().getDataFolder().getPath() + "\\" + check.getKey());

            File file = new File(HistoriaCore.plugin().getDataFolder().getPath(), check.getKey());

            config = YamlConfiguration.loadConfiguration(file);
        }

        else {

            Logging.debugToConsole("Obtained file from internal directory: " + check.getKey());

            InputStream is = FileIO.class.getClassLoader().getResourceAsStream(check.getKey());

            Reader reader = new InputStreamReader(is);

            config = YamlConfiguration.loadConfiguration(reader);

        }

        return config;

    }
}
